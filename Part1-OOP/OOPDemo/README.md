# OOPDemo: 物件導向程式設計實作範例庫

本專案是配合「軟體架構與框架」課程第一章與第二章設計的 Java 範例庫。它包含核心 OOP 觀念演示、現代 Java 特性（Java 17+）示範，以及一系列的實習課練習題（Labs）。

## 🚀 如何開始

1.  **環境需求**：Java 17 或以上版本（建議 Java 21+），Maven。
2.  **編譯專案**：
    ```bash
    mvn clean compile
    ```
3.  **執行主程式**：
    執行 `swfw.Main` 類別，它提供了一個互動式選單，讓你可以選擇要執行的範例或實習課。

---

## 📚 實習課練習 (Lab Exercises) 指南

練習題分為兩個階段，分別對應教材的兩章內容。

### 🔹 Chapter 1: 物件基礎與封裝 (Lab 01)

| 練習編號 | 範例名稱 | 說明與引導 | 檔案位置 |
| :--- | :--- | :--- | :--- |
| **L1** | **學生封裝練習 (Student)** | 練習如何使用 `private` 成員與 `public` Getter/Setter。請注意 GPA 的驗證邏輯，確保其數值在 0.0 到 4.0 之間。 | `swfw.labs.lab01.StudentLab` |
| **L2** | **銀行帳戶練習 (BankAccount)** | 強化封裝觀念。實作存款（deposit）與取款（withdraw）功能，並在程式中加入邏輯判斷避免非法金額或餘額不足。 | `swfw.labs.lab01.BankAccountLab` |
| **L3** | **汽車控制練習 (Car)** | 模擬真實世界的物件狀態。練習如何透過方法改變內部狀態（時速），並確保狀態變化的合理性（時速不能小於 0）。 | `swfw.labs.lab01.CarLab` |

### 🔹 Chapter 2: 物件導向進階 (Lab 02)

| 練習編號 | 範例名稱 | 說明與引導 | 檔案位置 |
| :--- | :--- | :--- | :--- |
| **L4** | **RPG 多型練習 (Character)** | **繼承與覆寫**：透過 `Character` 抽象類別，實作 `Warrior` 與 `Mage` 不同的攻擊方法（多型）。 | `swfw.labs.lab02.RPGGameLab` |
| **L5** | **圖形面積練習 (Shape)** | **多型應用**：練習使用父類別參照 (`Shape`) 來管理一組子類別物件（圓形、矩形），並統呼叫其面積計算法。 | `swfw.labs.lab02.ShapeAreaLab` |
| **L6** | **健康檢查練習 (BMI)** | **抽象類別**：實作 `People` 抽象類別。不同族群（學生與運動員）有不同的 BMI 過重判定標準，練習如何定義抽象方法並讓子類別具體化。 | `swfw.labs.lab02.PeopleBMILab` |

---

## 🛠 核心教學範例導覽

除了練習題，專案中也包含許多可供參考的「完美實作」範例：

*   **現代特性**：
    *   `swfw.ch01.modern.CarRecord`: 展示 Java 14+ 的 **Record**。
    *   `swfw.ch02.modern.SealedShapeDemo`: 展示 Java 17+ 的 **Sealed Classes**。
*   **物件關聯**：
    *   `swfw.ch01.relationships.HeroEquipmentDemo`: 清楚比對 **Composition** 與 **Aggregation** 的差異。
*   **介面與多型**：
    *   `swfw.ch02.interfaces.InterfaceDemo`: 演示介面的實作與 Java 8+ 的 **default method**。

---

## 🧪 驗證與測試

本專案使用 Maven 管理，建議在開發時確保程式碼能正確編譯。

```bash
mvn compile
```

如有任何問題，請參考 `src/main/java/swfw/` 目錄下的註解說明。
