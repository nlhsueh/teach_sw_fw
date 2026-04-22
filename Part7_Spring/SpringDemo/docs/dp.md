

# Design Patterns Application

## University system 目前具備以下功能
* Admin 新增刪除管理系所(Department)、師資(Teacher)與課程(Course)清單。
* 學生查詢課程、加退選課程、查看成績。
* 修改學生在某課程的成績

## 針對 University 這個例子，以下 design patterns 可被應用的狀況：

* Factory method: 
* Abstract factory: 
* Singleton
* Adapter: 透過 Adapter 與既存的 Web API 建立連結，並提供統一的介面供 Spring 應用程式呼叫。
* Strategy: 將不同的成績計算邏輯封裝成不同的 Strategy 物件，讓系統可以根據不同的需求選擇不同的成績計算邏輯。
* Template Method: 定義一個通用的課程建立流程，並將部分實作交由子類別。
* Observer: 建立一對多的相依關係，自動通知狀態更新。
* Chain of Responsibility
* Bridge
* Composite
* Decorator

