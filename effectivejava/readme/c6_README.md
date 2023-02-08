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