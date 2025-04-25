###### tags: `OOSE`

# Ch09 把脈清毒：程式碼重構

Martin Folwer 在他的書中 *Refactoring: improving the design of existing  code* 書中介紹了程式壞味道（bad smell）與重構（refactoring）方法。前者表示不良程式的現象，後者表示解決的方法。

這些壞味道與重構的方法可以說是設計樣式的基本元素，一個設計設計可能包含若干個重構方法，用以解決若干個程式壞味道。

## 把脈：程式壞味道

Matin Folwer 共提到 21 個程式壞味道，在此我們介紹部分。

- **重複的程式碼 Duplicated Code**。重複的程式碼應該抽出來集中在一個類別（或元件）中，需要的時候再去繼承或呼叫。重複的程式碼的維護性很低：我們常常改了這個忘了改另一個。
- **冗長的方法 Long Method**。一個1000 行的程式碼好不好看得懂？好不好維護？一個方法通常代表一個處理的流程或演算法，大到某個程度後我們就必須要學會「抽象」- 把部分的流程抽象到另一個方法裡。
- **大類別 Large Class**。同理，一個大類別包含很多的屬性、方法讓程式員難以理解類別的責任，應該把責任做分割，交給另一個類別來做。
- **太長的參數列 Long Parameter List**。參數太多太長時（想像一下，10個參數），參數代表一個方法可以調整的靈活度，有其複雜度，太多的參數令人難以理解該方法的彈性。過多的參數也會引來冗長的方法。一樣，我們應該把部分的參數彙整起來，抽象成另一個物件類別。
- **發散變更 Divergent Change**：一個類別有太多的變更原因而造成內聚力差。一個類別最好能有一個專一的功能，也就是內聚力要高，類別內的屬性和方法彼此間是有關係的。亦即，不要炒大鍋菜一樣把不相關的功能都擠在一個類別內。
- **散彈槍手術 Shotgun Surgery**：一個變更，要改很多「其他」模組。想像一下一個「班級人數」的值散落在10 個模組裡，當這個人數值需要改變時，你需要翻箱倒櫃的修改這十個模組。如果這個值是放在一個設定檔中，模組啟動時去讀這個檔就好了，如此修改就會少很多。
- **依戀情結 Feature Envy**：一個計算要取好多其他類別的值。一個內聚力強的類別其計算時主要會用到該類別的屬性，例如一個 Circle 的物件，有一個 area() 的方法來計算他的面積，會用到他內部的屬性 ridius，少許的引用其他類別的資料來運算當然難免，但如果過份的依賴大量其他的類別，那麼你的抽象化可能就出現了問題：是不是這個計算應該移到別的類別中？
- **資料泥團 Data Clumps**：常常會一起出現的資料卻沒有抽象成一個類別型態。例如說住址，有一群的城市、街道、ZIP code、號等字串出現，為什麼不把它抽象成 Address 的類別呢？
- **基本型別偏執 Primitive Obsession**：不用小類別，堅持只用基本型別。例如「錢」的這個變數，一開始你可能會用 int 來代表，又發現需要描述是哪個國家的幣值，所以又加了一個變數 String country。這會讓程式碼變得凌亂難懂，其實我們可以宣告一個小類別 Money，裡面包含這兩個屬性。
- **Switch 敘述句**：太多的 switch case，不會用多型。Switch 有一個缺點，當你要新增一個 case 選項時，你需要修改程式碼。如果我們本來就預期會有 case 的改變，那麼就用多型吧，至少可以不在修改程式的情況下，透過擴充來完成彈性的設計。
- **平行繼承體系 Parallel Inheritance Hierarchies**：可以說是散彈槍手術的一種：當你為某一個類別建立一個子類別時，你也需要為另一個類別建立子類別。假設你有兩個繼承樹，分別為 `SalariedEmployee`與 `HourlyEmployee`，其下分別有 `Engineer`, `Analyzer`, `Manager` 等類別，當我們要新增一個 `SoftwareEngineer` 類別時，必須在這兩個繼承樹分別新增，這就是一種不好的設計。
- **不實用的一般性 Speculative Generality**: 預留的太多不實用的擴充點。為了滿足「擴充性上的彈性」，我們常常會把程式寫的複雜些，但是仔細想一想，是不是這些擴充真的需要？還是為了設計而設計？
- **暫時欄位 Temporary Field**: 有一些暫時欄位只對某些特殊的方法或特殊時機才會用到，令人難以了解他的意義。例如為了不想傳太多參數就把某些值以 instance variable 的方式寫在類別內。
- **過度的訊息串 Message Chains**：`a.getB().getC().getD()` 當訊息串過長時可能是功能封裝的有問題，可能要考慮直接的訊息傳遞。
- **過度的中間人 Middle Man**：太多的方法都是透過委託來進行，該類別的方法封裝要考慮改寫。
- **狎暱關係 Inappropriate Intimacy**：類別間的過於親密（頻繁）的屬性存取，這可能是責任或模組切割的不合適所造成。可以考慮把其中一個的方法整併到另一類別中，若是誰整併誰都不合適，也可能增加一個中間人物件來降低親密關係。
- **同功能不同介面 Alternative Classes with Different Interfaces**：因為有不同的工程師做相同的工作，而造成相同的功能卻有多個不同的介面與實作。
- **資料類別 Data Class**：類別內僅有欄位，或是存取這些欄位的 `getter`, `setter`。思考這些資料的特性，為他們添增合適的方法來解決這個問題。
- **防臭的註解 Comments**：有些註解就像防臭劑一樣，因為程式碼寫的太亂太差，只好不斷的添加註解來說明。應該適當的為功能與類別做拆解模組化、賦與合適的名稱來幫助理解，而不是一味的加上註解。



