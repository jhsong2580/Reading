# 제네릭

---
### raw type은 사용하지 말자
- raw type사용시 발생하는 문제
```
private final Collection stamps = ...;
stamps.add(new Stamp());
stamps.add(new Coin()); //에러가 나지 않는다. 

for (Iterator i =stams.iterator(); i.hasNext();) {
    Stamp stamp = (Stamp) i.next(); // CAST Error가 발생한다. 
}
```
- 제너릭을 사용하여 컴파일 단계에서 타입 체크
```
private final Collection<Stamp> stamps = ....;
stamps.add(new Stamp());
stamps.add(new Coin()); // 컴파일 에러 발생
```

### 비한정적 와일드카드 타입을 사용하라
- 비한정 와일드카드는 Set<?> 으로 선언한다. 
- raw type은 어떤 원소나 넣을수 있지만 <?>은 null을 제외하고 어떤 원소도 넣을수 없다.
  - 컴파일 단계에서 에러가 발생한다. 
  - 당연히 받을수 있는 객체 타입도 알수 없다.
- [WildcardTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
### class 리터럴에는 로 타입을 써야한다. 
- List.class, String[].class 는 허용되나 List<String>.class 는 허용되지 않는다.
  - [ClassLiteralTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
- 런타임에는 제너릭 타입 정보가 지워지므로 instanceof 연산자는 비한정적 와일드카드 타입 외에는 매개변수화 타입에 적용할수 없다. 
- 로 타입이든 ?든 instanceof는 똑같이 동작한다. 

### class 리터럴? 타입 토큰? 슈퍼타입 토큰?
- Class 리터럴은 String.class, Integer.class등을 말한다. 
- 타입토큰은 "타입을 나타내는 토큰"이며, Class 리터럴이 타입 토큰으로써 사용된다. 
- myMethod(Class<?> clazz) 는 "타입토큰"을 인자로 받는 메서드
  - myMethod(String.class)로 호출하면 String.class라는 Class 리터럴을 함수에 전달한다. 
- Class 리터럴은 제너릭을 사용하지 못하고, raw type만 지원하는 단점이 있다. 
  - 이부분을 수퍼타입 토큰으로 해결 할 수 있다!
- [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch05/item26/ClassLiteralSource.java)

|     한글용어      |          영문용어           |                예                 |
|:-------------:|:-----------------------:|:--------------------------------:|
 |   매개변수화 타입    |   Parameterized type    |           List<String>           |
 |  실제 타입 매개변수   |  Actual Type Parameter  |              String              |
 |    제네릭 타입     |      Generic Type       |             List<E>              |
 |  정규 타입 매개변수   |  formal type parameter  |                E                 |
 | 비한정적 와일드카드 타입 | unbounded wildcard type |             List<?>              |
 |     로 타입      |        raw type         |               List               |
 |  한정적 타입 매개변수  | bounded type parameter  |        <E extends Number>        |
 |   재귀적 타입 한정   |  recursive type bound   |    <T extends Comparable<T>>     |
 |   한정적 와일드카드   |  bounded wildcard type  |      List<? extends Number>      |
 |    제네릭 메서드    |     generic method      | static <E> List<E> asList(E[] a) |
 |     타입 토큰     |       type token        |           String.class           |

---
### 아이템27 비검사 경고를 제거하라 
- 비검사 경고 : 형변환, 메서드 호출, 매개변수화 가변인수 타입경고, 등등...
  - 이러한 비검사 경고들이 발생하면 무조건 해결하고 넘어가자 
- 만약 경고를 제거할수는 없지만 타입이 안전하다고 확신하면 @Suppress Warnings("unchecked") 를 달아 경고를 숨기자.
  - 하지만 이건 Class Casting 에러를 잠재하는 부분을 무시하는 행위이기 때문에 "최대한 좁은 범위에 설정"하고 "경고를 무시해도 되는 이유를 항상 주석으로 설명" 해야한다. 

---
### 아이템 28 배열보다는 리스트를 사용하라
- 배열과 제네릭 타입의 중요한 차이
  1. [배열은_공변하다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
     - 공변하다 : 함께 변한다 
  2. [제네릭은_불공변하다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
  3. 배열은 실체화 된다. 
     - 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다. (그렇기 때문에 타입 체크 exception이 발생한다)
     - 제네릭은 타입 정보가 런타임때는 소거된다. 
       - 컴파일 단계에서 타입 체크를 하기 때문이다.
     - [제네릭은_컴파일단계에서_타입체크를한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
  4. 제네릭은 실체화 불가하다 
     - 실체화되지 않는다는 것은, 런타임에는 컴파일 타임보다 타입정보를 적게 가지는 타입
       - 소거 메커니즘 때문에, 실체화 될 수있는 타입은 List<?>과 Map<?,?> 같은 비한정적 와일드카드 타입 뿐이다. 
     
- [배열은_런타임시_casting Exception이 발생할수 있다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch05/item28/ChooserWithArray.java)
- [리스트를 통해 컴파일 단계에서 casting Exception을 방어하자](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch05/item28/ChooserWithList.java)

---
### 이왕이면 제네릭 타입클래스로 만들어라
---
### 이왕이면 제네릭 메서드로 만들어라

---
### 한정적 와일드카드를 사용해 API 유연성을 높여라
- 매개변수화 타입(List<String>)은 불공변 하기 때문에 상위/하위타입의 매개변수타입이 서로 다형성을 지키는 것이 불가능해진다.  
- [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch05/item31/Stack.java)
  - push(E e) 함수는 E의 하위 타입에 대해 타입 체크가 되어 사용이 될수 있다. 
    - E 가 Number일때 e에 Integer을 넣어도 동작 
  - pushAll(Iterator<? extends E> src) 함수 또한 Number을 상속한 Integer의 Iterator를 인자로 넣을수 있다. 
    - 만약 제네릭 타입인 Iterator<E>를 넣는다면, Integer의 Iterator을 인자로 넣을수 없다. 
      - E가 Integer를 수용할수 있는지 "컴파일 단계에서"알수 없으며, 런타임 단계에서는 E정보가 사라진다. 
- 이렇게 유연성을 극대화 하려면 매개변수에 와일드카드 타입을 사용하라
- 소비자라면 <? super E>의 한정적 와일드카드를, 공급자라면 <? extends E>를 사용하라 
  - 공급자(생산자) : 사용할 E 인스턴스를 생산하는 인자 
  - 소비자 : 해당 함수/클래스로부터 E 인스턴스를 사용하는 인자. 
  - [BoundedWildCardTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
---
### 아이템 32 제네릭과 가변 인수를 함께 쓸 때는 신중하라 
- 가변인수 : 메서드에 넘기는 인수의 개수를 가변적으로 조절
  - 자동으로 가변인수에 대한 "배열" 이 만들어 진다. 
  - 그렇게 되면 Object[]로 다형성을 통해 받아올수 있는데 이 때문에 데이터 오염이 생긴다
    - 예시 : [dangerous](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch05/item32/Variables.java)
- 이렇게 가변 인수가 위험하지만, 가변인수 메서드가 매우 유용하여 경고만 노출시킨다. 
  - Arrays.asList(T... a)
  - Collections.addAll(Collection<? super T> c)
  - 이러한 함수엔 @SafeVarargs를 사용하여, 메서드가 타입 안전함을 보장하게 한다. 
    - 타입 안전함을 어떻게 알수 있을까? 해당 함수를 호출할때, 배열의 참조가 외부로 노출되지 않거나, 내부 값을 따로 수정하지 않는다면 안전하다. 
    - 그리고 상속이 불가능한 함수에만 @SafeVaragrs를 붙여라. 하위 함수까지 안정성을 보장하기는 힘들다. 
    - ```
      //위험 케이스
      static <T> T[] toArray(T... args) {
          return args; // 배열의 참조가 외부로 노출! 매우 위험하다  
      }    
      ```

---
### 타입 안전 이종 컨테이너를 고려하라 
- 제네릭은 Set<E>, Map<K, V> 등에서 사용된다. 이런 모든 쓰임에서 매개변수화 되는 대상은 원소가 아닌 컨테이너 자신이다. 
  - Set에는 원소의 타입을 뜻하는 하나의 타입 매개변수만 있으면 되고, Map에는 Key, Value를 위한 2개의 타입만 필요하다 
- 더 유연한 수단이 필요할 때도 있다
  - 데이터베이스의 행은 임의의 다른 타입의 열을 가질수 있어, 이런 모습에서 "이종 컨테이너 패턴"을 사용할수 있다. s
    - [예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch05/item33/Factories.java)
      - class.cast를 사용하는 이유는 내부에서 타입 체크를 하기 때문에 컴파일 시 "타입 미검사 경고" 가 뜰것이다.
- JAVA에서는 checkedSet, checkedList, checkedMap같은 메서드가 이 방식을 적용한 컬렉션 래퍼들이다.
  - 각 각 2개의 매개변수를 갖고, 해당 Collection이 타입 안전한지 확인할수 있는 List로 감싸준다 
    - [CollectionTypeCheckTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch05/Example.java)
