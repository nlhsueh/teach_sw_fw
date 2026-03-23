import base64
import urllib.request
import os

def build_category(path):
    return f"""  <question type="category">
    <category>
      <text><![CDATA[$course$/{path}]]></text>
    </category>
  </question>
"""

def build_multichoice(name, text, correct, wrongs, image=None):
    if image:
        b64_data = fetch_and_encode(image['url'])
        img_name = image['name']
        img_tag = f'<br/><img src="@@PLUGINFILE@@/{img_name}" alt="UML Diagram" />'
        text += img_tag
        file_tag = f'\n      <file name="{img_name}" path="/" encoding="base64">{b64_data}</file>'
    else:
        file_tag = ''

    xml = f"""  <question type="multichoice">
    <name><text><![CDATA[{name}]]></text></name>
    <questiontext format="html">
      <text><![CDATA[{text}]]></text>{file_tag}
    </questiontext>
    <single>true</single>
    <shuffleanswers>true</shuffleanswers>
    <answernumbering>abc</answernumbering>
"""
    xml += f"""    <answer fraction="100"><text><![CDATA[{correct}]]></text></answer>\n"""
    for w in wrongs:
        xml += f"""    <answer fraction="0"><text><![CDATA[{w}]]></text></answer>\n"""
    xml += "  </question>\n"
    return xml

def build_essay(name, text):
    return f"""  <question type="essay">
    <name><text><![CDATA[{name}]]></text></name>
    <questiontext format="html">
      <text><![CDATA[{text}]]></text>
    </questiontext>
    <responseformat>editor</responseformat>
    <responserequired>1</responserequired>
    <responsefieldlines>15</responsefieldlines>
  </question>
"""

import ssl

def fetch_and_encode(diagram_code):
    ctx = ssl.create_default_context()
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    
    encoded = base64.urlsafe_b64encode(diagram_code.encode('utf-8')).decode('utf-8')
    url = f"https://mermaid.ink/img/{encoded}"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    print(f"Fetching image: {url[:50]}...")
    try:
        with urllib.request.urlopen(req, context=ctx) as response:
            return base64.b64encode(response.read()).decode('utf-8')
    except Exception as e:
        print(f"Error fetching image: {e}")
        return ""

def write_quiz(filename, category, questions):
    with open(filename, 'w', encoding='utf-8') as f:
        f.write('<?xml version="1.0" encoding="UTF-8"?>\n<quiz>\n')
        f.write(build_category(category))
        for q in questions:
            if q['type'] == 'mc':
                f.write(build_multichoice(q['name'], q['text'], q['correct'], q['wrongs'], q.get('image')))
            elif q['type'] == 'essay':
                f.write(build_essay(q['name'], q['text']))
        f.write('</quiz>\n')

