# 객체 생성과 파괴

---
### 생성자 대신 정적 팩터리 메서드글 고려하라 
- 클래스의 인스턴스를 얻는 방법은 보통 public 생성자이다. 하지만 static factory method로도 제공이 가능하다. 
- static factory method는 장단점이 존재하는데 아래에서 알아보자
1. 장점
   1. 이름을 가질수 있다. 
      - 생성자는 이름이 없어 반환될 객체의 특징을 잘 설명하지 못한다
      - 값이 소수인 BigInteger을 반환하는 2가지 방법
        ```
        //생성자
        new BigInteger(int, int, random)
              
        // 정적메서드
        BigInteger.probablePrime()  
        ```
      - 위처럼 이름을 가질수 있는 정적 팩터리 메서드는 제약이 없어 함수 설명을 할수 있다. 
   2. 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다. 
      - 새로 생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체 생성을 피할수 있다. 
   3. 반환 타입의 하위 객체를 반환할수 있는 능력이 있다
      - [example1](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch02/Example.java)
      - API를 만들 때 이 유연성을 응용하면 구현클래스를 공개하지 않고도 그 객체를 반환할수 있어 효율적이다.
      - 자바에서 Collection이 instance반환을 할때 사용자에겐 요청에 맞는 구현체를 반환해 주는게 예시이다. 
   4. 입력 매개변수에 따라 배전 다른 클래스 객체를 반환할 수 있다
      - 반환 타입의 하위 타입이지만 어떤 클래스 객체를 반환하든 상관없다. 
      - EnumSet은 정적 팩터리만 제공하는데, 64개 이하는 RegularEnumSet의 인스턴스를, 65개이상은 JumboEnumSet인스턴스를 반환한다.
        - java.util.EnumSet.noneOf function 참조
   5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다 
      - 서비스 제공자 프레임워크는 3가지 핵심 컴포넌트로 구분된다
        - service interface : 구현체의 동작 정의
        - provider registration API : 제공자가 구현체를 등록할때 사용하는 제공자 등록 API
        - service access API : 클라이언트가 서비스의 인스턴스를 얻을때 사용하는 접근 API (정적 팩터리 메서드)
      - JDBC API를 보면 
        - service interface : Connection
        - provider registration API : DriverManager.registerDriver
        - service access API : DriverManager.getConnection
        - 이렇게 3가지로 구분되어있다. 우리는 DriverManager.getConnection()을 통해 DB연결 객체를 얻는다. 

2. 단점
   1. 상속을 하려면 public이나 protected 생성자가 필요하니, static factory method만 제공하면 하위 클래스를 만들수 없다 
   2. static factory method는 프로그래머가 찾기 어렵다. 
      - 생성자처럼 API설명에 정확히 드러나지 않으니, 사용자는 static factory method를 통해 인스턴스를 만들 방법을 찾아야 한다. 
      - static factory method 함수 명 

          | 함수 명    | 용도 | 예시 |
        |:------:|:---------:|:--------:|
       | from    |   매개변수 하나를 받아 해당 타입의 인스턴스를 반환    | Date d = Date.from(instant);|
            | of      |   여러 매개변수를 받아 해당 타입의 인스턴스를 반환 | Set<Rank> faceCards = EnumSet.of(JACK,QUEEN);|
            | valueOf |   from, of의 더 자세한 버전 | BigInteger.valueOf(Integer.MAX_VALUE)|
            | instance / getInstance       |   매개변수로 명시한 인스턴스를 반환하지만, 같은인스턴스는 보장하지않음 | A a = A.getInstance(options)|
            | crate / newInstance |  매번 새로운 인스턴스를 반환 | Array.newInstance(classObject, arrayLen)|

---
### 생성자에 매개변수가 많다면 빌더를 고려하라
```
public Class Dummy { 
   private final int a;
   private final int b;
   private final int c;
   private fianl int d; 
   .....
   
   public Dummy (int a, int b, int c, int d){}
   public Dummy (int a, int b, int c){}
   public Dummy (int a, int b){}
   public Dummy (int a){}
}
```
- 위와 같은 코드의 인스턴스를 만드려면 아주 많은 생성자가 보이고, 각 생성자가 어떠한 역할을 하는지 알수가 없다.
- [example2](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch02/Example.java)
  - 다량의 생성자를 조합해야 할때 빌더 패턴을 사용할수 있고, 상속 관계에서도 위와 같이 사용할수 있다. 

