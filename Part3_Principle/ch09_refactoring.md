###### tags: `OOSE`

# Ch09 把脈清毒：程式碼重構

Martin Fowler 在其經典著作 *Refactoring: Improving the Design of Existing Code* 中介紹了「程式壞味道」（Bad Smell）與「重構」（Refactoring）方法。前者代表程式中存在的不良現象，後者則是具體的解決之道。

> [!TIP]
> 這些壞味道與重構方法是「設計樣式」（Design Patterns）的基本元素。一個具備設計感的程式系統往往包含若干個重構步驟，用以消除特定的程式壞味道。

## 9.1 把脈：程式壞味道

Martin Fowler 共提到了 21 個典型的程式壞味道，以下介紹其中最常見的部分：

- **重複的程式碼 (Duplicated Code)**：相同的程式結構出現在多處。應將其萃取出並集中在單一類別或方法中（如 `Extract Method`）。重複代碼會大幅降低維護性，導致「改了這、忘了那」的困境。
- **冗長的方法 (Long Method)**：方法過長（例如超過 100 行）會極難閱讀與維護。方法應代表單一的處理流程或演算法，當過於複雜時，應透過抽象化將其拆分。
- **大類別 (Large Class)**：一個類別承擔過多責任，包含過多屬性與方法。應將責任分割，交給不同的類別處理（如 `Extract Class`）。
- **太長的參數列 (Long Parameter List)**：參數過多（如超過 5 個）會增加理解難度並容易導致呼叫錯誤。可考慮將相關參數封裝成物件（如 `Introduce Parameter Object`）。
- **發散變更 (Divergent Change)**：一個類別因為過多不相關的變動原因而需要修改。這代表內聚力差，應確保一個類別僅負責一個專一的功能。
- **散彈槍手術 (Shotgun Surgery)**：每當進行一個變更，都需要修改許多不同的模組（跨多個類別）。這會導致修改風險極高，理想情況下應將變動點集中。
- **依戀情結 (Feature Envy)**：一個類別的方法頻繁存取另一個類別的屬性。這通常代表該方法應該屬於被存取的那個類別。
- **資料泥團 (Data Clumps)**：某些資料總是一起出現（如：郵遞區號、城市、街道），卻沒有被封裝成類別。應將其抽象化為一個物件（如 `Address`）。
- **基本型別偏執 (Primitive Obsession)**：堅持只用基本型別（如 `int`, `String`）而抗拒使用小類別。例如「幣值」應由 `Money` 類別表示，而非僅用 `int` 加 `String`。
- **Switch 敘述句 (Switch Statements)**：過度使用 `switch-case` 而非多型。當需要新增選項時必須修改程式碼，違反開閉原則 (OCP)。
- **平行繼承體系 (Parallel Inheritance Hierarchies)**：當你為類別 A 增加子類別時，也必須為類別 B 增加子類別。這是「散彈槍手術」的一種特例。
- **不實用的一般性 (Speculative Generality)**：為了「未來可能的擴充」而預留了過多複雜的設計，但實際上卻從未用到。
- **暫時欄位 (Temporary Field)**：某些屬性僅在特定演算法執行時才有用，其餘時間皆為空值，這會增加類別理解的難度。
- **過度的訊息串 (Message Chains)**：`a.getB().getC().getD()`。過長的呼叫鏈表示類別導航過於透明，違反迪米特法則。
- **過度的中間人 (Middle Man)**：一個類別過多方法都只是在轉發 (Delegate) 請求設計。應考慮務必移除中間人，讓 Client 直接與目標物件溝通。
- **狎暱關係 (Inappropriate Intimacy)**：類別之間存取過於頻繁的內部私有細節。應透過重構降低其依賴程度或重新分配職責。
- **同功能不同介面 (Alternative Classes with Different Interfaces)**：兩個類別的功能幾乎相同，但方法名稱或介面卻不同。
- **資料類別 (Data Class)**：類別僅包含欄位與 `getter/setter`，缺乏行為。應思考將相關邏輯移入此類別中。
- **防臭的註解 (Comments)**：當程式碼寫得太差時，工程師被迫加上大量註解。應透過改善命名與重構來讓程式碼「自我解釋」。

---

## 9.2 清毒：程式碼重構

重構方法一共有六大類，包含 25 種形式與 70 多個具體方法：