ch01_qs = [
    {
        'type': 'mc', 'name': 'MC01',
        'text': '關於 Java 的「類別 (Class)」與「物件 (Object)」，下列敘述何者正確？',
        'correct': '類別是物件的藍圖，同一個類別可以建立多個物件。',
        'wrongs': ['物件是類別的藍圖，類別是物件的實例。', '一個類別在程式中只能建立一個物件。', '物件可以在執行時修改其所屬類別的定義。']
    },
    {
        'type': 'mc', 'name': 'MC02',
        'text': '在 Java 中，下列哪一種是「原生型態 (Primitive Type)」，而非類別型態？',
        'correct': 'int',
        'wrongs': ['String', 'Integer', 'ArrayList']
    },
    {
        'type': 'mc', 'name': 'MC03',
        'text': '以下 Java 程式碼執行後，<code>Counter.count</code> 的輸出值為何？<br><pre>class Counter {\n    static int count = 0;\n    Counter() { count++; }\n}\npublic class Main {\n    public static void main(String[] args) {\n        new Counter(); new Counter(); new Counter();\n        System.out.println(Counter.count);\n    }\n}</pre>',
        'correct': '3',
        'wrongs': ['0', '1', '編譯錯誤，count 必須宣告為 public 才能存取。']
    },
    {
        'type': 'mc', 'name': 'MC04',
        'text': '關於 <code>private</code> 存取修飾子，下列敘述何者正確？',
        'correct': '<code>private</code> 成員只能在宣告它的同一個類別內部被存取。',
        'wrongs': ['<code>private</code> 成員可以在同一個 package 內的所有類別存取。', '<code>private</code> 成員可以被子類別繼承並使用。', '<code>private</code> 方法可以被其他類別透過物件呼叫。']
    },
    {
        'type': 'mc', 'name': 'MC05',
        'text': '以下程式碼中，<code>this</code> 關鍵字的主要用途為何？<br><pre>class Person {\n    String name;\n    Person(String name) {\n        this.name = name;\n    }\n}</pre>',
        'correct': '區分同名的類別屬性與建構子參數，this.name 指向物件的屬性。',
        'wrongs': ['建立一個新的 Person 物件。', '呼叫父類別的建構子。', 'this 等同於 static，代表類別本身。']
    },
    {
        'type': 'mc', 'name': 'MC06',
        'text': 'Java 方法的「值傳遞 (Pass by Value)」特性，對參考型態（如物件）的影響為何？',
        'correct': '傳遞的是物件的「參考值（記憶體位址）」，因此方法內對物件屬性的修改會影響原物件。',
        'wrongs': ['傳遞物件時會建立物件的完整副本，方法內的修改完全不影響原物件。', '參考型態採用的是傳參考 (pass by reference)，與原生型態不同。', 'Java 不允許將物件作為方法參數傳入。']
    },
    {
        'type': 'mc', 'name': 'MC07',
        'text': '下列哪一種方法重載（Method Overloading）的定義是「不合法」的？',
        'correct': '在同一個類別內定義兩個名稱相同、參數完全相同，但回傳型別不同的方法。',
        'wrongs': ['在同一個類別內定義兩個名稱相同、但參數型別不同的方法。', '在同一個類別內定義兩個名稱相同、但參數數量不同的方法。', '在同一個類別內同時擁有 add(int, int) 與 add(double, double) 兩個方法。']
    },
    {
        'type': 'mc', 'name': 'MC08',
        'text': '關於 Java <code>static</code> 成員，下列敘述何者「錯誤」？',
        'correct': 'static 方法可以直接存取同一類別中非靜態（instance）的成員變數。',
        'wrongs': ['static 變數屬於整個類別，所有物件共享同一份資料。', 'static 方法不需要建立物件實例即可透過類別名稱呼叫。', 'static 變數常被用來實作計數器或儲存共用常數。']
    },
    {
        'type': 'essay', 'name': 'SA01',
        'text': '請說明「封裝 (Encapsulation)」的概念，並舉例說明在 Java 中如何利用 private 屬性與 getter/setter 方法實現封裝，以及封裝帶來哪些好處。'
    },
    {
        'type': 'essay', 'name': 'SA02',
        'text': '請解釋 Java 中「原生型態 (Primitive Type)」與「類別型態 (Reference Type)」在記憶體配置上的差異（Stack vs. Heap），並說明當一個物件被傳入方法時，為何對物件屬性的修改會反映到原物件，但對原生型態變數的修改則不會。'
    }
]

