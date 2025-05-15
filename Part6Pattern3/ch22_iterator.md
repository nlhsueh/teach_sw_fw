###### tags: `OOSE`

# Ch22 逍遙遊：Iterator
   
## 22.1 目的與動機


提供一個能夠循序瀏覽某個集合體的內部資料，而不必去了解其內部結構的方法。


*Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation*

## 22.2 動機

在程式設計中，我們常常會遇到必須要叫出一一叫出陣列或集合物件內的內容，但基於資料隱藏的原則，這些集合物件的內容結構應該避免透露。如果只是要其他物件取得該集合物件的值，而非修改，那們就不該把集合物件的參考直接傳過去。Iterator 樣式的目的，便是透過抽離出瀏覽方法的方式，讓使用者不必去了解目標內容物的結構，就可以依著規定的方式取得內容物件。

> 只可遠看，不可褻玩焉

## 22.3 結構與方法


![](https://hackmd.io/_uploads/H1HHwJSE3.png)
FIG: Iterator 

不同結構的 Aggregate 需要不同的 Iterator 來瀏覽，因此可以運用 factory method 來依據不同型態的複合物件產生不同的瀏覽物件。例如MapCollection 需要的瀏覽器是 MapIterator、ListCollection 需要的是 ListIterator。Collection 並沒有決定要產生哪一個 Iterator。如此的結構是為 Polymorphic Iterator, 也是較為普遍的使用方法。

![](https://hackmd.io/_uploads/SyjPw1HE3.png)

FIG: Polymorphic iterator


### 22.3.1 參與者


- `Aggregate/Collection`：複合物件。定義如何取得瀏覽者物件的方法，例如 `createIterator()`，除了產生 `iterator` 以外，`Aggregate` 有對元素處理的方法，包含 `addElement()`, `removeElement()` 等。
- `Iterator`：瀏覽者介面，裡面定義一個複合物件的瀏覽方法，例如 `first()`, `next()` 等，還有判斷是否瀏覽完畢的檢查方法，例如 `isDone()`。
- `ConcreteIterator`：具體的瀏覽者類別，例如 `ListIterator`, `MapIterator` 等。
- `ConcreteAggregate`：具體的複合物件，例如 `ListCollection`, `MapCollection` 等。



### 22.3.2 程式樣板

```java=
Iterator it = aggregateObject.iterator();

while (it.hasNext()) {
   Object = it.next();
   ...
}   
```

### 效益
可以有效的建立一個瀏覽物件的集合內容，不必因為新的內容增加而必須去新增方法去引用他，不必公開其內部結構。

## 22.4 範例

### Java Vector, ArrayList, HashMap

在 Java1.2 之後已經將 Iterator 的方法納入其中了，包含 Vector, ArrayList, HashMap 的瀏覽方法，如下：

```java
package iterator;

import java.util.*;

public class IterationDemo {

	public static void main(String[] args) {
		// Vector並且增加內容
		Vector<String> v = new Vector<String>();
		v.addElement(new String("Hello"));
		v.addElement(new String("Taichung"));
		v.addElement(new String("Have a nice day"));
		// 瀏覽 Vector內的內容
		Iterator<String> it1 = v.iterator();
		System.out.print("Vector 內的內容為:");
		traverse(it1);

		// ArrayList
		ArrayList<String> v2 = new ArrayList<String>();
		v2.add(new String("Hello"));
		v2.add(new String("Taipei"));
		v2.add(new String("Good morning"));
		// 瀏覽 ArrayList 內的內容
		Iterator<String> it2 = v2.iterator();
		System.out.print("\nArrayList 內的內容為:");
		traverse(it2);

		// HashMap
		HashMap<String, Integer> v3 = new HashMap<String, Integer>();
		v3.put("John", new Integer(172));
		v3.put("Mary", new Integer(168));
		v3.put("Nick", new Integer(180));
		// 瀏覽 HashMap 的 Key
		Iterator<String> it3 = v3.keySet().iterator();
		System.out.print("\nHashMap 內的內容為:");
		traverse(it3);
	}

	static void traverse(Iterator<String> e) {
		while (e.hasNext()) {
			System.out.print(e.next() + ", ");
		}
	}

}
```

![](https://hackmd.io/_uploads/rJwBO1S43.png)


[Get the code](\codeURL/iterator/IterationDemo.java)


## 22.3.3 練習


## 22.CHK

1. 要取得一個集合物件內所有物件的，為何不直接從此集合物件取值？還需要先取得其瀏覽物件 (iterator)？
	A) 瀏覽物件的功能較為強大
	B) 瀏覽物件是 View, 透過如此可以將 Model 與 View 分離
	C) 可以避免修改到集合物件的內容
	D) 避免傳遞過多的資料，速度較快
	
2. Polymorphic iterator 是結合哪兩個樣式？(選兩個)
	A) iterator
	B) mediator
	C) decorator
	D) factory method

3. 下列的程式會計算一群學生的平均成績，你覺得有什麼問題？可以怎麼改善？
```java
class Student {
    int score;
    int getScore() { return score;}
}
 ...
class GradeComputer {
     void computeAverage(ArrayList<Student> s) {
        ... 
     }
}
```

4. 同上，如果我們用 Iterator 來做，第二行的「?」部分為何？
```java
double getAverage(Iterator<Student> iterator) {
     ?
}   
```

## 22.EX

### 22.ex01
請完成以下程式

```java
class Student {...}

class Course {
   HashMap<Student, Integer> gradeBook; // store all grade of students
      
   // 取得成績的瀏覽物件
   public ? getGradeIterator() {
      ? // Hint: 查看 API, HashMap 如何取得 value 的 iterator?
   }  
   void addGrade(Student s, int grade) {
      ?
   }
}

class GradeComputer {
   void computeAverage(Course c) {   
       ?
       System.out.println(averageGrade);
   }
}

public class MainApp {
   public static void man(Strings[] a) {
      Course math = new Course();
      // add some students and their grade to the course
      ?
      GradeComputer gc = new GradeComputer();
      gc.computerAverage(?);
   }
}      
```