---
### private 생성자나 열거 타입으로 싱글턴임을 보장하라
- 싱글턴 ? 
  - 인스턴스를 오직 하나만 생성하는 클래스.
- 싱글턴을 만드는 방법
  1. private 생성자 
    - 아래 코드처럼 private생성자는 Elvis.INSTANCE를 초기화 할때만 동작하고 그 외에는 접근이 불가능하여 "단 하나의 객체"를 지원한다.
    ```
    // public static final 필드 방식의 싱글턴
    public class Elvis {
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {}
    }
    ```
  1. 정적 팩터리 메서드 제공
  ```
  public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis(){}
    public static Elvis getInstance() {
        return INSTANCE;
    }
  }  
  ```
  
---
### 인스턴스화를 막으려거든 private 생성자를 사용하라.
- private 생성자는 인스턴스화를 막아줄 뿐만 아니라 상속도 불가능하게 만든다.

### 자원을 직접 명시하지 말고 의존객체 주입을 사용하라.
```
public class SpellChecker {
    private static final Lexicon dictionary = ...;
    private SpellChecker();
}
```
- 이 방식은 사전 하나를 SpellChecker가 의존하고있어, 사전이 변경될시 유연한 변경이 불가능하다. 
- 해결방법
  1. Setter을 통한 Dictionary 변경 
     - Thread-safe하지 않고, 특히 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다 
  2. Interface로 느슨한 의존 후 생성자를 통한 주입 
    ```
  public class SpellChecker {
    private Dictionary dictionary = ...;
    public SpellChecker(Dictionary dictionary);
  }
    new SpellChecker(new ADictionary());
    new SpellChecker(new BDictionary());
    ```

---
### 불필요한 객체 생성을 피하라 
- 똑같은 기능의 객체를 매번 생성하기보다는 객체 하나를 재사용 하는 편이 낫다. 
```
//계속 생성하는 안좋은 예
for(1..100) {
    String s = new String("a"); // 100 개의 String이 Heap Memory에 생성
}

//재사용하는 예
for(1..100) {
    String s = "a"; // 1개의 String을 Heap Memory 내 Strping constant pool에서 재사용 
}
```
- boolean의 박스 타입인 Boolean 또한 기존에 만들어준 Boolean.True, Boolean.False를 재사용한다.
- 정규식에 대한 객체 재활용
```
// 정규식에 대한 패턴을 항상 생성한다. 
static boolean isRomanNumeral(String s) {
    return s.matches("^정규식");
}

// 정규식에 패턴을 재활용한다.
class RomanNumerals {
    private static final Pattern pattern = Pattern.compile("정규식");
    
    static boolean isRomanNumeral(String s) {
        return pattern.matchers(s).matches();
    }
} 
```

- 오토 박싱으로 인한 불필요한 객체 생성
```
void sum () {
    Long sum = 0L;
    for(long i 1..100) {
        sum += i; // 더할때마다 Long 객체가 새로 생성됨... (Boxing 타입은 사칙연산이 불가능하여, unboxing을 해주어야한다!)
    }
}
```

---
### 다 쓴 객체 참조를 해제하라 
- JAVA 는 GC가 메모리 할당 해제를 자동으로 해주나, 자동으로 할당을 못하는 경우도 생긴다.
```
public class Stack {
    private Object[] elements;
    private static final int SIZE = 16;
    private int size = 0;
    public Stack() {
        elements = new Object[SIZE];
    }
    public void push(Object e) {
        ensureCapacity();
        elements[++size] = e;
    }
    
    public Object pop() {
        if (size == 0) 
            throw ~
        return elements[--size];
    }
    private void ensureCapacity() {
        if(elements.length == size) {
            elements = Arrays.copyOf(elements,2 * size +1);
        }
    }
}
```
- 위 코드의 문제
  1. ensureCapacity() 에서 Arrays.copy를 할때 Heap Memory가 부족할수도 있다. 
  2. Pop을 하고 리스트 대부분을 사용하지 않아도, Stack이라는 인스턴스가 계속해서 참조가 되고있기 때문에 리스트는 GC에 의해서 할당 해제되지 않는다.
