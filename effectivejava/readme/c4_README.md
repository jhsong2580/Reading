# 클래스와 인터페이스

---
### 아이템15 : 클래스와 멤버의 접근권한을 최소화 하라
- 잘 설계된 Component는 모든 내부 구현을 완벽히 숨겨, 구현과 API를 깔끔히 분리한다. (정보은닉, 캡슐화)
- 장점
  1. 여러 Component를 병렬로 개발할 수 있게 하여, 시스템 개발 속도를 높인다 
  2. 시스템 관리 비용을 낮춘다. 각 Component를 더 빨리 파악하여, 디버깅 할 수 있고, 다른 컴포넌트로 교체하는 부담도 적다 
  3. 정보 은닉 자체가 성능을 높여주지는 않지만 성능 최적화에 도움을 준다. 
  4. 소프트웨어에 재사용성을 높인다. 외부에 거의 의존하지 않고 독자적으로 동작할 수 있는 컴포넌트라면 그 컴포넌트와 함께 개발되지 않은 환경에서 사용 가능하다. 
  5. 큰 시스템을 제작하는 난이도를 낮춰준다. 
- 원칙
  1. 모든 클래스와 멤버의 접근성을 가능 한 좁히자. 
     - 톱레벨 클래스나 인터페이스를 "public"으로 선언하면 공개 API가 되며, package-private으로 선언하면 패키지 안에서만 사용할수 있다. 
       - 외부에서 쓸 필요가 없다면 package-private으로 선언하여 API를 노출시키지 말자 
     - 한 클래스에서만 사용하는 package-private 톱레벨 클래스나 인터페이스는 이를 사용하는 클래스 안에 private static으로 중첩시키자.
     - public 클래스에서는 멤버의 접근 수준을 protected로 바꾸는 순간, 멤버에 접근할수 있는 범위가 엄청나게 넓어진다. 
       - public 클래스의 protected 멤버는 공개 API이므로 영원히 지원되야 하고, 사용자에게 설명을 해야한다 
       - protected 멤버의 수는 적을수록 좋다 
     - 상위 클래스의 메서드를 override할때는 접근성을 좁힐수가 없다.
       - 만약 좁힌다면 리스코프 치환원칙에 어긋난다.
       ```
       interface class Parent {
            public void doSomething();
       }       
       
       public class Child implements Parent {
            @Override
            private void doSomething(){}
       }
       
       Parent parent = new Child();
       parent.doSomething() <- 빌드 에러! 리스코프 치환원칙에 어긋난다. 
       ```
  2. Public 클래스의 인스턴스 필드는 되도록 public이 아니여야 한다. 
     - final이 아닌 인스턴스 필드를 public으로 두면 그 필드에 담을 수 있는 값을 제한할 힘을 잃는다. 
     - 또한 해당 변수에 대해 불변을 보장할수 없어, Thread-safe하지도 않다.
     - 이러한 문제는 정적 필드에서도 마찬가지나, 공개가 필수적이라면 "public static final" 필드로 공개해도 좋다. 
       - 이러한 필드는 대문자와 각 단어 사이에 _를 넣어 표현한다. 
       - 그리고 불변 객체를 참조해야지 가변 객체를 참조하면 안된다. 
       ```
       public static final Thing[] values;
        // values의 값은 변경되지 않지만, values 배열 내의 값은 가변이다.
        // 아래와 같이 변경하면 좋다. 효율적이고 가변 객체에 대해 보호할수 있다.
       private static final Thing[] PRIVATE_VALUES = {};
       public static final Thing[] values() {
            return PRIVATE_VALUES.clone();  //1안
            return Collections.unmodifableList(Arrays.asList(PRIVATE_VALUES)); //2안
       }
              
       ```
---
### 아이템 16 public 클래스에는 public 필드가 아닌 접근자 메서드를 사용하라
- public class는 절대 필드를 직접 노출하지 말고 생성자, getter, setter를 사용하자
- 불변 필드라면 노출해도 덜 위험하지만, 단점은 여전히 존재한다. 
- package-private, private class들을에서는 필드를 직접 노출하는 편이 나을때 도 잇다. 
```
//이 클래스는 공개 API기 때문에(public 접근자) 패키지 바깥에서 필드에 대해 직접 접근을 차단할수 있는 장점을 가진다.
public class Point {
    private double x;
    private double y;
    //All Arg Constructor
    //getter
    //setter
```
```
// 이 클래스는 공유 클래스가 아니기 때문에 데이터 필드를 노출한다 해도 하등의 문제가 없다. 
package-private class Point {
    public double x;
    public double y;
}
```