1. **方法組裝 (Composing Methods)**：重組方法內的語句。
2. **特性移動 (Moving Features Between Objects)**：在物件之間移動屬性或方法。
3. **資料組織 (Organizing Data)**：改善資料結構的處理方式。
4. **條件簡化 (Simplifying Conditional Expressions)**：簡化複雜的邏輯判斷。
5. **呼叫簡化 (Making Method Calls Simpler)**：讓介面與方法調用更清晰。
6. **一般化處理 (Dealing with Generalization)**：處理類別架構中的繼承關係。

> [!IMPORTANT]
> 重構方法著重在「動作」，沒有絕對的對錯，有些方法甚至恰好對立（如 `Extract Method` 與 `Inline Method`），應根據具體情境靈活應用。

### 9.2.1 方法組裝

- **Extract Method**：把功能萃取出來，形成另一個獨立方法。
- **Inline Method**：直接使用該方法的功能，減少不必要的包裝。

```java
// Inline Method 範例
class Student {
   boolean pass() {
      // Before: return isGoodGrade();
      // After:
      return grade > 60;
   }
}
```

- **Introduce Explaining Variables**：導入有意義的變數名稱，取代複雜的表示式（特別是條件判斷）。

```java
// Introduce Explaining Variables 範例
class Student {
   boolean getScholarship() {
      boolean isMasterStudent = (sType == "M");
      boolean allPass = (g1 > 60 && g2 > 70);
      if (isMasterStudent && allPass) {
         return true;
      }
      return false;
   }     
}   
```

- **Inline Temp**：不宣告暫時變數，直接使用表達式。
- **Split Temporary Variable**：不共用同一個暫時變數來儲存不同的計算意義。

### 9.2.2 特性移動

- **Move Method / Field**：將方法或屬性移至更合適的類別。
- **Extract Class**：將責任過重的類別拆分為多個專一的小類別。
- **Inline Class**：將過於簡單的類別併入另一個類別中。
- **Remove Middle Man**：移除轉發層，讓物件直接溝通。

### 9.2.3 資料組織

- **Replace Data Value with Object**：將單純的字串或數值封裝為具有行為的物件。
- **Replace Array with Object**：將存有不同型態資料的陣列改為明確的物件（類別）。

```java
// Before: String student [] = {"Nick", "P1010", "Master"}
// After:
class Student {
   String name;
   String id;
   String degree;
}    
```

- **Encapsulate Field**：將公開屬性改為私有，並提供 `getter / setter`。
- **Encapsulate Collection**：對集合物件不提供直接的 setter，而是提供 `add / remove` 方法。

```java
// Encapsulate Collection 範例
class Student {
   private Set courses = new HashSet();
   
   public void addCourse(Course course) { courses.add(course); }
   public void removeCourse(Course course) { courses.remove(course); }
   public Set getCourses() { return Collections.unmodifiableSet(courses); }
}   
```

- **Replace Subclass with Fields**：當子類別差異過小時，改用欄位區分即可，不需建立子類別。

### 9.2.4 條件簡化

- **Consolidate Duplicate Conditional Fragments**：將所有路徑中重複出現的程式片段抽離出條件句。
- **Replace Nested Conditional with Guard Clauses**：避免深層巢狀，使用 Guard Clauses（衛句）提早回傳。

```java
// Guard Clauses 範例
boolean isPass() {
   if (isPhD) return computePhDGrade();
   if (isPartTimeMaster) return computePartTimeMasterGrade();
   if (isMaster) return computeMasterGrade();
   return computeGrade();
}               
```

- **Replace Conditional with Polymorphism**：以「多型」取代複雜的 `switch-case` 或 `if-else`。

```mermaid
---
title: 重構案例- 以多型取代判斷
---
classDiagram
    direction LR
    class Student {
        <<abstract>>
        +getGrade()* int
    }
    class PhDStudent {
        +getGrade() int
    }
    class MasterStudent {
        +getGrade() int
    }
    class UndergradStudent {
        +getGrade() int
    }
    
    Student <|-- PhDStudent
    Student <|-- MasterStudent
    Student <|-- UndergradStudent
    
    note for Student "定義抽象方法 (規格)"
    note for PhDStudent "實作博士生計分邏輯"
```

### 9.2.5 呼叫簡化

- **Separate Query from Modifier**：將讀取資料與修改資料的方法分開。
- **Replace Parameter with Explicit Methods**：與其用參數控制方法行為，不如拆分為明確命名的多個方法。

