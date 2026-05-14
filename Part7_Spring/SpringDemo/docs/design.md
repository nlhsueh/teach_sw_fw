

# Design Patterns Application

## University system 目前具備以下功能
* Admin 新增刪除管理系所(Department)、師資(Teacher)與課程(Course)清單。
* 學生查詢課程、加退選課程、查看成績。
* 修改學生在某課程的成績

## Spring 框架的設計理念與物件導向原則

在探討 University 具體例子之前，我們必須先理解 Spring 框架本身的設計理念。Spring 的誕生就是為了解決傳統 Java EE 開發時的臃腫與高耦合問題。它的核心設計哲學與我們在 **Part 3** 學到的 OOD 原則（SOLID）不謀而合：

### 1. IoC/DI 與 DIP (相依反轉原則)
Spring 最核心的機制是 **IoC (Inversion of Control，控制反轉)** 與 **DI (Dependency Injection，相依注入)**。
* **設計理念**：傳統寫法中，高階物件（如 Controller）會自己 `new` 出低階物件（如 Service），導致強烈耦合且難以單元測試。Spring 則是將「建立物件」與「維護物件相依關係」的控制權，從程式碼中抽離，交由 Spring 容器管理。
* **符合原則**：這正是 **DIP** 的終極實踐。高階模組與低階模組都不再互相依賴，而是共同相依於抽象（介面），而具體的實作則由 Spring 容器在執行期動態注入。

### 2. AOP 與 SRP (單一職責原則)
**AOP (Aspect-Oriented Programming，剖面導向程式設計)** 是 Spring 的另一大支柱。
* **設計理念**：在企業級應用中，日誌記錄、權限檢查、事務管理（Transaction）等功能會散落（Cross-cutting）在各個業務方法中。
* **符合原則**：AOP 允許我們將這些「橫切關注點」抽離出來，集中在 Aspect（切面）中管理。這使得我們的 Service 類別可以專注於純粹的業務邏輯，不需要混雜系統層級的雜務，完美符合 **SRP**。

### 3. 非侵入式設計與 OCP (開放封閉原則)
* **設計理念**：Spring 倡導「非侵入式（Non-invasive）」設計。你的業務類別不需要繼承 Spring 的特定類別或實作其介面，只需使用 POJO（Plain Old Java Object）搭配註解即可。
* **符合原則**：這提供了極高的彈性與擴充性。當你需要改變行為或增加功能時，可以透過設定或新增 AOP 切面來達成，而不需要修改原本封閉好的業務程式碼，符合 **OCP**。

---

## 針對 University 這個例子，以下 Part3 Principle 的應用狀況：

* **SRP (單一職責原則)**
  - **應用狀況**：目前的 [SchoolService.java](../src/main/java/com/example/demo/service/SchoolService.java) 同時處理了學生、教師、課程的管理，以及成績計算和 DTO 轉換，這實際上違反了 SRP（一個類別有太多改變的理由）。在實際重構時，應將其拆分為 `StudentService`、`CourseService` 等專職類別。
* **OCP (開放封閉原則)**
  - **應用狀況**：在 [SchoolService.java](../src/main/java/com/example/demo/service/SchoolService.java) 的 `toPerformanceDTO` 方法中，判斷學術站位（Pass, Probation）的邏輯是寫死的。若未來需要新增不同的及格標準（例如交換生或碩士生），勢必得修改此類別。若改用 **Strategy 模式** 抽離計算邏輯，即可符合 OCP。
* **LSP (Liskov 取代原則)**
  - **應用狀況**：若系統未來擴充 `RequiredCourse` (必修) 與 `ElectiveCourse` (選修) 繼承自 `Course`。在學生加退選的模組中，傳入任何一種子類別都應該能正常運作，不應因為子類別的特殊行為而破壞原本流程。
* **ISP (介面分離原則)**
  - **應用狀況**：目前系統沒有使用介面（Interface），所有 Controller 都直接相依於具體的 [SchoolService.java](../src/main/java/com/example/demo/service/SchoolService.java)。若要符合 ISP，應為不同角色定義專屬介面（如：`StudentOperations`、`AdminOperations`），讓類別只相依於它需要的方法。
