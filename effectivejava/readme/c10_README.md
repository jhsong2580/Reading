# 예외 

---
### 아이템69 예외는 진짜 예외 상황에만 사용하라
- 예외는 오직 예외 상황에서만 사용해야 하고 절대로 제어 흐름 용으로 쓰여선 안된다.
- 잘 설계된 API라면 클라이언트가 정상적인 제어 흐름에서 예외를 사용할 일이 없어야 한다. 

---
### 아이템70 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라 
- 호출하는 쪽에서 복구하리라 여겨지는 상황이라면 CheckedException을 사용하라
  - 호출하는 쪽에 복구가 강제되기 때문에 확실하다. 
- 프로그래밍 오류를 나타낼 때는 런타임 예외를 사용하자.
  - 런타임 예외의 대부분은 "전제조건"을 지키지 못했을때 발생한다. ex) ArrayIndexOutOfBOundsException
- throwable은 정상적인 검사보다 나을게 없으면서 API사용자를 햇갈리게 한다. 

---
### 아이템71 필요 없는 검사 예외 사용은 피하라 
- Checked Exception를 제대로 사용하면 API와 프로그램의 질을 높일 수 있다. 
- 하지만 API호출자가 예외상황에서 복구할 방법이 없다면, UnCheckedException을 던지거나 Optional을 반환해도 될지 확인하자.

---
### 아이템72 표준예외를 사용하라 
- IllegalArgumentException
  - 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을때 발생 (인수값이 잘못되면 예외발)
- NullPointException 
  - 잘못된 null참조
- IndexOutBoundsException
  - 시퀀스의 허용 범위를 넘을때 발생 
- ConcurrentModificationException
  - 단일 스레드에서 사용할 객체를 다중 스레드에서 참조할때 발생 
- UnsupportedOperationException
  - 호출된 메서드를 지원하지 않을 때 
- IllegalStateException
  - 인수값이 어떻든 실패했을때 예외 발생.

---
### 아이템73 추상화 수준에 맞는 예외를 발생하라
- 상위 계층에서는 저수준 예외를 잡아 자신의 추상화 수준에 맞는 예외로 바꿔 던져야 한다. 
```
try{

}catch (LowLevelException e) {
    throw new HigherLevelException(...);
}
```

---
### 아이템74 메서드가 던지는 모든 예외를 문서화 하라 
- CheckedException은 항상 따로따로 선언하고, 각 예외가 발생하는 상황을 Javadoc의 @throws 태그를 사용하여 정확히 문사화 하라 
- 메서드가 던질수 있는 예외를 @throws태그로 문서화 해되, UnCheckedException은 @throws 목록에 넣지 말자
- 한 클래스에 정의 된 많은 메서드가 같은 이유로 같은 예외를 던진다면 그 예외를 클래스 설명에 추가하는 방법도 있다. 
  - 클래스 문서화 주석에 "이 클래스의 모든 메서드는 인수로 null이 넘어오면 NPE를 던진다"라고 할수 있다. 

---
### 아이템 75 예외의 상세 메세지에 실패 관련 정보를 담아라 
- 예외를 잡지못해 프로그램이 실패하면 그 예외의 스택 추적 정보를 자동으로 출력한다. 
  - 예외 객체의 toString메서드를 호출한다. 
  - 예외 상황을 포착하려면 관여된 모든 매개변수와 필드 값을 실패 메세지에 담아야 한다. 
    - ex) IndexOutOfBoundsException 에는 범위의 최솟값과 최댓값, 그리고 범위를 벗어났다는 인덱스 값을 담아야 한다. 
  - 예외를 만들때 User에게 보여줄 "친절한 메세지"와 개발자들에게 보여줄 "정보"를 넣어주자
    - ex) IndexOutOfBoundsException는 생성자에 index를 넣어주게 되면 "친절한 메세지"도 자동으로 만들어준다. 

---
### 아이템 76 가능한 실패 원자적으로 만들라 
- 호출된 메서드가 실패하더라도 해당 객체는 메서드 호출 전 상태를 유지해야 한다. 
- 방법
  1. 객체를 불변 객체로 설계하라. 불변객체로 는 태생적으로 실패 원자적이다. 
  2. 가변 객체라면 작업 수행에 앞서 매개변수의 유효성을 검사하는 것이다. (객체 값이 오염되기 전에 사전에 차단하는 방법이다)
     - 아래 코드에서 size가 0이면 예외를 던진다. 
     - size 0검사 코드가 없어도 IndexOutBoundException을 던지는데, 에러 의미는 EmptyStackException이 더 맞다 (추상 단계에 맞는 에러를 반환하자)
        ```
        public Object pop() {
           if (size == 0) {
               throw new EmptyStackException();     
          }
           Object result = elements[--size];
        }
     
        ```
  3. 객체의 임시 복사본에서 작업을 수행한 다음, 작업이 성공적으로 완료가 되면 원래 객체와 교환하는 방법 
  4. 작업 도중 발생하는 실패를 가로채는 복구 코드를 작성하여 작업 전 상태로 되돌리는 방법

---
### 아이템77 예외를 무시하지 말라 
- catch 블록을 비워두지말자. 비워두려면 사유를 주석으로 꼭 넣어두자. 