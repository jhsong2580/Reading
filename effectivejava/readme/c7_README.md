# 람다와 스트림

---

### 익명 클래스보다는 람다를 사용하라
- [AnonymousClassForStringSort](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
- [LamdaForStringSort](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
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
- 스트림은 소스 스트림 -> n개의 중간연산 -> 종단연산 으로 구성된다. 
- 스트림 파이프라인은 Laxy Evaluation 된다. 종단 연산이 호출 될때 Evalutation되며, 종단 연산에 쓰이지 않는 데이터는 계산에 쓰이지 않는다. 
- 파이프라인을 병렬로 호출하기 위해 parallel 메서드를 호출하면 되나, 효과를 볼수있는 상황은 많지 않다 
- 스트림을 사용하지 않는 예 
  - [getDictionaryMapNoStream](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch07/Item45/Anagrams.java)
- 스트림을 너무 과도하게 사용한 예 (String 값을 sort하는 부분을 Stream으로 처리한다. 너무 가독성이 떨어진다)
  - [getDictionaryMapWithBadStream](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch07/Item45/Anagrams.java)
- 스트림을 내 key를 처리하는 부분을 함수화 하여 가독성을 높인다 
  - [getDictionaryMapWithGoodStream](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch07/Item45/Anagrams.java)
- 스트림 사용시 주의사항 
  - 람다에선 final이거나 사실상 final인 변수를 읽을 수 있고 지역 변수를 수정하는건 불가능하다 
- 스트림을 사용하면 좋은 경우 
  - 원소들의 시퀀스를 일관되게 변환한다. 
  - 원소들의 시퀀스를 필터링 한다 
  - 원소들의 시퀀스를 하나의 연산을 사용해 결합한다 
  - 원소들의 시퀀스를 컬렉션에 모은다 
  - 원소들의 시퀀스에서 특정 조건을 만족하는 원소를 찾는다.

### 아이템 46 스트림에서는 부작용 없는 함수를 사용하라 
- 스트림은 계산을 일련의 "변환"으로 재구성 하는 부분이다. 이때 각 변환 단계는 이전 단계의 결과를 받아 처리하는 순수 함수여야 한다. 
  - 순수함수 : 오직 입력한 값 만이 결과에 영향을 주는 함수
- 아래 예시는 스트림을 가장한 반복 코드이다. 
  - [StreamBadCase1Test](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
  - 연산한 결과를 보여주는게 아닌, 다른 동작(freq map에 값을 추가)이 추가되어 있어 가독성도 떨어진다. 
  - 내부 연산이 외부 값을 변경한다. 
- 아래 예시는 스트림으로 잘 작성한 코드이다.
  - [StreamGoodCaseTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
  - 스트림은 내부 연산만 동작하며, 외부 값을 연산중에 변경하지 않는다. 또한 가독성도 더욱 좋아졌다. 
- Stream에서는 Collector를 자주 사용한다 
  - [StreamGetFreqTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
  - [StreamToMapTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
  - [StreamToMapTestWithMergeMax](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
  - [StreamToMapTestWithMergeLast](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)
  - [StreamGroupByTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch07/Example.java)

---
### 아이템 47 반환타입으로는 스트림보다 컬렉션이 낫다
- Stream < - > Iterable 변환을 자바에서 제공하지 않기때문에, 둘 다 사용이 가능한 Collection으로 반환해 주는 편이 낫다. 
- 반환할 시퀀스가 크다면, 전용 컬렉션을 구현하는 방안을 검토해보자. 
  - {a,b,c} 멱집합을 반환하는 Collection을 만든다고 했을때, 모든 집합을 담는 리스트를 반환한다면 데이터가 매우 커진다 .
  - 예시처럼 AbstractList를 만들어 반환해보자 (실제 데이터는 넣지 않고 연산에 의해 반환)
    - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch07/item47/PowerSet.java)

---
### 아이템 48 스트림 병렬화는 주의해서 적용하라
- 데이터 소스가 Stream.iterate거나 중간 연산으로 limit를 쓰면 파이프라인 병렬화로는 성능 개선을 기대할 수 없다. 
  - ArrayList, HashMap, HashSet,의 인스턴스이거나, int & long 범위 일때 병렬화 효과가 아주 좋다.
    - 해당 자료구조들은 데이터를 원하느 ㄴ크기로 정확하게 나눌수 있어, 스레드 분배하기에 좋다. 
    - 나누는 작업은 "Spliterator"가 담당하며, Spliterator 객체는 Stream/Iterable의 메서드로 가져올 수 있다.
- 파이프라인 병렬화는 limit를 다룰 때 CPU 코어가 남는다면 원소를 몇개 더 처리한 후 제한된 개수 이후의 결과를 버려도 아무런 해가 없다고 가정한다.
- 