* **DIP (相依反轉原則)**
  - **應用狀況**：這在 [SchoolController.java](../src/main/java/com/example/demo/controller/SchoolController.java) 中得到了部分展現：它透過 `@Autowired` 讓 Spring 容器注入 `SchoolService`，而不是自己 `new` 出來。但若要完全符合 DIP，Controller 應該相依於 `SchoolService` 的**介面**而非具體類別。

## 針對 University 這個例子，以下 design patterns 可被應用的狀況：

### Part 4: 建立型模式 (Creational Patterns)

* **Factory method**: 
  - **Spring 框架應用**：Spring 的 `BeanFactory` 是工廠模式的典型應用，負責建立與管理 Bean。
  - **University 系統**：當我們在 [SchoolController.java](../src/main/java/com/example/demo/controller/SchoolController.java) 中使用 `@Autowired` 進行依賴注入時，底層即是工廠模式在運作。詳細 UML 圖請參閱 [UML.md#2-工廠模式-factory-pattern](UML.md#2-工廠模式-factory-pattern)。
* **Abstract factory**: 
  - **Spring 框架應用**：`ApplicationContext` 整合了多種工廠與服務，可視為大型的抽象工廠。
  - **University 系統**：**無**在業務邏輯中直接實作，完全依賴 Spring 容器。
* **Singleton**: 
  - **Spring 框架應用**：Spring Bean 預設皆為 Singleton。
  - **University 系統**：系統中的 [SchoolService.java](../src/main/java/com/example/demo/service/SchoolService.java) 在 Spring 管理下是單例，確保全系統共用同一份實例與記憶體內的資料。概念圖請參閱 [UML.md#1-單例模式-singleton-pattern](UML.md#1-單例模式-singleton-pattern)。

### Part 5: 結構型模式 (Structural Patterns)

* **Adapter**: 
  - **University 系統**：透過 Adapter 與既存的 Web API（如舊教務系統）建立連結，並提供統一的介面供 Spring 應用程式呼叫（目前系統中尚未實作此轉接器，若未來需要，可由 [SchoolService.java](../src/main/java/com/example/demo/service/SchoolService.java) 呼叫）。概念圖請參閱 [UML.md#8-轉接器模式-adapter-pattern](UML.md#8-轉接器模式-adapter-pattern)。
  - **Spring 框架應用**：Spring MVC 的 `HandlerAdapter` 也是經典應用。
* **Bridge**: 
  - **Spring 框架應用**：Java 的 JDBC API 本身就是 Bridge 模式，將抽象的資料庫操作與各家廠商的具體驅動程式解耦。
  - **University 系統**：**無**在業務碼中顯式實作，但若未來接入真實資料庫（透過 JPA/JDBC），將間接享有 JDBC Bridge 的好處。
* **Composite**: 
  - **University 系統**：若「系所」有子系所或組織階層結構，可應用此模式以一致方式處理。以目前扁平結構來看為**無**。
* **Decorator**: 
  - **Spring 框架應用**：Spring 的 AOP（剖面導向程式設計）與 `@Transactional`（事務管理）機制，本質上就是 Decorator（或 Proxy）概念的應用。詳細 UML 圖請參閱 [UML.md#3-代理模式-proxy-pattern](UML.md#3-代理模式-proxy-pattern) 或 [UML.md#7-裝飾者模式-decorator-pattern](UML.md#7-裝飾者模式-decorator-pattern)。
  - **University 系統**：若我們未來在 [SchoolService.java](../src/main/java/com/example/demo/service/SchoolService.java) 的方法上加上事務或自訂日誌註解，Spring 就會動態為我們的物件「裝飾」上這些功能。

### 其他模式 (Part 6 行為型模式)

* **Strategy**: 將不同的成績計算邏輯封裝成不同的 Strategy 物件，讓系統可以根據不同的需求選擇不同的成績計算邏輯。概念圖請參閱 [UML.md#6-策略模式-strategy-pattern](UML.md#6-策略模式-strategy-pattern)。
* **Template Method**: 定義一個通用的課程建立流程，並將部分實作交由子類別。概念圖請參閱 [UML.md#4-範本方法模式-template-method-pattern](UML.md#4-範本方法模式-template-method-pattern)。
* **Observer**: 建立一對多的相依關係，自動通知狀態更新。概念圖請參閱 [UML.md#5-觀察者模式-observer-pattern](UML.md#5-觀察者模式-observer-pattern)。
* **Chain of Responsibility**: 可用於過濾請求（例如 Spring Security 的過濾器鏈）。