```java
// Before: setValue("grade", v);
// After:
void setGrade(int g) { grade = g; }
void setLevel(int l) { level = l; }
```

- **Introduce Parameter Object**：將過長的參數列封裝成單一物件。
- **Replace Error Code with Exception**：不回傳錯誤代碼（如 `-1`），改以拋出例外。

### 9.2.6 一般化處理

- **Pull Up Method / Field**：將共通的方法或屬性移至父類別。
- **Push Down Method / Field**：將僅子類別需要的方法或屬性移至該子類別。
- **Extract Superclass**：為具備相似行為的類別提取出共通的父類別。
- **Replace Inheritance with Delegation**：子類別若僅需父類別的部分功能，改用「委託」而非「繼承」。

---

## 9.3 核心重構案例詳解

本節針對實務中最常見的三個重構方法進行深度解析，透過對比重構前後的代碼與架構，幫助理解「壞味道」的消除過程。

### 9.3.1 搬移方法 (Move Method)

當一個類別的方法頻繁存取另一個類別的屬性時（依戀情結），該方法應搬移至目標類別。

```mermaid
---
title: 重構案例- 搬移方法 (Move Method)
---
classDiagram
    direction LR
    class Account {
        -AccountType type
        -int daysOverdrawn
        +getOverdraftCharge() int -- 搬移前
        +bankCharge() int
    }
    class AccountType {
        +getOverdraftCharge(int days) int -- 搬移後
    }
    Account o-- AccountType
    note for Account "搬移前：Account 承擔了計費邏輯"
    note for AccountType "搬移後：計費邏輯與類型定義合一"
```

*   **重構前**：`Account` 類別內具備 `getOverdraftCharge()` 方法，但讀取的資料幾乎都來自於 `AccountType`。
*   **重構後**：將方法移至 `AccountType`，`Account` 僅需呼叫 `type.getOverdraftCharge(daysOverdrawn)`。

### 9.3.2 提煉類別 (Extract Class)

當一個類別因職責過多而變得龐大（大類別）時，應將其部分屬性與行為提煉至新的類別中。

```java
// 重構前：Person 類別包含了所有的辦公室電話資訊
class Person {
    private String name;
    private String officeAreaCode;
    private String officeNumber;

    public String getTelephoneNumber() {
        return "(" + officeAreaCode + ") " + officeNumber;
    }
}

// 重構後：提煉出 TelephoneNumber 類別
class TelephoneNumber {
    private String areaCode;
    private String number;
    public String getTelephoneNumber() {
        return "(" + areaCode + ") " + number;
    }
}

class Person {
    private String name;
    private TelephoneNumber officeTelephone = new TelephoneNumber();
}
```

*   **好處**：降低了 `Person` 類別的複雜度，且 `TelephoneNumber` 未來可被其他類別復用。

### 9.3.3 以衛句取代巢狀判斷 (Replace Nested Conditional with Guard Clauses)

當條件判斷過於深層（巢狀）時，會增加閱讀的認知負荷。應使用「衛句」提早退出方法。

```java
// 重構前：深層巢狀邏輯
double getPayAmount() {
    double result;
    if (isDead) result = deadAmount();
    else {
        if (isSeparated) result = separatedAmount();
        else {
            if (isRetired) result = retiredAmount();
            else result = normalPayAmount();
        }
    }
    return result;
}

// 重構後：使用衛句 (Guard Clauses) 扁平化邏輯
double getPayAmount() {
    if (isDead) return deadAmount();
    if (isSeparated) return separatedAmount();
    if (isRetired) return retiredAmount();
    return normalPayAmount();
}
```

*   **核心理念**：若條件判斷是「非正常情況」的特殊處理，應在判斷後立即回傳，讓「正常情況」的邏輯保持在最外層，提高清晰度。

---

## 9.4 綜合練習

本小節提供互動式練習，請思考重構的原則並核對解答。

---

1️⃣ **觀念辨析：軟體重構的目的**
軟體重構之後，會改善其功能（外部行為）嗎？為什麼？

<details>
<summary>解答</summary>

**不會。**
**說明：** 重構的定義是在「不改變軟體外部行為」的前提下，改善其內部的程式結構。目的是提高可讀性、維護性與擴展性，而非增加新功能或修正 Bug（雖然重構過程中常會發現潛在的 Bug）。
</details>

---

