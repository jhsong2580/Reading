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