---
### 아이템 17 변경 가능성을 최소화 하라 
- 불변 클래스로 만들면 설계하고 구현하고 사용하기 쉬우며, 오류가 생길 여지도 적고 훨씬 안전하다. 
- 클래스를 불변으로 만드려면 다음 다섯가지 규칙을 따르면 된다. 
  1. setter는 제공하지 않는다. 
  2. 클래스를 확장할수 없게 만든다. (클래스를 final로 선언한다)
  3. 모든 필드를 final로 선언한다. 
  4. 모든 필드를 private으로 선언한다. 
  5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다. 
     - unmodifableList, clone ...
- 불변 객체는 자유롭게 공유할수 있고, 불변객체 끼리는 priavte 필드를 공유 할수 있다. 
- 객체를 만들때 다른 불변 객체들을 구성 요소로 사용하면 이점이 많다. 
  - 불변객체로 이루어진 객체라면 구조가 복잡하더라도 불변식을 유지하기 수월하다. 
  - 특히 Map, Set과 같은 자료구조에서 불변 객체를 사용하면, 데이터 사용에 안전함을 획득할수 있다. 
- 불변 객체는 그 자체로 실패 원자성을 제공한다. 상태가 절대 변하지 않으니 잠깐이라도 불일치 상태에 빠질 가능성이 없다. 
- 불변클래스의 단점은 값이 다르다면 "반드시 독립된 객체로 만들어야한다" 라는 것이다. 
  - 이것때문에, 속도나 메모리상에 효율이 떨어질수 있다. 
  - [불변객체의단점은_값이다르면_무조건_새로운객체로만든다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/readme/c4_README.md)

---
### 아이템 18 상속보다는 컴포지션을 사용하라 
- 상속은 코드를 재사용 하는 강력한 수단이지만, 경계를 넘어 다른 패캐지의 클래스를 상속하는것은 매우 위험하다 
  - 클래스가 인터페이스를 구현하거나, 인터페이스가 인터페이스를 확장하는건 관계가 없고, 구현체를 상속받아 확장하는것이 문제가 된다. 

### 메서드 호출과 달리 상속은 캡슐화를 깨뜨린다.
- 상속은 강력하지만 캡술화를 해친다는 문제가 있다. 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만 써야 한다. 
  - 상속의 취약점을 피하려면 상속 대신 컴포지션과 전달을 사용하자! 래퍼 클래스는 하위 클래스보다 견고하고 강력하다. 
- 상위 클래스가 어떻게 구현 되느냐에 따라 하위 클래스의 동작에 이상이 생길 수 있다. 
  - [잘못된_상속은_예상치못한에러를_발생시킨다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/readme/c4_README.md)
  - 또한 다음 패치에서 상위 클래스에 새로운 메서드를 추가한다면, 해당 메서드에 대해서 검증 및 보안 패치가 추가로 진행 되어야 할 것이다.
- 이러한 재정의가 문제가 될수 있어, 기존 클래스를 확장하는 대신 "새로운 클래스를 만들고 private필드로, 기존 클래스의 인스턴스를 참조하게 하자"(컴포지션)
  ```
  public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s; // 컴포지션! 거의 일급클래스같이 생겼다 
    //Set에 대한 모든 메서드에 대해 this.s 를 통해 구현한다. 
  }  
  
  public InstrumentedhashSet<E> extends ForwardingSet<E> {
    private int addCount = 0;
    public InstrumentedhashSet(Set<E> s) {
        super(s);
    }
  
    public void add(~) { // domain.ch04.item18.InstrumentedHashSet과 똑같으나, Set의 add가 아닌, ForwardSet의 add를 사용한다. 
        addCount++;
        super(e);
    }
  }
  ```
  - 이러한 Wrapper Class는 Set에 기능을 덧씌운다는 뜻에서 "데코레이터 패턴"이라고 한다. 
  - 하지만 Wrapper Class는 Callback Framework와는 어울리지 않는다. 
    - Callback때는 Wrapper Class가 아닌, this를 넘겨 데코레이팅 한 기능이 동작하지 않는다.
