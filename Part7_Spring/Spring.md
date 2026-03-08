# Spring Boot 開發實務：從物件基礎到 Web 應用

這是一份針對已經具備 Java 物件導向基礎（如封裝、繼承、泛型）的學生所設計的 Spring Boot 教材。我們將跳過傳統 Spring 複雜的配置，直接進入現代化的開發流程。

## 內容大綱

### 1. Spring Boot 簡介
*   **為什麼選擇 Spring Boot？**
*   **核心優勢**：慣例優於配置、內嵌伺服器、自動化配置。
*   **與傳統 Spring 的差異**。

### 2. 快速動手做 (Quick Start)
*   **使用 IntelliJ IDEA 建立首個專案**。
*   **撰寫第一個 REST API**。
*   **理解專案結構**。

### 3. 核心概念：IoC 與 DI
*   **控制反轉 (IoC)**：誰來管理物件？
*   **依賴注入 (DI)**：如何組合物件？（建構子注入實務）。
*   **Bean 的生命週期與作用域**。

### 4. 物件設計與 Spring Boot
*   **POJO 的角色**：Entity、DTO、VO。
*   **封裝與安全性**：防範隱私洩漏 (Privacy Leak)。
*   **泛型的應用**：型別安全的 API 回應 (`ResponseEntity<T>`)。

### 5. 實戰練習：學生資訊系統
*   **整合練習**：銜接 `ch01_obp` 的 Student 與 Course 範例。

---

## 1. Spring Boot 簡介

**什麼是 Spring Boot？**
Spring Boot 是基於 Spring Framework 的延伸框架，旨在「簡化」Spring 應用程式的初始搭建與開發過程。它讓開發者能以最少的配置，快速建立一個生產等級的獨立應用程式。

**核心優勢**
*   **慣例優於配置 (Convention over Configuration)**：Spring Boot 預設了許多合理的設定，除非你有特殊需求，否則不需要寫大量的 XML 或 Java Config。
*   **內嵌伺服器**：內建 Tomcat/Jetty，不需要安裝額外的 Web 伺服器軟體，執行 `main` 方法即可啟動 Web 服務。
*   **Starter POMs**：簡化 Maven 依賴管理，只要引入 `spring-boot-starter-web`，所有 Web 開發需要的套件都會自動帶入。

---

## 2. 快速安裝與第一個程式 (Quick Start)

為了讓學生快速上手，我們直接使用 **IntelliJ IDEA** 內建的功能來建立專案。

### A. 產生專案
1. 開啟 IntelliJ IDEA，點擊 **New Project**。
2. 選擇 **Spring Initializr**。
3. 設定：**Name** (`demo`), **Type** (Maven), **Java** (17 或 21), **Packaging** (Jar)。
4. 相依性選擇：搜尋並勾選 **Spring Web**。
5. 點擊 **Create**，等待 IntelliJ 下載完成。

### B. 撰寫第一個 Controller
在 `src/main/java` 下建立 `HelloController.java`：

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 告訴 Spring 這是一個處理 API 的元件件
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
}
```

### C. 執行與測試
執行 `Application.java` 中的 `main` 方法，並存取 `http://localhost:8080/hello`。

---

## 3. 核心概念：IoC、DI 與物件設計

### 控制反轉 (IoC, Inversion of Control)
在基礎 Java 中，我們用 `new` 來建立物件：
`UserService service = new UserService();`
而在 Spring Boot 中，我們將物件的「控制權」交給容器。只要標註 `@Service` 或 `@Component`，Spring 就會自動幫我們實例化並管理這個物件。

### 依賴注入 (DI, Dependency Injection)
當一個物件需要另一個物件時（例如 Controller 需要 Service），我們不自己 `new`，而是讓 Spring 注入進來。**推薦使用建構子注入**，因為它最安全且方便測試：

```java
@RestController
public class StudentController {
    private final StudentService service; // 標註為 final

    public StudentController(StudentService service) { // 由 Spring 自動注入
        this.service = service;
    }
}
```

### 銜接物件導向基礎 (ch01_obp)
在 Spring Boot 開發中，良好的物件設計依然是核心：
1.  **POJO (Plain Old Java Object)**：我們定義的 `Student` 或 `Course` 物件。
2.  **DTO (Data Transfer Object)**：為了防範隱私洩漏 (Privacy Leak)，我們不直接回傳帶有敏感資料（如密碼）的 Entity，而是透過 DTO 只傳送需要的欄位。
3.  **泛型 (Generics)**：利用 `ResponseEntity<T>`，我們可以讓 API 的回傳值具備型別安全性。

---

## 4. 結語：為什麼學 Spring Boot？
學習 Spring Boot 不僅是學習一個框架，更是學習如何將基礎的物件導向知識（封裝、繼承、多型）應用在複雜的企業級系統中。透過 IoC 與 DI，我們能寫出低耦合、易測試且高品質的代碼。