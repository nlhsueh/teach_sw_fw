# 🏫 Spring University - 校園管理系統 (School Management System)

這是一個基於 Spring Boot 的校園管理系統，旨在演示物件導向設計 (OOD) 原則、設計樣式以及現代 Web 開發流程。

## 核心設計原則
本專案嚴格遵循 `Part2_Model/models/School.md` 的規格實作，主要展示以下概念：
- **介面 (Interface)**：實作 `Displayable` 介面，統一成員資訊顯示行為。
- **繼承與抽象 (Inheritance & Abstraction)**：透過抽象類別 `SchoolMember` 定義通用屬性，並衍生出 `Student` 與 `Teacher`。
- **組合 vs 聚合 (Composition vs Aggregation)**：
  - **組合**：`School` 與 `Department` 之間。
  - **聚合**：`Department` 與 `Course` 之間。
- **多型 (Polymorphism)**：前端處理資料時，能動態處理不同類型的學校成員。
- **DTO 模式**：所有與前端互動的資料皆透過 DTO 進行封裝，防止隱私洩漏。

## 功能模組

### 1. 行政管理 (Admin Portal)
- 視覺化管理系所、師資與課程清單。
- 監控全校成員組成與授課狀況。

### 2. 老師平台 (Teacher Portal)
- 查看個人授課清單。
- 即時對選修學生進行評分。
- 成績等級 (Letter Grade) 自動換算機制。

### 3. 學生中心 (Student Portal)
- 瀏覽全校課程與學分資訊。
- 線上選課功能。
- 個人成績單查詢。

## 環境需求
- **Java**: 21
- **Maven**: 3.x 或更高版本

## 執行方式
1. 執行 `src/main/java/com/example/demo/SpringDemoApplication.java`。
2. 開啟瀏覽器造訪 [http://localhost:8080/](http://localhost:8080/)。
3. 透過頂部的角色選擇器，分別體驗不同角色的操作流程。

## API 端點
- `GET /api/school/students`: 取得所有學生(含成績)。
- `GET /api/school/teachers`: 取得所有老師(含授課清單)。
- `GET /api/school/courses`: 取得所有課程。
- `POST /api/school/enroll`: 學生選課。
- `POST /api/school/grade`: 老師評分。