2️⃣ **壞味道辨識：散彈槍手術 (Shotgun Surgery)**
下列何者為散彈槍手術的概念？ (單選)
- (A) 一個類別因為過多的需求變動而頻繁修改
- (B) 每當一個變動，你必須在許多不同的類別中同時做出修改
- (C) 相關聯的資料總是一起出現卻沒有封裝成類別
- (D) 一個計算邏輯需要從許多其他類別中取得資料

<details>
<summary>解答</summary>

**解答：(B)**
**說明：** 散彈槍手術是指一種變更散佈在多個類別中（像散彈一樣散開）。(A) 對應的是「發散變更」(Divergent Change)。
</details>

---

3️⃣ **重構方法：重複程式碼**
關於解決「重複程式碼」的方法，下列敘述何時有誤？ (單選)
- (A) 若在同一個類別中，可透過 `Extract Method` 萃取。
- (B) 若在不相干類別中，可透過 `Extract Class` 先將重複部分抽取出來。
- (C) 若在兩個具備相同父類別的子類別中，應使用 `Push Down Method` 處理。
- (D) 若部分相似但不完全相同，仍可萃取出相同部分。

<details>
<summary>解答</summary>

**解答：(C)**
**說明：** 若子類別有重複代碼，應使用 `Pull Up Method` 將其往上移至父類別，而非往下的 `Push Down`。
</details>

---

4️⃣ **壞味道辨識：過長的參數列**
關於「過多參數」的壞味道，下列敘述何時有誤？ (單選)
- (A) 增加理解難度。
- (B) 會大幅降低程式的執行效率。
- (C) 容易造成呼叫時參數順序錯誤的 Bug。
- (D) 每次需要更多參數時都需連動修改呼叫處，增加維護困難。

<details>
<summary>解答</summary>

**解答：(B)**
**說明：** 重構主要關注的是「維護性」而非「執行效率」。參數列長度對執行效率的影響在現代編譯器中微乎其微。
</details>

---

5️⃣ **解決方案：巨大的類別**
對於「巨大的類別」，下列哪一項重構方法**不建議**使用？ (單選)
- (A) 將每個方法都獨立成一個新類別，以確保功能不干擾。
- (B) 運用 `Extract Class` 將相關變數與邏輯提煉至新類別。
- (C) 使用 `Extract Subclass` 拆解出特定的子類別功能。
- (D) 透過 `Extract Interface` 為不同客群定義適合的操作介面。

<details>
<summary>解答</summary>

**解答：(A)**
**說明：** 過度拆分會造成「懶惰類別」或「過多中間人」，反而增加系統複雜度。重構應基於責任 (Responsibility) 來拆分。
</details>

---

6️⃣ **實作應用：重構錯誤處理**
以下程式碼存在什麼問題？應如何重構？
```java
int computeGrade() {
   // ... 運算邏輯 ...
   if (grade > 100 || grade < 0) return -1;
   return 0;
}
```

<details>
<summary>解答</summary>

**問題：** `Replace Error Code with Exception`
**說明：** 應拋出明確的例外（Exception）而非回傳特殊碼（-1）。
**重構建議：**
```java
void computeGrade() throws GradeException {
   if (grade > 100 || grade < 0) throw new GradeException("成績區間錯誤");
}
```
</details>

---

7️⃣ **實作應用：集合封裝**
以下設計可能違反了什麼原則？該如何改善？
```java
class Student {
   Set getCourse() { return courses; }
   void setCourse(Set c) { this.courses = c; }
}
```

<details>
<summary>解答</summary>

**問題：** `Encapsulate Collection`
**說明：** 直接提供 setter 會讓外部可以隨意替換整個集合，破壞封裝。
**重構建議：** 移除 `setCourse`，改提供 `addCourse` 與 `removeCourse`，並讓 `getCourse` 回傳不可變的 View。
</details>

---

8️⃣ **實作應用：取代複雜條件句**
若類別中出現繁雜的 `switch (studentType)` 來計算成績，最建議的重構手法是？

<details>
<summary>解答</summary>

**解答：** `Replace Conditional with Polymorphism` (以多型取代判斷)
**說明：** 建立 `Student` 抽象父類別，並讓不同類身份的子類別實作各自的 `getGrade()` 方法，符合 OCP 原則。
</details>

---


---

*本章節旨在引導學生掌握從發現「壞味道」到執行「重構」的流程，轉化不良程式為高品質設計。*
