# OOPDemo: 物件導向程式設計實作範例庫

本專案是配合「軟體工程與框架」課程第一章與第二章設計的 Java 範例庫。它包含核心 OOP 觀念演示、現代 Java 特性（Java 21）示範，以及一系列的實習課練習題（Labs）。

## 🚀 如何開始

1.  **環境需求**：Java 21 或以上版本，Maven。
2.  **編譯專案**：
    建議使用 Maven Wrapper (`./mvnw`)，這可以確保所有開發者都使用相同版本的 Maven，且不需事先安裝 Maven 於系統中：
    ```bash
    # 使用 Maven Wrapper 編譯 (推薦)
    ./mvnw clean compile

    # 或使用系統安裝的 Maven
    mvn clean compile
    ```

    Maven 與 Maven Wrapper 的差異：
    *   `mvn` 是建置工具，負責編譯、測試、打包、部署等任務。
    *   `mvnw` 是 Maven 的包裝器，可以在沒有安裝 Maven 的環境下執行 Maven 命令。

    為什麼要用 Maven 專案？
    *   Maven 可以自動管理專案的依賴項目。
    *   Maven 可以自動編譯、測試、打包、部署專案。
    *   Maven 可以自動生成專案的報告。

    為什麼要用 Java 21?
    *   Java 21 是目前最新的 LTS (Long-Term Support) Java 版本。
    *   Java 21 提供了許多新特性，例如：
        *   虛擬執行緒（Virtual Threads）
        *   記錄（Records）
        *   模式匹配（Pattern Matching）
        *   密封類別（Sealed Classes）
        *   文字區塊（Text Blocks）

3.  **執行主程式**：
    本專案提供多種執行方式，推薦使用 IntelliJ IDEA 或 Maven Wrapper：

    #### 🔹 方式 A：使用 IntelliJ IDEA (推薦)
    這是最適合教學與開發的方式：
    *   **直接執行**：打開 `src/main/java/swfw/ch01/basics/CarDemo.java` (或其他帶有 `main` 方法的類別)，點擊類別名稱或 `main` 方法左側的 **綠色播放按鈕 (Run)**。
    *   **主選單**：若要開啟包含所有範例的互動式選單，請執行 `swfw.Main` 類別。

    #### 🔹 方式 B：使用終端機 (命令行)
    如果你偏好使用命令行，可以使用 Maven Wrapper：
    ```bash
    # 執行特定的範例 (以 CarDemo 為例)
    ./mvnw exec:java -Dexec.mainClass="swfw.ch01.basics.CarDemo"

    ```
    這會透過 `exec-maven-plugin` 啟動專案，並自動處理好所有類別路徑（Classpath）。