## 清毒：程式碼重構

重構方法一共有六大類，25 種形式 72 個重整的方法：

- 合：Compose Method，方法組裝。
- 移：Move features between object，特性移動，亦即在物件之間移動特性（屬性或方法）。
- 組：Organize data，資料組織。
- 化：Simply conditional expression，條件簡化。
- 簡：Make method calls simpler，呼叫簡化。
- 通：Deal with generalization，一般化處理。


25 種形式包含：Change 改變、Encapsulate 封裝、Extract 萃取、Hide 隱藏、Move 移動、Pull up 拉上、Push down 推下、Replace 取代等。

注意這些重構方法著重在動作，沒有絕對的對錯，有些還恰好對立，例如 extract method 要你把方法抽出來，inline method 則要你把方法合為一體，其使用的情境要看你的應用程式而定。

### 方法組裝


- Extract Method: 把功能萃取出來，形成另一個方法。
- Inline Method: 直接使用該方法的功能，不將其包裝為一個方法，因為直接使用更直覺。Inline method 和 extract method 兩者恰好是相反。

```java=
class Student {
   boolean pass() {
      return isGoodGrade()? true: false;
   }
   boolean isGoodGrade() {
      return grade > 60
   }   
}
// 使用 inline method 重構後
class Student {
   boolean pass() {
      return grade > 60? true: false;
   }
}
```


- Introduce Explaining Variables: 導入有意義的變數。特別是在一群條件判斷式中。


```java=
class Student {
   boolean getScholarship() {
      if (sType == "M" && g1 > 60 && g2 > 70)
         return true;
    }
}
// introduce explaining variable 重構後
class Student {
   boolean getScholarship() {
      boolean isMasterStudent = (sType == "M");
      boolean allPass = (g1 > 60 && g2 > 70);
      if (isMasterStudent && allPass) 
         return true;
    }     
}   
```

- Inline Temp: 不宣告一個變數來表達一個簡單的表示式 (expression)，直接用就好。
- Split Temporary Variable: 不用某個 local 變數來儲存不同的計算意義，而是每次都給一個有意義的變數名稱。例如我們常常一會兒 `len = heigh + width`, 一會兒又 `len = now() - age`, 容易造成混亂，不好。
- Remove Assignments to Parameters: 不改變 parameter 的值，而是用另一個變數來儲存該值，再做運算。


### 特性移動


- Move Method: 移動方法到別的類別。
- Move Field: 移動屬性到別的類別。
- Extract Class: 當一個類別負責的功能太多，將獨立的功能萃取出成為另一個類別。
- Inline Class: 合併類別，將兩個相近的類別合併，不分成兩個類別。
- Remove Middle Man: 移除中間人，讓兩個物件直接溝通。


### 資料組織

- Replace Data Value with Object: 某一資料具備自己的屬性與行為，那麼就為他封裝為一個類別，而不是一個字串。例如 People 可宣告為一個類別，不要僅宣告為一個字串描述其姓名。
- Change Value to Reference: 一些物件都有相同的值，不要讓他們各自擁有資料，建立一個物件讓他們共同參考。
- Replace Array with Object: 將一個存有不同型態的陣列，改以物件來設計。