ch02_qs = [
    {
        'type': 'mc', 'name': 'MC01',
        'text': '關於 Java 的繼承，下列敘述何者正確？',
        'correct': '<code>final</code> 關鍵字可以用來防止一個類別被繼承。',
        'wrongs': ['Java 支援類別的多重繼承（一個類別可以同時 extends 兩個父類別）。', 'private 方法可以被子類別覆寫（override）。', '抽象類別（abstract class）內不能包含已實作的具體方法。']
    },
    {
        'type': 'mc', 'name': 'MC02',
        'text': '以下程式碼中，<code>Bike</code> 的建構子呼叫 <code>super()</code> 時會發生什麼問題？<br><pre>class Vehicle {\n    int speed;\n    public Vehicle(int speed) { this.speed = speed; }\n}\nclass Bike extends Vehicle {\n    public Bike(int seatHeight) {\n        super(); // 問題在這裡\n    }\n}</pre>',
        'correct': '編譯錯誤，因為 Vehicle 沒有無參數建構子，super() 無法呼叫。',
        'wrongs': ['執行時錯誤，因為 speed 沒有被初始化。', '正常執行，super() 呼叫的是 Object 的建構子。', '編譯錯誤，因為子類別的建構子不能使用 super()。']
    },
    {
        'type': 'mc', 'name': 'MC03',
        'text': '以下程式碼執行後，<code>a.m1()</code> 會輸出什麼？<br><pre>class A { void m1() { System.out.println("A"); } }\nclass B extends A { void m1() { System.out.println("B"); } }\nA a = new B();\na.m1();</pre>',
        'correct': 'B',
        'wrongs': ['A', '編譯錯誤，A 型態的變數不能指向 B 的物件。', '執行時錯誤（ClassCastException）。']
    },
    {
        'type': 'mc', 'name': 'MC04',
        'text': '關於「向下轉型（downcasting）」，下列何者正確？',
        'correct': '向下轉型在語法上需要明確強制轉型，若執行時物件的實際型態不符，會拋出 ClassCastException。',
        'wrongs': ['向下轉型是自動的，不需要強制轉型語法。', '向下轉型時如果物件實際型態不符，會發生編譯錯誤而非執行時錯誤。', '向下轉型等同於向上轉型，不影響執行結果。']
    },
    {
        'type': 'mc', 'name': 'MC05',
        'text': '下列關於抽象類別（abstract class）的敘述，何者「錯誤」？',
        'correct': '抽象類別必須至少包含一個抽象方法，否則無法宣告為 abstract。',
        'wrongs': ['抽象類別無法直接使用 new 建立物件。', '抽象類別可以同時包含抽象方法與已實作的具體方法。', '繼承抽象類別的子類別，若未實作所有抽象方法，則該子類別也必須宣告為 abstract。']
    },
    {
        'type': 'mc', 'name': 'MC06',
        'text': '在以下的多型範例中，<code>render(new Triangle())</code> 被呼叫時，程式的行為為何？<br><pre>abstract class Shape { abstract void draw(); }\nclass Circle extends Shape { void draw() { System.out.println("圓"); } }\nclass Square extends Shape { void draw() { System.out.println("方"); } }\nclass App {\n    void render(Shape s) { s.draw(); }\n}</pre>',
        'correct': '因為 Triangle 類別不存在，這行程式碼將產生編譯錯誤，但 render 方法本身不需要修改。',
        'wrongs': ['因為 render 方法只接受 Shape 的直接子類別，Triangle 無法傳入。', '程序可以正常執行，但輸出將是空的。', '程序會執行 Shape 的預設 draw() 方法。']
    },
    {
        'type': 'mc', 'name': 'MC07',
        'text': '以下關於 Java 介面（interface）的敘述，何者正確？',
        'correct': '一個類別可以同時實作（implements）多個介面，藉此達到概念上的多重繼承。',
        'wrongs': ['介面中可以定義 private 屬性（instance variables）供實作類別使用。', '實作介面的類別可以選擇性地不實作介面中的某些方法。', '介面與抽象類別的唯一差別，在於介面不能包含任何方法。']
    },
    {
        'type': 'mc', 'name': 'MC08',
        'text': '關於「動態綁定（Dynamic Binding）」，下列敘述何者最正確？',
        'correct': '當一個父類別型態的變數呼叫被覆寫的方法時，JVM 在執行時期根據物件的「實際型態」決定執行哪個版本的方法。',
        'wrongs': ['動態綁定發生在編譯時期，由編譯器決定要呼叫哪個方法。', '動態綁定只適用於 static 方法。', '動態綁定與多型無關，它只是一個效能最佳化的機制。']
    },
    {
        'type': 'essay', 'name': 'SA01',
        'text': '請說明「抽象類別（Abstract Class）」與「介面（Interface）」的異同，並各舉一個適合使用的情境。例如：何時應該使用抽象類別？何時應該使用介面？為什麼 Java 允許一個類別實作多個介面，但只能繼承一個類別？'
    },
    {
        'type': 'essay', 'name': 'SA02',
        'text': '請解釋「動態綁定（Dynamic Binding）」與「多型（Polymorphism）」的關係，並以以下情境說明：假設有一個 Animal 抽象類別，包含抽象方法 speak()，以及繼承它的 Dog 和 Cat 子類別。若以 Animal a = new Dog() 宣告後呼叫 a.speak()，請說明 JVM 如何決定執行哪個 speak() 方法，以及這種設計帶來什麼好處。'
    }
]

