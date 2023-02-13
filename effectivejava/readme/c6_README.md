# 열거 타입과 애너테이션 
- enum : 클래스의 일종인 열거타입 
- annotation : 인터페이스의 일종인 애너테이션 

--- 
### 상수 대신 열거 타입을 사용하라 
- 열거타입이란 ? 
  - 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다. 
  - 열거타입 자체는 클래스이며, 상수 하나당 자신의 인스턴스를 만들어 public static final 필드로 공개한다.
  - 열거 타입은 밖에서 접근할수 있는 생성자를 제공하지 않아, 사실상 final 클래스 이다
    - 열거 타입 으로 만들어진 인스턴스들은 딱 하나씩만 존재한다. 
  - 각 상수 별 필드를 추가 할수 있어, 해당 타입이 설명하는 값들을 정확히 표현할수 있다.

### 열거 타입내 상수 별 다른 함수를 적용 하려면 상수 별 메서드 구현을 통해 진행하라
- [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/Operation.java)
  - [EnumAbstractFunctionTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch06/Example.java)

### Enum 타입에 ToString을 재정의 한다면, fromString도 제공하라
- [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/Operation.java)

### 필요한 원소를 컴파일 타임에 다 알수 있는 상수 집합이라면 항상 열거 타입을 사용하자 
### 열거 타입에 정의된 상수가 영원히 불변일 필요는 없다. 나중에 추가할수도 있다.

---
### Ordinal 메서드 대신 인스턴트 필드를 사용하라.
- 열거 타입은 해당 상수가 그 열거 타입에서 몇번 째 위치인지를 반환하는 "ordinal"이라는 함수를 제공한다. 
  - 진짜 유지보수하기 힘드니 아예 사용하지 말고 만약 상수 별 ordinal 을 가져와야 한다면, 상수에 필드를 만들어 getter을 통해 가져오자 진짜 

---
### 비트 필드 대신 EnumSet을 사용하랃

---
### Ordinal 인덱싱 대신 EnumMap을 사용하라
- HashMap Vs EnumMap
  1. EnumMap이 성능이 훨씬 좋다. 
     - HASHMap은 해싱 작업이 필요하지만, Enum은 단일 객체임을 보장하기 때문에 이런 작업이 필요 없다.
     - 키를 별도로 관리하고있지 않아, 메모리를 더 효율적으로 사용하고 데이터의 위치값을 바로 알수 있다. 
  2. 순서를 기억한다. 
     - Enum에 명시되어있는 순서를 기억하고 있어, 정렬된다 하더라도 성능이 우수하게 동작한다.
  - 즉 Hash는 Hash Code를 키로 하여, 데이터를 적재하는 반면 Enum의 경우 Ordinal값이 존재하는 것을 활용하여, 데이터를 바로 엑섹스 할수 있는 효과가 있다.
- Enum의 상수값을 통해 Enum객체를 찾을때, 해당 상수를 기반으로 배열을 static으로 생성 해 놓은 뒤에 찾는 방법도 좋다 
  - [예시_TRANSITION_MAP](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item37/Phase.java)