---
### 상속을 고려해 설계하고 문서화 하라. 그러지 않았다면 상속을 금지하라
- 상속용 클래스는 재정의할수 있는 메서드들을 내부적으로 어떻게 이용하는지 문서로 남겨야 한다. 
- API 문서의 메서드 설명 끝에서 "Implementation Requiredments"로 시작하는 절이 있는데 그 메서드의 내부 동작 방식을 설명해준다 
  - @implSpec 태그를 붇혀주면 자바독 도구가 생성해준다.
  - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item18/ImplSpecTagInterface.java)
  - 하지만 가장 확실한건 "상속을 고려하지 않은 함수는 상속하지 못하게 막아라!"
- 그리고 효율적인 하위 클래스를 큰 어려움 없이 만들수 있게 하려면 클래스의 내부 동작 과정 중간에 끼어들 수 있는 훅(hook)을 잘 선별하여, protected 메서드 형태로 공개해야 할수도 있다. 
  - AbstractList.removeRange 함수는 clear()함수를 더욱 고성능으로 수행할수 있게 만들어둔 함수이다. 
  - 만약 removeRange()함수가 없다면 clear 성능이 안좋아지고 이러한 기능을 바닥부터 끝까지 새로 만들어야 할 것이다. 
- 그렇다면 어떤 클래스를 protected로 노출해야할까?

### 상속용 클래스를 시험하는 방법은 직접 하위클래스를 만들어 보는것이 유일하다.
- 꼭 필요한 protected 함수(hook)을 놓쳤다면, 하위 클래스를 작성할때 그 빈자리가 드러난다.
- 거꾸로 하위 클래스를 여러개 만들때까지 사용하지 않는 protected함수는 private 접근제어자였어야 할 가능성이 크다.

### 상속용 클래스의 생성자는 직접적으로든 간접적으로든 재정의 기능 메서드를 호출해서는 안된다. 
- 상위 클래스의 생성자가 하위 클래스의 생성자보다 먼저 실행되므로, 하위 클래스에서 재정의한 메서드가 하위클래스의 생성자보다 먼저 동작한다.
  - 아래 "Child" 클래스를 생성하게 되면, Child Class의 생성 로직이 호출되지 않았음에도, 재정의된 Child의 "overrideMe" 함수가 호출되어 문제를 일으킬 수 있다. 
```
public class Super {
 // 잘못된 예 - 생성자가 재정의 가능 메서드를 호출한다.
  public Super() {
    overrideMe();
  }
  
  public void overrideMe(){
  
  }
}

public class Child extends Super {
    public Child(){
      super();
      // doSomeThing
    }
    
    @Override
    public void overrideMe() {
        // doSomeThing Of Child
    }
}
```
### Clone과 readObject모두 직접적으로든, 간접적으로든 재정의 가능 메서드를 호출해서는 안된다. 
- readObject의 경우, 하위 클래스의 상태가 미처 다 역직렬화 되기 전에 재정의한 메서드 부터 호출하게 된다.
  - Serializable을 구현한 상속용 클래스가 readResolve나 writeReplace메서드를 갖는다면, 이 메서드는 protected로 선언해야한다. 
    - private일시 하위 클래스에서 무시되기때문.
- clone의 경우 하위 클래스의 clone메서드가, 복제본의 상태를 올바른 상태로 수정하기 전에 재정의한 메서드를 호출하게 된다. 
  - 하위 클래스의 생성자가 동작을 하지 않은 상태에서 하위 클래스의 Override한 메서드를 호출한다면, 정상적으로 clone이 되었다고 보기 힘들수 있다. 

### 상속을 하지 못하게 할 클래스는 무조건 상속을 금지하라
1. final로 클래스를 선언한다.
2. 모든 생성자를 private, package-private으로 선언하고 정적팩터리 메서드를 사용한다. 
3. 만약 구체 클래스가 인터페이스를 구현하지 않았지만 상속을 허용해야 한다면 "클래스 내부에선 Override가 가능한 메서드를 호출하지 않아야 한다"
   - 예상한 동작 순서가 하위 구현체들의 동작에 의해 꼬인다!