```java=
String student [] = {"Nick", "P1010", "Master"}
// 重構後
class Student {
   String name;
   String SSN;
   String degree;
}    
```
- Encapsulate Field: 將公開的屬性資料，宣告為私有，並且宣告 `getter`, `setter` 來存取該屬性。
- Encapsulate Collection: 一個類別內具備有集合物件（Set），原本提供此集合物件的 `getter` 與 `setter`, 將之重整為 read-only 的 `getter`，或是對此物件的動作。

```java=
class Student {
   getCourse(): Set;
   setCourse(Set); 
}
// encapsulate collection 重構後：
class Student {
   getCourse(): SetIterator; //read-only getter
   addCourse(Course); // operation to Course, not set
   removeCourse(Course) 
}   
```

- Replace Subclass with Fields: 兩個子類別的差異很小，僅是用一個變數來區分他們的差異，可濃縮為一個父類別，並也用一個變數來區分即可。

```java=
class Master extends Student {
   static int getDegree() {return 3;}
}   
class Undergradute extends Student {
   static int getDegree() {return 2;}
}   
// 重構後
class Student {
   int degree;
   public Student (int s) { degree = s;}
   int getDegree() { return degree; }
}   
```


### 條件簡化

- Consolidate Duplicate Conditional Fragments: 相同的程式片段存在於所有的條件流中，則可以將之移出。

```java=
if (isFemale()) {
   tall > 170? return "basketball": "baseball";
   orgTeam();
}   
else {
   tall > 190? return "basketball": "baseball";
   orgTeam();
}   
// 重構後
if (isFemale())
   tall > 170? return "basketball": "baseball";
else
   tall > 190? return "basketball": "baseball";  
orgTeam(); // 移出來
```
- Replace Nested Conditional with Guard Clauses: 避免巢狀的判斷句，用扁平的判斷來取代。

```java=
boolean isPass() {
   boolean result;
   if (isPhD) 
      result = computePhDGrade();
   else 
      if (isPartTimeMaster)    
         result = computePartTimeMasterGrade()
      else
         if (isMaster)
            result = computeMasterGrade()
   result = computeGrade()
   return result;
}
//重構後
boolean isPass() {
   if (isPhD)
      return computePhDGrade();
   if (isPartTImeMaster)
      return computePartTimeMasterGrade()
   if (isMaster)
      return computeMasterGrade()
   return computeGrade();
}               
```

- Replace Conditional with Polymorphism: 用多型來取代判斷。

```java=
class Student {
   int getGrade() {
      swith (type) {
         case PHD:
             return getQualityExam();
         case MASTER:
             return exam*0.5 + report*0.5;
         case UNDER:
             return exam*0.7 + report*0.3;        
      }
   }   
}
//重構後
class PhDStudent extends Student {
   int getGrade() {
      return getQualityExam();
   }   
}
class MasterStudent extends Student {
   int getGrade() {
      return exam*0.5 + report*0.5;
   }   
}
...
```      

### 呼叫簡化

Seperate Query from Modifier: 將查詢與修改的方法分開來，讓每一個方法有一個明確獨立的功能。

```java=
int getGradeAndSetLevel(} { ... }
// 重構後
int getGrade() {...}
void setLevel() { ... }
```
- Parameterize Method: 將多個方法彙整為一個方法，以參數來進行一般化。
- Replace Parameter with Explicit Methods: 一個方法區分為若干個不同的部份，由參數來決定執行哪一個部分。重構後用明確的方法名稱來取代個部分，讓程式更簡潔明確。
```java=
setValue(String title, int v} {
   if (title == "grade")
      grade = v;
   else if (title == "level") 
      level = v;
}
// 重構後
setGrade(int grade) {grade = g;}
setLevel(int lv) { level = lv;}          
```
- Introduce Parameter Object: 方法包含了一大群的參數，可用一個物件來取代這些參數。
```java=
void exam(Time startTime, Time endTime) {...}
// 重構後
void exam(TimeRange examTime) {...}
```
- Remove Setting Method: 當我們要設計一個 Immutable object 時，物件的屬性值只有在其建構時設定，不提供 setter 的方法以修改其內部值。
- Hide Method: 將公開的方法設為私有，不要外部的物件呼叫，以避免不需要的錯誤。
- Replace Error Code with Exception: 當程式發生例外時，不回傳錯誤碼，而是拋出例外。

