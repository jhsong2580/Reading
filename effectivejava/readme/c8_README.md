# 메서드

---
### item49 매개변수가 유효한지 검사하라 
- 메서드 몸체가 실행되기 전에 매개변수를 확인한다면 잘못된 값이 넘어왔을때, 즉각적이고 깔끔한 방식으로 예외를 던질 수 있다.
- public과 protected 메서드는 매개변수 값이 잘못됬을때 던지는 예외를 문서화 해야 한다. 
  - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch08/BigIntegerUtils.java)
- java에서 제공하는 null 검사 기능을 이용해도 효율적이다
  - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch08/NullCheck.java)
- 코드 제작자(우리같은)만 사용하는 코드라면 간단히 assert를 통해 매개변수 유효성을 검증하자
  - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch08/AssertCheck.java)
- 하지만 메서드 몸체 실행 전에 매개변수 유효성을 검사해야 한다는 규칙에도 예외는 있다. 
  - 유효성 검사 비용이 지나치게 높거나, 실용적이지 않을 때 혹은 계산 과정에서 암묵적으로 검사가 수행될때는 검사하지 않는다. 
- 메서드나 생성자를 작성할 때면 그 메서드에서 정한 "제약"들을 문서화 하고 메서드 코드 시작 부분에서 명시적으로 검사해야한다. 

---
### item50 적시에 방어적 복사본을 만들라
- 매개변수의 유효성을 검사 후, 참조타입에 대한 예상하지 못한 변화가 있을수 있다. 이러한 문제를 방어적 복사본을 통해 대처하자
  - [방어적_복사본_실패_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch08/Example.java)
  - [방어적_복사본_성공_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch08/Example.java)
- 또한 복사를 할때 clone()를 사용하는건 조심스럽게 접근하자. 해당 클래스가 clonable을 재정의하지 않았을수도 있다. 
- 하지만 방어적 복사는 성능 저하가 발생하고, 항상 쓸수 있는것도 아니다. 컴포넌트 내부를 수정하지 않는다 확신하면 방어적 복사는 생략하자. 
  - 추가로 수정을 한다면 위험이 발생한다는 것을 "문서에 명시하자"

---
### item51 메서드 시그니처를 신중히 설계하라
1. 메서드 이름을 신중히 짓자. 
   - 항상 표준 명명 규칙을 따라야 한다.
2. 편의 메서드를 너무 많이 만들지 말자. 
   - 모든 메서드는 각각 자신의 소임을 다해야 한다. 클래스나 인터페이스는 자신의 각 기능을 완벽히 수행하는 메서드로 제공해야 한다. 
   - 아주 자주 쓰일 경우에만 별도의 약칭 메서드를 두기 바란다. 
   - 확신이 서지 않으면 만들지 말자. 
3. 매개변수 목록은 짧게 유지하자. 
   - 4개 이하가 좋고 같은 타입의 매개변수가 여러개 연달아 올 경우 특히 해롭다.
   - 매개변수 목록 짧게 유지하는 방법
     1. 여러 메서드로 쪼갠다. 
     2. 여러 매개변수를 묶는 도우미 클래스(DTO)를 만든다. 
     3. 객체 생성에 사용한 빌더 패턴을 메서드 호출에 응용한다. 
---
### item52 다중 정의는 신중히 사용하라 
- 아래 예시에 대해서 test case를 실행하게 되면 "그 외"가 출력된다 
  - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch08/item52/CollectionClassifier.java)
  - [다중정의_실패예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch08/Example.java)
    - 어느 메서드를 호출할지가 컴파일 타임에 정해지기 때문이다. 컴파일 타임에는 모든 타입이 "Collection"으로 보이게 된다. 
- 아래 예시에 대해 test case를 실행하게 되면 각각 재정의한 이름이 출력된다. 
  - [오버라이딩_성공예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch08/Example.java)
- 이러한 이유는 "오버라이딩한 메서드는 동적으로 선택되고, 오버로딩한 메서드는 정적으로 선택되기 때문"
- 이렇기 때문에 "근본적으로 다른" 매개변수들로 구성된 오버로딩 메서드는 확실하게 구분이 되기 때문에 위에 문제가 발생되지 않는다. 
  - 근본적으로 다른 -> null이 아닌 두 타입이 어느쪽도 형변환이 불가능함
- [List클래스의_잘못된_오버로딩_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch08/Example.java)