---
### 아이템 20 추상 클래스보다는 인터페이스를 우선하라
- 다중 구현용 타입은 인터페이스가 가장 적합하다. 복잡한 인터페이스라면 구현의 수고를 덜어주는 default method 등을 사용하자 
1. 기존 클래스에도 손쉽게 새로운 인터페이스를 구현해 놓을수 있다. 
   - 만약 추상클래스를 끼워 넣는다면, 기존 클래스가 상속받고있는 추상클래스의 상위 추상클래스에 추가할 메서드를 넣어줘야한다. 
     - 이렇게 되면 하위 모든 클래스들이 영향을받는다 (같은 조상 클래스밖에 키워넣을수밖에없다)
   - 인터페이스는 같은 조상이 아니여도, 기존 클래스에만 영향을 주는 인터페이스를 추가할수 있다.
     - 다른 조상을 넣을수 있고 다중상속이 되기 때문에. 
2. 인터페이스는 mixin정의에 안성맞춤이다. 
   - MixIn : 클래스가 구현할 수 있는 타입으로, 믹스인을 구현한 클래스에 원래의 타입 외에도 특정 선택정 행위를 제공한다고 선언하는 효과를 줌.
     - Comparable이 자신을 구현한 클래스의 인스턴스끼리는 순서를 정할수 있다고 선언하는 믹스인 인터페이스
   - 추상클래스로는 MixIn을 정의할수 없다. 기존 클래스에 덧씌울수도 없고 단일 상속이기때문에 믹스인을 삽입할 수 있는 위치를 찾기 어렵다. 
3. 인터페이스로는 계층구조가 없는 타입 프레임워크를 만들수 있다. 
   - [인터페이스를통한 다중상속](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item20/SingerSongWriterByInterface.java)
   - SingerSongWriterByInterface 클래스는 Singer와 Songwriter을 상속받아 구현을 한다. 
   - 만약 SingInterface, SongWriterInterface가 Abstract Class 라면 다중상속은 불가능하다 .
     - 이 기능을 합쳐놓은 또다른 추상클래스를 만들어야한다. (같은 조상이 아니기때문에)
4. 래퍼클래스를 사용하면 인터페이스는 기능을 향상시키고 안전한, 강력한 수단이 된다.
   - 타입을 추상클래스로 정의해두면, 기능을 구현하는 방법은 "상속"뿐이다. 
   - 인터페이스는 굳이 사용자들이 구현할 필요가 없는 메서드를 "디폴트 메서드"로 제공하여 일감을 덜어줄 수 있다. 
     - [다양한 상속관계가 있는 interface는 디폴트메서드와 골격메서드를 추가한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item20/Parent.java)

---
### 아이템 21 인터페이스는 구현하는 쪽을 생각하여 설계하라.
- 인터페이스에 메서드를 삽입하기 위해서는 default method가 사용된다.
  - 추상메서드로 했을때 구현체들 전부 업데이트가 이루어 져야 하기 때문이다. 
  - 하지만 default method는 구현체들의 동의없이 삽입되는 것이라 구현체들이 사용했을때 비 정상적인 동작을 할수도 있다. 
  - 즉 default method는 컴파일에 성공하더라도, 서비스 실행 중에 런타임 오류를 일으킬 수 있어 매우 조심해야한다.

---
### 아이템 22 인터페이스는 타입을 정의하는 정도로만 사용하라
- 인터페이스는 타입을 정의하는 용도로만 사용하고 상수 공개용 수단으로 사용하지 말자 
- 인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다. 
- 아래 잘못된 인터페이스 사용인 "상수 인터페이스"를 보자 
  - 상수 인터페이스는 메서드 없이 상수를 뜻하는 static final 로 가득찬 interface이다. 
  - PhsicalConstant를 상속받는 구현체들은 해당 상수를 사용하지 않아도 계속 가지고 있게 된다. 
  - 상수는 클래스 내부 구현이기 때문에, 상수로 설정함으로써 내부 구현을 외부로 노출시키는 일을 초래한다.
```
public interface PhysicalConstants { 
    static final double AVOGADROS_NUMBER = 6.022_140_85;
}
```
- 상수를 공개할 목적이라면 아래와 같이 하자 
  - 구현체를 만들어, 상속을 못하게 한 뒤 상수를 선언하자 