ch04_qs = [
    {
        'type': 'mc', 'name': 'MC01',
        'text': '請觀察下圖：關於 <code>brake()</code> 方法末尾的 <strong>*</strong> 符號，代表什麼意涵？',
        'correct': '這是一個抽象方法（Abstract Method），子類別必須覆寫它。',
        'wrongs': ['這是一個靜態方法（Static Method），屬於類別而非物件。', '這是一個重載方法（Overloaded Method）。', '這個方法有預設的回傳值，不需要明確 return。'],
        'image': {
            'name': 'q1.png',
            'url': 'classDiagram\n    class Car {\n        -brand: String\n        -speed: int\n        #fuelType: String\n        +maxPassengers: int$\n        +accelerate() void\n        +brake() void*\n    }'
        }
    },
    {
        'type': 'mc', 'name': 'MC02',
        'text': '請觀察下圖：<code>maxPassengers: int$</code> 中的 <strong>$</strong> 符號代表什麼？',
        'correct': 'static 靜態成員，屬於類別本身，所有物件共享同一份資料。',
        'wrongs': ['private 私有成員，只有類別內部可以存取。', 'final 常數，宣告後不可更改其值。', 'abstract 抽象成員，需要子類別實作。'],
        'image': {
            'name': 'q1.png',
            'url': 'classDiagram\n    class Car {\n        -brand: String\n        -speed: int\n        #fuelType: String\n        +maxPassengers: int$\n        +accelerate() void\n        +brake() void*\n    }'
        }
    },
    {
        'type': 'mc', 'name': 'MC03',
        'text': '請觀察下圖：<code>Library</code> 與 <code>Book</code> 之間是空心菱形，<code>Book</code> 與 <code>Loan</code> 之間是實心菱形。以下敘述何者正確？',
        'correct': 'Library–Book 為聚合（Aggregation）：書籍可獨立於圖書館存在；Book–Loan 為組合（Composition）：借閱紀錄的生命週期完全依附於書籍。',
        'wrongs': ['Library–Book 為組合（Composition）；Book–Loan 為聚合（Aggregation）。', '兩者都是聚合（Aggregation），差別只在多重性設定。', '空心菱形代表依靠關係（Dependency），實心菱形代表關聯（Association）。'],
        'image': {
            'name': 'q2.png',
            'url': 'classDiagram\n    direction LR\n    class Library\n    class Book\n    class Loan\n    Library "1" o-- "0..*" Book : manages\n    Book "1" *-- "0..*" Loan : generates'
        }
    },
    {
        'type': 'mc', 'name': 'MC04',
        'text': '請觀察下圖：關聯線上的 <strong>1..5</strong> 多重性代表什麼業務規則？',
        'correct': '每位學生最少選修 1 門課，最多選修 5 門課。',
        'wrongs': ['每門課最多只能有 5 位學生修課。', '學生與課程之間最多只能有 5 條關聯線。', '每位學生恰好選修 5 門課，不能多也不能少。'],
        'image': {
            'name': 'q3.png',
            'url': 'classDiagram\n    direction LR\n    class Student\n    class Course\n    Student "1" --> "1..5" Course : enrolls'
        }
    },
    {
        'type': 'mc', 'name': 'MC05',
        'text': '請觀察下圖：以下哪一行 Java 程式碼「無法」通過編譯？',
        'correct': 'Shape s = new Shape();',
        'wrongs': ['Shape s = new Circle();', 'Shape s = new Rectangle();', 'Circle c = new Circle();'],
        'image': {
            'name': 'q4.png',
            'url': 'classDiagram\n    direction LR\n    class Shape {\n        <<abstract>>\n        +area()* double\n        +color: String\n    }\n    class Circle {\n        -radius: double\n        +area() double\n    }\n    class Rectangle {\n        -width: double\n        -height: double\n        +area() double\n    }\n    Shape <|-- Circle\n    Shape <|-- Rectangle'
        }
    },
    {
        'type': 'mc', 'name': 'MC06',
        'text': '請觀察下圖：<code>Flyable</code> 帶有 <code>&lt;&lt;interface&gt;&gt;</code> 標籤，且箭頭線為虛線。以下敘述何者正確？',
        'correct': 'Bird 和 Airplane 都實作（implements）了 Flyable 介面，必須提供 fly() 方法的具體實作。',
        'wrongs': ['Bird 和 Airplane 繼承（extends）了 Flyable，因此自動擁有 fly() 的預設實作。', '虛線箭頭代表依賴（Dependency）關係，不是實作關係。', '介面中的 fly() 方法不能被子類別覆寫。'],
        'image': {
            'name': 'q5.png',
            'url': 'classDiagram\n    direction LR\n    class Flyable {\n        <<interface>>\n        +fly() void\n    }\n    class Bird {\n        +fly() void\n    }\n    class Airplane {\n        +fly() void\n    }\n    Flyable <|.. Bird\n    Flyable <|.. Airplane'
        }
    },
    {
        'type': 'mc', 'name': 'MC07',
        'text': '請觀察下圖：<code>Teacher ..&gt; Student</code> 是虛線箭頭，代表什麼類型的關係？它在 Java 實作上通常如何呈現？',
        'correct': '依賴 / use 關係。在 Java 中，Student 通常是 Teacher.assignGrade() 方法的「參數」，而不是 Teacher 的屬性（成員變數）。',
        'wrongs': ['聚合（Aggregation）。Teacher 類別應宣告一個 List&lt;Student&gt; 屬性，生命週期獨立。', '組合（Composition）。Student 是在 Teacher 的建構子中建立的，生命週期綁定。', '繼承關係。Teacher 繼承了 Student 的所有屬性與方法。'],
        'image': {
            'name': 'q6.png',
            'url': 'classDiagram\n    direction LR\n    class Teacher {\n        +assignGrade(Student s, Course c, int score) void\n    }\n    class Student\n    class Course\n    Teacher ..> Student : use\n    Teacher ..> Course : use'
        }
    },
    {
        'type': 'mc', 'name': 'MC08',
        'text': '在 UML 類別圖中，以下哪一種情境最適合使用「組合（Composition，實心菱形）」關係？',
        'correct': '一個「訂單（Order）」包含多筆「訂單明細（OrderItem）」，當訂單刪除時，所有明細也應一起刪除。',
        'wrongs': ['一個「學校（School）」聘僱多位「老師（Teacher）」，學校關閉後老師可以轉到其他地方任職。', '一個「學生（Student）」修了多門「課程（Course）」，學生退學後課程依然存在。', '一個「老師（Teacher）」在上課時使用「投影機（Projector）」，但投影機不屬於老師。']
    },
    {
        'type': 'essay', 'name': 'SA01',
        'text': '請說明 UML 類別圖中「聚合（Aggregation）」與「組合（Composition）」的差異，並分別舉一個實際的例子說明。這兩種關係在 Java 程式碼中（物件的建立方式、生命週期管理）如何體現出差異？'
    },
    {
        'type': 'essay', 'name': 'SA02',
        'text': '在 UML 類別圖中，「關聯（Association）」與「依賴（Dependency）」都可以表達兩個類別之間有「使用關係」，但語意上有所不同。請說明：(1) 這兩種關係各自在 Java 程式碼中如何實作（例如：成員變數 vs. 方法參數）。(2) 以「老師（Teacher）打分數（assignGrade）」為例，Teacher 與 Student、Grade 之間各應使用哪種關係？理由為何？'
    }
]

base_dir = '/Users/nlh/Library/Mobile Documents/com~apple~CloudDocs/TEACH/teach_sw_fw/exam/quiz01/ch01'

print("Writing ch01...")
write_quiz(os.path.join(base_dir, 'ch01_quiz.xml'), 'quiz01/ch01', ch01_qs)

print("Writing ch02...")
write_quiz(os.path.join(base_dir, 'ch02_quiz.xml'), 'quiz01/ch02', ch02_qs)

print("Writing ch04...")
write_quiz(os.path.join(base_dir, 'ch04_quiz.xml'), 'quiz01/ch04', ch04_qs)

print("Cleaning up old .gift.txt files...")
for f in ['ch01_quiz.gift.txt', 'ch02_quiz.gift.txt', 'ch04_quiz.gift.txt']:
    path = os.path.join(base_dir, f)
    if os.path.exists(path):
        os.remove(path)

print("Done generating XML.")
