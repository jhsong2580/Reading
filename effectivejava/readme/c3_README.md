# 모든 객체의 공통 메서드
- Object는 객체들의 최상위 class이며 구체 클래스이나, 대부분 상속해서 사용한다. 
- equals, hashcode, toString, clone, finalize는 모두 overriding을 염두해 두고 설계된 것이라 규약이 명확하다. 이번 장에선 이런 규약을 다뤄본다

---
### equals는 규약을 지켜 overriding하라
- equals를 재정의하지 않아야할 때 
  1. 각 인스턴스가 본질적으로 고유하다.
     - 값을 표현하는것이 아닌 "동작하는 개체그 자체"를 표현하는 클래스가 해당한다. 
       - 예로 Thread는 값타입이 아닌 동작하는 하나의 개체이므로 equals를 override할 필요가 없다 
  2. 인스턴스의 동등성을 검사할 일이 없다. 
  3. 상위 클래스의 equals가 하위 클래스에도 적용 가능할때 
     - Set 구현체는 Abstract Set이 구현한 equals를 상속받아 쓰고, List는 abstractList로부터 그대로 사용한다.
  4. 클래스가 private이거나 package-private이고 equals를 호출할 일이 없다. 
     ```
     @Override 
     public boolean equals(Object o) {
        throw new AssertionError(); //호출 금지!!!
     }
     ```
- equals를 재정의해야할 때 
  1. 동등성을 비교해야하나, equals가 동일성만 지원할 때

- equals 메서드는 동치관계를 구현하며, 다음을 만족한다(규약)
  1. 반사성 : null이 아닌 모든 참조값 x에 대해 x.equals(x)는 true이다.
  
     - [equals는_반사성을_지켜야한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)
  
  2. 대칭성 : null이 아닌 모든 참조값 x,y에 대해 x.equals(y)는 true이면 y.equals(x)도 true이다

     - [equals는_대칭성을_지켜야한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)

  3. 추이성 : null이 아닌 x,y,z에 대해 x.equals(y), y.equals(z), z.equals(x)의 결과는 같다

     - [equals는_추이성을_지켜야한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)

  4. 일관성 : null이 아닌 x,y,z에 대해 x.equals(y)의 결과는 몇번을 수행해도 똑같다

     - [equals는_일관성을_지켜야한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)

  5. null아님 : null이 아닌 x에 대해 x.equals(null)은 항상 false를 반환한다.

     - [equals는_null과비교하면_항상false이다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)

---
### equals를 재정의하려면 hashcode도 재정의하라
- 재정의하지 않으면 hashcode일반 규약을 어기게 되어, 해당 클래스의 인스턴스를 HashMap, HashSet같은 컬렉션의 원소로 사용할때 문제를 일으킨다.
```
Object명세
1. equals 비교에 사용되는 정보가 변경되지 않았다면, 객체의 hashcode method는 일관성을 지켜야 한다. 
2. equals(Object)가 같다고 판단되면, 두 객체의 hashCode는 같은 값을 반환해야한다
3. equals(Object)가 다르더라도 두 객체의 hashCode가 다른 값을 반환 할 필요는 없다. 
```

- [hashCode선언하지않으면_hashCollection이_정상적으로_동작하지않는다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)
- [hashCode선언하면_hashCollection이_정상적으로_동작한다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)
- 만약 hashCode를 아래와 같이 재정의하면 매우 큰 문제가 발생한다.
  - 검색시 모든 객체에서 똑같은 hashCode를 반환하니, 모든 객체가 해시 테이블의 하나의 인덱스에 담겨 연결리스트처럼 동작한다. 그 결과 검색 시간이 O(1)이 아닌 O(n)시간이 걸리게 되버린다.
```
@Override public int hashCode() { return 42;}
```
- equals에 제외된 필드는 hashCode에서도 제외되어야 한다. 
  - equals가 의미하는 동등성과 hashCode가 의미하는 동등성의 의미가 같아야 한다. 
- hash계산비용이 크다면 캐싱을 하는것도 방법이다.
  - [hashCode의캐싱](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch03/item11/PhoneNumberYesHashCode.java)

---
### toString을 항상 재정의하라
- toString의 default는 "getClass().getName() + "@" + Integer.toHexString(hashCode());" 이다. 
- toString을 잘 재정의한 클래스는 디버깅 하기 훨씬 직관성이 있다.
- System.out.print 인자에 Object를 넘길시 자동으로 toString()이 호출되어 재정의 하지 않으면 예상치못한 에러를 발생할수 있다.
- toString에는 의도를 명확히 밝혀야한다.
  - [toString](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch03/item11/PhoneNumberYesHashCode.java)

---
### Clone 재정의는 주의해서 진행하라
- Cloneable은 복제해도 되는 클래스임을 명시하는 용도의 interface이다. 
  - 가장 큰 문제가 Clone메서드가 선언된 곳이 Object이고, 그 마저도 protected이다. Cloneable을 구현하는 것 만으로는 외부 객체에서 Clone메서드를 호출할 수 없다. 
- Cloneable은 메서드가 하나도 없지만 Object의 Protected 메서드인 Clone의 동작 방식을 결정한다. 
  - Cloneable을 구현한 인스턴스에서 clone()을 호출하면 구현 방식에 맞게 복사하지만, 그렇지 않은 클래스의 인스턴스에서 사용하면 Exception을 발생시킨다. 
- Object명세의 Clone 규약 
  1. x.clone() != x
  2. x.clone.getClass() == x.getClass();
  3. x.clone().equals(x)
  4. x.clone().getClass() == x.getClass()

---
### Defautl Clone 메서드를 사용할때 얕은복사를 주의해라 
- [SUPER_CLONE_사용시문제점](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)
- 위 얕은 복사를 방지하기 위해 clone메서드를 재정의 할수 있다. 
  - [CLONE_메서드_재정의시_얕은복사를_피해간다](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch03/Example.java)
  - 하지만 Object[][] 라면? ... 겉 배열은 깊은복사가 되지만 내부 배열은 얕은복사다

---
### Compareable을 구현할지 고려하라 