```
public final class PhysicalConstants {
    public static final double AVOGADROS_NUMBER = 6.022_140_85;
}
```
---
### 아이템 23 태그 달린 클래스보다는 클래스 계층 구조를 활용하라
- 태그 달린 클래스는 장황하고, 오류를 내기 쉽고 ,비효율 적이고 클래스 계층구조를 어설프게 흉내낸 것일 뿐이다.
  - [badCase](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item23/FigureBadCase.java)
  - [goodCase](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item23/FigureGoodCase.java)

---
### 아이템 24 멤버클래스는 되도록 static 으로 만들라.
- 중첩클래스 : 다른 클래스 안에 정의된 클래스. 자신을 감싼 바깥 클래스에서만 쓰여야 하며, 그 외 쓰임새가 있다면 톱레벨 클래스로 만들어야 한다. 
  - 종류 : 정적 멤버 클래스, 멤버 클래스, 익명 클래스, 지역 클래스 
1. [정적 멤버 클래스](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item24/StaticMemberClass.java)
   - 바깥 클래스와 함께 쓰일때만 유용한 public 도우미 클래스로 사용
   - [private 정적 멤버 클래스](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item24/PrivateStaticMemberClass.java)
     - 흔히 바깥 클래스가 표현하는 객체의 한 부분을 나타낼때 많이 쓴다. 
2. [멤버 클래스](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item24/MemberClass.java)
   - 바깥 인스턴스 없이는 생성이 불가능하며 바깥 인스턴스 - 멤버 인스턴스는 연결되어있다.
     - 멤버클래스에서 바깥인스턴스클래스.this 를 통해 바깥 클래스를 참조할수 있다. 
       - [MemberClass.this.return3](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch04/item24/MemberClass.java)
   - 보통 바깥 인스턴스를 생성자로 생성할때, 멤버 클래스를 인스턴스로 만들어 주는것이 보통이지만, 바깥 인스턴스를 호출 해 수동으로 만들기도 한다. 
   - 멤버 클래스는 "어댑터"를 정의할때 자주 쓰인다. 
     - 어떤 클래스의 인스턴스를 감싸 마치 다른 클래스의 인스턴스처럼 보이게 하는것.
       - HashMap.keySet() 함수를 보면 AbstractSet을 구현한 멤버 클래스인 KeySet을 반환한다.
         - [HashMapAdapter](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch04/Example.java)
   - ### 멤버클래스에서 바깥 인스턴스를 참조할 일이 없다면 무조건 static을 붙여 정적 멤버클래스로 만들자
     - 멤버 클래스 참조를 저장하려면 시간과 공간이 소비되고, 가비지컬렉션이 바깥 클래스의 인스턴스를 수거하지 못한다. 
       
3. 익명클래스
   - 사용 이유
     1. 프로그램에서 일시적으로 사용되고 버려진다. 단발적으로 사용되는 객체일 경우에 사용된다. 
        - UI 이벤트 처리, 스레드 객체 등(단발성 이벤트 처리)
     2. 재사용이 없고, 확장성을 활용하는것이 유지보수에서 불리할때 
   - 사용 방법
     1. 상속 아래 익명 자식 객체를 만드는 방법 
        - [AnonymousClassTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch04/Example.java)
     2. 인터페이스를 구현한 익명 구현 객체
        - [AnonymousInterfaceTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch04/Example.java)
4. 정리
   - 메서드 밖에서도 사용해야 하거나 정의하기엔 너무 길다면 "멤버 클래스"로 만든다. 
   - 멤버 클래스 인스턴스 각각이 바깥 클래스를 참조한다면 "비정적 멤버클래스"로, 참조하지 않는다면 "정적 멤버클래스"로 만들어준다. 
   - 클래스가 한 메서드 안에 쓰이면서 인스턴스를 생성하는 곳이 단 한곳이고, 해당타입으로 쓰기 적당한 부모 클래스나 인터페이스가 있다면 "익명클래스"로 만들고, 없다면 "지역클래스"로 만들자.
       

---
### 아이템 25. 톱레벨 클래스는 한파일에 하나만 담으라.
- 소스파일 하나에 톱레벨 클래스를 여러개 선언 하더라도 컴파일에 문제는 없으나, 많이 위험하다. 
- 이렇게 하면 컴파일러가 한 클래스에 대해 정의를 여러개 만들어 내게 되고, 소스파일을 어떤 순서로 컴파일 하느냐에 따라 프로그램의 동작이 달라질 수 있다. 