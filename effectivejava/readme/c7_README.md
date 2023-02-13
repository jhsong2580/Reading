# 람다와 스트림

---

### 익명 클래스보다는 람다를 사용하라
- [AnonymousClassForStringSort](https://github.com/jhsong2580/Reading/blob/master/effectivejava/readme/c7_README.md)
- [LamdaForStringSort](https://github.com/jhsong2580/Reading/blob/master/effectivejava/readme/c7_README.md)
- 익명클래스를 람다로 바꾸면 자질구레한 코드들이 사라지고, 어떤 동작을 하는지 명확하게 드러난다. 
  - 컴파일러가 알아서 문맥을 살펴 타입, return값 등을 설정해준다. 
- 이전에 만든 Operation을 아래와 같이 변경할 수 있다. 
  - [람다를 사용하지 않은 Operation](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch06/item38/BasicOperation.java)
  - [람다를 사용한 Operation](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch07/item42/BasicOperationWithLamda.java)
- 람다의 단점은 ?
  1. 람다는 이름이 없고 문서화도 할 수 없다
     - 코드 자체로 동작이 명확히 설명되지 않거나 가독성이 떨어진다면 람다를 쓰면 안된다. 
- 람다를 사용할수 없는 곳은?
  - 람다는 함수형 인터페이스에서만 사용되어, 추상클래스의 인스턴스를 만들 때는 람다를 사용할수 없고, 익명클래스를 사용해야 한다. 
  - 람다는 자신을 참조할 수 없다. 람다에서의 "this" 키워드는 바깥 인스턴스를 가리킨다. 반면, 익명클래스에서의 "this"는 익명클래스 본인을 가리킨다. 
  - 람다도 익명 클래스처럼 직렬화 형태가 구현별로(혹은 VM별로) 다를수 있어, 람다를 직렬화 하는 일은 극히 삼가야 한다. 
    - 직렬화 해야하는 함수 객체가 있다면, private static innter class의 인스턴스를 사용하자 

---
### 람다보다는 메서드 참조를 사용하라
- 람다가 익명 클래스보다 나은 점 중에서 가장 큰 특징은 "간결함" 이다
- 하지만 간결함으론 람다보다 "메서드 참조"가 더 크다.
- 람다는 불가능 하지만 메서드 참조로 가능한 예는 바로 제네릭 함수 타입이다. 
---
### 아이템 44 표준 함수형 인터페이스를 사용하라 
- 자바가 람다를 지원하면서 API 를 작성하는 모법 사례도 크게 바뀌었다. 
  - 상위 클래스의 기본 메서드를 재정의해 원하는 동작을 구현하는 템플릿 메서드 패턴의 매력이 크게 줄고, 정적 팩터리 메서드와 생성자가 인기를 끌고있다. 
- 예로 LinkedHashmap을 보자. LinkedhashMap은 removeEldestEntry를 재정의하면 cache로 사용할 수 있다. 
  - [CacheWithHashMap](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
    - 템플릿 메서드를 통한 캐시 구현 
- 표준 함수형 인터페이스로 구현 가능한 부분은 표준 함수형 인터페이스를 사용하자 (아래 표에 표준 함수형 인터페이스들이 있다.)

|       인터페이스       |       함수 시그니처       |                           예시                           |
|:-----------------:|:-------------------:|:------------------------------------------------------:|
| UnaryOperator<T>  |    T apply(T t)     | [UnaryOperatorTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java) |
| BinaryOperator<T> | T apply(T t1, T t2) |[BinaryOperatorTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)                                                        |
|   Predicate<T>    |  boolean test(T t)  |[PredicateTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)                                                        |
|   Function<T,R>   |       T get()       |[SupplierTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)                                                        |
|    Consumer<T>    |  void accept(T t)   |[ConsumerTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)                                                        |

- 표준 함수형 인터페이스를 사용할때 박싱타입을 넣어 사용하지 말자. 
  - 동작은 하지만 박싱/언박싱에 소모되는 메모리, 시간이 크다! 
- 직접 만든 함수형 인터페이스에는 @FunctionalInterface 를 붙여줘라. 
- 함수형 인터페이스를 API에서 사용할 때 
  - 서로 다른 함수형 인터페이스를 같은 위치의 인수로 받은 메서드들을 다중 정의해서는 안된다. 

---
### 아이템 45 스트림은 주의해서 사용하라 
