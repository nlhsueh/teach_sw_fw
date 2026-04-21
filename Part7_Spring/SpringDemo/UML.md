# SpringDemo UML Architecture

本文檔包含了 `SpringDemo` 的軟體架構圖，旨在演示物件導向設計 (OOD) 原則與設計樣式。

## 1. 領域模型與 OO 原則 (Domain Model & OO Principles)
這張圖表重點展示了 **繼承 (Inheritance)**、**介面 (Interface)** 以及 **組合與聚合 (Composition vs Aggregation)**。

```mermaid
classDiagram
    direction TB

    class Displayable {
        <<interface>>
        +displayInfo()* String
    }

    class SchoolMember {
        <<abstract>>
        #String memberId
        #String name
        #LocalDate joinDate
        +displayInfo()* String
    }

    class Student {
        -int gradeLevel
        -List~Grade~ grades
        +enrollCourse(course)
    }

    class Teacher {
        -String department
        -List~Course~ taughtCourses
        +assignGrade(student, course, score)
    }

    class Grade {
        -double score
        +getLetterGrade() String
    }

    class School {
        -String schoolName
        +openDepartment(name)
    }

    class Department {
        -String deptName
        +addCourse(course)
        +assignTeacher(teacher)
    }

    Displayable <|.. SchoolMember : Interface Realization
    SchoolMember <|-- Student : Inheritance (is-a)
    SchoolMember <|-- Teacher : Inheritance (is-a)

    School "1" *-- "*" Department : Composition (強擁有)
    Department "1" o-- "*" Course : Aggregation (弱擁有)
    Teacher "1" -- "*" Course : teaches
    Student "1" *-- "*" Grade : Composition
    Grade "*" -- "1" Course : references
```

---

## 2. 系統分層架構 (Layered Architecture)
這張圖展示了 **關注點分離 (Separation of Concerns)**，即 Controller、Service 與 Model 之間的調用關係。

```mermaid
graph LR
    subgraph Web_Layer ["Web Layer (API)"]
        SC[SchoolController]
    end

    subgraph Service_Layer ["Service Layer (Business Logic)"]
        SS[SchoolService]
    end

    subgraph Domain_Layer ["Domain Layer (Models)"]
        SM[SchoolMember]
        S[Student]
        T[Teacher]
        C[Course]
    end

    SC -- "Calls" --> SS
    SS -- "Manages" --> Domain_Layer
```

---

## 3. 資料傳輸與封裝 (DTO Pattern)
這張圖解釋了為何要使用 **DTO (Data Transfer Object)** 來保護內部 Domain Model，防止 **隱私洩漏 (Privacy Leak)**。

```mermaid
classDiagram
    class Student {
        <<Domain Entity>>
        -Map~Course, Grade~ grades
        +getGrades() List
    }

    class StudentDTO {
        <<Data Transfer Object>>
        +String name
        +List~GradeDTO~ grades
    }

    class SchoolService {
        +toStudentDTO(Student) StudentDTO
    }
```

---

## 4. Spring 框架互動流程 (Spring Framework Interaction)
這張時序圖 (Sequence Diagram) 呈現了當使用者點擊「選課」時，Spring 框架如何與我們的領域模型進行互動。

```mermaid
sequenceDiagram
    participant Browser as 瀏覽器 (Frontend)
    participant Controller as SchoolController (@RestController)
    participant Service as SchoolService (@Service)
    participant Model as Student (Model)

    Note over Browser, Model: 使用者發送選課請求
    Browser->>Controller: POST /api/school/enroll {studentId, courseId}
    
    Note right of Controller: Spring 自動將 JSON <br/>轉為 Map/DTO
    Controller->>Service: enrollStudent(sId, cId)
    
    Note right of Service: 透過 @Autowired 注入後的業務邏輯
    Service->>Model: enrollCourse(course)
    Model-->>Service: 更新內部狀態 (Grades List)
    
    Service-->>Controller: 執行完成
    Controller-->>Browser: 200 OK
    
    Note over Browser: 前端收到通知，重新整理列表
```

### 設計要點說明：
1.  **依賴注入 (Dependency Injection)**：`SchoolController` 不需要自己建立 `SchoolService`，而是透過 `@Autowired` 由 Spring 容器自動注入單例物件。
2.  **請求映射 (Request Mapping)**：Spring 透過 `@PostMapping` 攔截 HTTP 請求，並利用 `Jackson` 函式庫自動處理物件與 JSON 之間的轉換。
---

## 5. 與 Spring 框架的結構關係 (Framework Structural Integration)
這張類別圖展示了專案代碼與 Spring Framework 之間的結構聯繫。雖然 Spring 提倡 POJO (Plain Old Java Objects)，不強調強制的繼承，但透過 **註解 (Annotations)** 建立了深刻的結構關聯。

```mermaid
classDiagram
    direction TB
    
    class SpringFramework {
        <<Library/Framework>>
        +run(Class, args)
        +getBean()
    }

    class SpringBootApplication { <<annotation>> }
    class RestController { <<annotation>> }
    class Service { <<annotation>> }
    class Autowired { <<annotation>> }

    class SpringDemoApplication {
        +main(args)
    }

    class SchoolController {
        -SchoolService schoolService
    }

    class SchoolService {
        -School school
    }

    %% 結構聯繫
    SpringDemoApplication ..> SpringFramework : uses (calls run())
    SpringBootApplication ..> SpringDemoApplication : marks
    
    RestController ..> SchoolController : marks (Managed Bean)
    Service ..> SchoolService : marks (Managed Bean)
    
    SchoolController "1" o-- "1" SchoolService : Dependency Injection
    Autowired ..> SchoolController : directs injection
    
    note for SchoolController "透過註解，Spring 知道如何將 HTTP <br/>與此類別連動，這就是控制反轉 (IoC)"
```

### 關鍵概念說明：
1.  **與框架解耦 (Decoupling)**：注意到我們的 `SchoolService` 與 `SchoolController` 並沒有 `extends` 或 `implements` 任何 Spring 的類別。這展示了 **非侵入式 (Non-invasive)** 框架設計的優點——業務邏輯與框架分離。
2.  **註解即契約 (Annotations as Contracts)**：`@RestController` 與 `@Service` 告訴 Spring：「請幫我管理這個類別的生命週期」。Spring 隨後會透過反射 (Reflection) 機制與這些類別互動。
3.  **控制反轉 (Inversion of Control)**：在傳統程式中，我們會在 Controller 中 `new SchoolService()`；但在 Spring 中，我們僅標註 `@Autowired`，由框架「反向」將服務實例注入到控制器中。
