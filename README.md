# Software Framework 軟體架構與框架

本專案旨在提供逢甲大學資訊工程學系（IECS, FCU）軟體架構與框架課程的教學教材。涵蓋從物件導向基礎、軟體塑模到設計樣式（Design Patterns）的完整領域。

---

## 🚩 課程大綱

### Part 1: 馬步 - 物件程式設計基礎
本章節建立穩固的物件開發基礎，介紹從基本物件到進階導向觀念。
- [Ch01 物件基礎設計 (OBP)](./Part1-OOP/ch01_obp.md): 介紹物件的基本概念、封裝（Encapsulation）、自訂建構子以及現代 Java 的 Record 特案。
- [Ch02 物件導向設計 (OOP)](./Part1-OOP/ch02_oop.md): 深入繼承（Inheritance）、介面（Interface）、多型（Polymorphism）以及密封類別（Sealed Classes）。
- **[OOPDemo 範例專案](Demo/src/)**: 整合式的實作程式碼，包含所有教材範例與練習題。

### Part II: 圖模術 - 軟體塑模
學習如何使用 UML 工具來視覺化軟體結構與行為。
- [Ch04 靜態圖模](./Part2-Model/ch04_static.md): 深入探討類別圖 (Class Diagram) 與物件圖的定義與應用。
- [Ch05 動態圖模](./Part2-Model/ch05_dynamic.md): 學習使用循序圖 (Sequence Diagram) 與狀態圖 (State Diagram) 描述系統行為。

### Part III: 心訣 - 設計原則
掌握高品質軟體的核心規則，學習如何撰寫易於維護的程式碼。
- [Ch07 軟體設計原則](./Part3-Principle/ch07_principle.md): 探討 Clean Code 與基本設計原則。
- [Ch08 物件導向設計原則](./Part3-Principle/ch08_oo.md): 深入 SOLID 五大原則 (SRP, OCP, LSP, ISP, DIP)。
- [Ch09 程式碼重構](./Part3-Principle/ch09_refactoring.md): 識別 Code Smells 並學習常見的重構技巧，提升代碼品質。

### Part IV: 生成式樣式 (Creational Patterns)
學習如何優雅地建立物件，解耦物件的創建與使用。
- [Ch11 工廠方法 (Factory Method)](./Part4-PatternCreation/ch11_fm.md): 使用子類別來延遲物件實例化的過程。
- [Ch12 抽象工廠 (Abstract Factory)](./Part4-PatternCreation/ch12_af.md): 提供產品家族的建立介面，確保產品間的相容性。
- [Ch13 獨體 (Singleton)](./Part4-PatternCreation/ch13_singleton.md): 確保類別僅存在一個實例，並提供全局存取點。

### Part V: 結構式樣式 (Structural Patterns)
探討如何組合物件與類別以形成更大的結構。
- [Ch14 轉接器 (Adapter)](./Part5-PatternStructure/ch14_adaptor.md): 讓介面不相容的類別能夠協同工作。
- [Ch15 虛實分離 (Bridge)](./Part5-PatternStructure/ch15_bridge.md): 將抽象部分與實作部分分離，使兩者各處變化。
- [Ch16 隻手乾坤 (Composite)](./Part5-PatternStructure/ch16_composite.md): 將物件組合成功樹狀結構以表示「整體-部分」的階層。
- [Ch17 神行百變 (Decorator)](./Part5-PatternStructure/ch17_decorator.md): 動態地將功能附加到物件上，比繼承更具彈性。

### Part VI: 行為式樣式 (Behavioral Patterns)
專注於物件間的溝通、責任分配與算法封裝。
- [Ch18 一法萬策 (Strategy)](./Part6-PatternBehavior/ch18_strategy.md): 封裝演算法，讓它們在執行時可以互相替換。
- [Ch19 剛中帶柔 (Template Method)](./Part6-PatternBehavior/ch19_template.md): 在父類別定義框架，並將部分實作交由子類別。
- [Ch20 眾觀其變 (Observer)](./Part6-PatternBehavior/ch20_observer.md): 建立一對多的相依關係，自動通知狀態更新。
- [Ch21 三足鼎立 (MVC)](./Part6-PatternBehavior/ch21_mvc.md): 經典的模型-視圖-控制器架構，分離資料、呈現與控制邏輯。
- [Ch22 逍遙遊 (Iterator)](./Part6-PatternBehavior/ch22_iterator.md): 提供一種方法順序存取集合物件，而不需暴露其內部呈現。
- [Ch23 物換星移 (State)](./Part6-PatternBehavior/ch23_state.md): 允許物件在內部狀態改變時改變其行為。
- [Ch24 七星聚會 (Mediator)](./Part6-PatternBehavior/ch24_mediator.md): 用一個中介物件來封裝一群物件的交互，降低耦合度。
- [Ch25 綿綿不絕 (Chain of Responsibility)](./Part6-PatternBehavior/ch25_CoR.md): 將請求的發送者與接收者解耦，讓多個物件都有機會處理請求。

### Part VII: 現代實務
- [Spring Framework 基礎](./Part7-SpringCase/Spring.md): 學習企業級開發常用的 Spring 框架。

---
© 2026 nlhsueh @ IECS, FCU