- 위 코드의 해결
  - 2번에 대한 해결. pop을 할때, 해당 index를 null로 처리하여, 참조 해제를 해준다. 

---
### 메모리 누수의 주범?
1. 사용하지 않는, 하지만 계속 참조가되는 메모리에 대한 할당
2. 캐시
   - 객체참조를 캐시에 넣고나서 그 객체를 다 쓴 뒤로도 한참을 놔두는 행동
   - 엔트리가 살아있는 캐시가 필요한 상황이라면 WeakHashmap을 사용하여  캐시를 만들자

   ```
   WeakHaqshMap (약한참조 해시맵)
   1. 강한참조 ?
   - Integer prime = 1; //가장 일반적인 참조 유형. Prime은 1인 Integer에 강한 참조를 한다 
   2. 부드러운 참조 (Soft Reference)
   - SoftReference<Integer> soft = new SoftReference<Integer>(prime); // 만약 prime == null이 되어 원본이 없고 SoftReference만 존재하고, 메모리가 부족할때 GC 대상으로 들어가게 한다 
   3. 약한 참조(Weak Reference)
   - WeakReference<Integer> weak = new WeakReference<Integer>(prime); // 만약 prime == null이 되면 GC 대상이 된다
   
   WeakHashMap
   - 일반적인 hashmap은 Key/Value쌍이 put이 되면 사용 여부와 관계없이 할당해제/삭제 되지 않는다.
      - Key에 대한 Value가 없어도, 해당 Key는 남아있는다. 
   - Weakhashmap은 WeakReference특성을 이용하여 HashMap의 element를 자동 GC 해버린다. key에 해당하는 객체가 더이상 사용되지 않는다고 판단되면 제거한다. 
   ```
   
---
### finalizer와 cleaner사용을 피하라 
- 자바는 finalizer, cleaner라는 객체 소멸자를 제공한다.
  - finalizer? cleaner?
    - finalizer : 예측할수 없고 상황에 따라 위험할 수 있다. 
    - cleaner : finalizer보단 덜 위험하지만 여전히 예측할수  없고 느리고 위험하다.
  - 왜 위험한가
    1. finalizer,  cleaner의 실행 시점은 전적으로 GC 알고리즘에 달려있으며, 상황마다 메모리 회수 속도가 달라진다. 
      - 나는 잘 됬는데 고객 시스템에 가서는 대형 장애를 일으킬수 있
    2. finalizer, cleaner는 실행 시점도 정확하지 않으나, 실행 여부도 정확하지 않다. 자원에 대한 반환은 Try-Catch-Resource로 대신하자
      [example3](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch02/Example.java)
      [example4](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch02/Example.java)
    3. finalizer, cleaner는 성능 문제도 있다. 
          - AutoClosable을 통한 GC 소요시간을 1이라 한다면, finalizer/cleaner는 약 50배 가량 느리다 
    4. finalizer을 사용한 Class는 finalizer공격에 노출되어 보안문제를 일으킬 수도 있다. 
       - 생성자/직렬화 과정(readObject, readResolve)에서 예외가 발생하면, 생성되다 만 객체에서 악의적인 하위 클래스의 finalizer가 수행되게 된다. 
         - finalizer을 사용할꺼면 final을 붙여 사용하자 (이 finalizer을 override하지 못하게끔)
  - 그럼 어디다 쓰는걸까?
    - 자원 소유자가 close 메서드를 호출하지 않는 것에 대한 안전망
    - native peer와 연결된 객체에서 사용
      - native peer ? : 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 객체로써 자바 객체가 아님
        - 자바객체가 아니기 때문에 GC가 알아차리지 못하여 수동으로 메모리 정리를 해주어야
  
---
### try-finally 보다는 try-with-resource를 사용하라
- finally 안에서 자원을 반환하게되면 예기치못한 에러에 의해 자원이 정상 반환이 안될수 있다.
  [example5](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch02/Example.java)
- try-catch-resource로 자원을 반환하게되면 예기치못한 에러가 있어도 자원이 정상 반환된다.
  [example6](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch02/Example.java)
- 