---
### 아이템 38 확장할수 있는 열거 타입이 필요하면 인터페이스를 사용하라
- 기능 확장이 필요한 Enum이 필요하면, Inteface를 상속하고 각 Enum 값 별 구현하라 
  - [interface](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item38/Operation.java)
    - [impl1](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item38/ExtendedOperation.java)
    - [impl2](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item38/BasicOperation.java)
  - 아래 예시와 같이 제너릭을 통해 확장성 있게 사용할 수 있다. 
    - [OperationEnumCheck](https://github.com/jhsong2580/Reading/blob/mastereffectivejava/src/test/java/ch06/Example.java)
- 열거 타입 자체는 확장할 수 없지만, interface와 interface를 구현하는 Enum type을 사용해 같은 효과를 낼수 있다. 
  - 이렇게 하면 클라이언트는 자신만의 Enum Type을 만들수가 있고 확장해 사용할 수 있다. 

---
### 명명 패턴 보다 Annotation을 사용하라
- 명명 패턴의 단점
  1. 오타가 나면 알아차리기 힘들다. 
  2. 올바른 프로그램 요소에서만 사용되는것을 확인하기 어렵다. 
  3. 프로그램 요소를 매개변수로 전달할 마땅한 방법이 없다. 
 
- Meta-Annotation : Annotation 선언에 사용되는 Annotation
  - @Test 내에 @Retention, @Target같은 선언형 Annotation을 말한다. 
    - @Retention : 유지 범위 
      - @Retention(RetentionPolicy.RUNTIME) : 런타임에도 이 Annotation이 유지되어야 한다 
    - @Target : Annotation 설정 대상 
      - @Target(ElementType.METHOD) : 반드시 메서드 선언에만 적용시킬 수 있다.
- Annotation을 통해 값을 전달할 수 있다. 
  - [MakerAnnotationTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch06/Example.java)
- JAVA 8 에서는 여러 개의 값을 받는 Annotation을 다른 방식으로 만들 수 있다. 
  - 배열 매개변수를 사용하는 대신 @Repetable Annotation을 다는 방식이다. 
  - 주의사항
    1. @Repetable을 단 Annotation을 반환하는 '컨테이너 Annotation'을 하나 더 정의하고, @Repetable에 이 컨테이너 Annotation의 Class을 매개변수로 전달해야한다. 
    2. 컨테이너 Annotation는 내부 Annotation타입의 배열을 반환하는 value method를 정의해아 한다. 
    3. 컨테이너 AnnotationType에는 적절한 @Retention과 @Target을 명시해야한다.
  - [RepeatableMarkerAnnotationTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch06/Example.java)

---
### 아이템 40 @Override Annotation을 일관되게 사용하라 
- 상위 클래스의 메서드를 재정의 하려는 모든 메서드에 @Override를 달자. 
  - [잘못된_예](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item40/NotOverrideBigram.java)
    - Override Annotation을 붙이지 않아 컴파일이 정상적으로 되었고, 그 결과 아래와 같은 장애상황이 발생한다 
      - [UseOverrideAnnotationToUpperClassMethod](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch06/Example.java)
  - [좋은 예](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item40/OverrideBigram.java)
- 구체 클래스에서 상위 클래스의 추상메서드를 재정의 할때는, 굳이 Override를 달지 않아도 된다. 
  - 구체 클래스에서 아직 재정의가 안된 추상 메서드를 정의하지 않았을때는 @Override 태그와 상관없이 컴파일이 되지 않는다. 

### 아이템 41 정의하려는 것이 타입이라면, 마커 인터페이스를 사용하라.
- marker interface : 자신을 구현하는 클래스가 특정 속성을 가짐을 표시해주는 interface
  - ex) Serializable : 자신을 구현한 클래스의 인스턴스는 "ObjectOutputStream"을 통해 직렬화 될수 있다고 알려준다. 
- marker interface가 marker annotation보다 좋은 이유
  1. marker interface는 이를 구현한 클래스의 인스턴스를 구분하는 타입으로 쓸 수 있지만, 마커 애너테이션은 그럴수 없다.
     - marker interface는 하나의 Type이기 때문에, 타입 체크를 런타임이 아닌 컴파일 시점에 진행한다. 
  2. 적용 대상을 더 정밀하게 지정할 수 있다. 
     - marker annotation은 @Target을 통해 어느 타입에다가 지정할수 있는지를 확인하고 컴파일 단계에서 잡을수 있다.
       - 하지만 어떤 Class를 구현한 Class에만 적용하고 싶은 마커를 잡을수는 없다. 
- marker annotation이 더 나은 것은 없을까 ? 
  -1. 거대한 Annotation System 지원을 받을 수 있다.
- 상황 별 marker annotation vs marker interface
  - marker annotation
    1. Class와 Interface외의 프로그램 요소(모듈, 패키지, 필드)에 마킹해야할 때 Annotation을 쓸수밖에 없다.
  - marker interface
    1. 마킹이 된 객체를 인자로 받는 메서드가 있다면, 해당 객체에는 marker interface를 설정해야 한다. 