```java=
int computeGrade() {
   ....
   if (grade >100 || grade < 0)
      return -1;
   return 0;
}
// 重構後
void computeGrade() throws Exception {
   ...
   if (grade > 100 || grade < 0) 
      throw new Exception("Error grade");
   ...
}         
```

### 一般化處理


- Pull Up Field: 當兩個子類別有相同的屬性，則將相同的屬性往上提至父類別。
- Pull Up Method: 當兩個子類別有相同的方法，則將相同的方法往上提至父類別。
- Push Down Method: 當父類別的方法並不適用所有的子類別，將該方法往下移至子類別。

```java=
class Student {
   void attendOralDefense() {...}
}
// 重構後
class MasterStudent extends Student {
   void attendOralDefense() {...}
   ...
}
class UnderGraduateStudent extens Student {
   ...
}
```

- Extract Superclass: 當有兩個類別具備相似的屬性與方法時，建立一個父類別，並且讓這兩個類別繼承之。
- Replace Inheritance with Delegation: 當子類別僅繼承部分的行為，改以委託來設計，該繼承的部份改用委託來完成。
- Replace Delegation with Inheritance: 當物件將多半的工作委託給另一物件，可直接繼承之。


## 綜合練習

- ex01 軟體重構後會改善其功能嗎？為什麼？
- ex02 下列何者為Shotgun Surgery的概念?	(單選)
  - [ ] 1. 一個類別會因為因應太多的變更原因而需修改
  - [ ] 2. 每次為因應同一種變更，你必須同時在許多類別上做出許多修改
  - [ ] 3. 常常會一起出現的資料群卻沒有抽象呈一個類別型態	
  - [ ] 4. 一個計算要取好多其他類別的值。
- ex03 關於"重複程式碼"壞味道的重構方法，底下說明何時有誤？	(單選)
  - [ ] 1. 若在同一個class中有重複的程式碼，我們可透過提取函式(Extract Method)將重複的部分抽出來變成method。
  - [ ] 2. 若不相干的兩個類別中具有重複的程式碼，我們可透過提取類別(Extract Class)將重複的部分先抽取出來並獨立成一個新的class。
  - [ ] 3. 若在兩個子類別(同一個父類別下)具有重複的程式碼，可透過提取類別(Extract Class)將重複的部分先抽取出來，並獨立成一個新的父類別。
  - [ ] 4. 若重複的部分相似但不完全相同，仍可透過提取函式(Extract Method)將重複且相同的部分抽取出來。	
	
- ex04 關於"過多的參數"壞味道，請問底下哪一個敘述有誤？	(單選)
  - [ ] 1. 太長的參數列難以理解
  - [ ] 2. 太長的參數列執行效率差 
  - [ ] 3. 對於具有過多參數的method，呼叫此method之程式容易形成呼叫參數順序錯誤的bug
  - [ ] 4. 對於具有過多參數的method，一旦需要更多的輸入參數，在修改這個method時，也要修改呼叫的程式，造成維護的困難	
	
- ex05 對於"巨大的類別"壞味道之重構方法，底下敘述何者有誤？(單選)
  - [ ] 1. 可以將每個method均獨立成一個類別，以確保功能間不互相干擾。
  - [ ] 2. 可以運用Extract Class方法將數個相關聯的變數一起提煉至新的class中。
  - [ ] 3. 可以使用Extract Subclass，將原本的巨大類別拆解成父類別與子類別。
  - [ ] 4. 可先觀察客戶端程式如何用原本的類別，先透過Extract Interface為每一種使用方法提煉出一個interface。	
	
- ex06 散彈槍手術 Shotgun Surgery 可以透過哪些重構方法來解決？
- ex07 以下程式碼可能有哪個問題？該如何重構之？
```java=
int computeGrade() {
   ....
   if (grade >100 || grade < 0)
      return -1;
   return 0;
}
```

- ex08 以下程式碼可能有哪個問題？該如何重構之？

```java=
class Student {
   getCourse(): Set
   setCourse(Set) 
}
```

- ex09 以下程式有何缺點？可以如何重構？
```java=
class Student {
   int getGrade() {
      swith (type) {
         case PHD:
             return getQualityExam();
         case MASTER:
             return exam*0.5 + report*0.5;
         case UNDER:
             return exam*0.7 + report*0.3;        
      }
   }   
}
```

可註冊 [物件導向設計II- 重構](https://sec.openedu.tw/courses/course-v1:SEC+SE101.2+201709/about)，觀看教學影片並回答相關問題。
