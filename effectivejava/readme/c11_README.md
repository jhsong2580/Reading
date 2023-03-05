# 동시성 

---
### 아이템78 공유 중인 가변 데이터는 동기화 해서 사용해라
- 동기화 없이는 한 스레드가 만든 변화를 다른 스레드에서 확인하지 못할 수 있다.
- 예를들어 변수를 읽는 동작은 atomic이지만, 자바 언어는 스레드가 변수를 읽을때 '항상 수정이 반영 된 값을 얻는다'라는걸 보장하지 않는다. 
  - 가시성 문제 
- [가시성_문제에_의한_영원히_동작하는_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch11/StopThread.java)
- [동기화를통한_가시성문제_해결_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch11/StopThread.java)
- [volatile_변수를_사용한_가시성문제해결_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/main/java/domain/ch11/StopThread.java)
  - 하지만 volatile변수는 가시성 문제는 해결할수 있으나, 동시성 문제는 해결할 수 없다. 
  - 동시성 문제까지 해결하려면 Atmoic 변수를 사용하자. 

---
### 아이템79 과도한 동기화는 피하라 
- 응답 불가와 안전 실패를 피하려면 동기화 메서드나 동기화 블록 안에서는 제어를 절대 클라이언트에게 양도하면 안된다. 
  - 동기화된 영역 내에서는 재정의 할수 있는 메서드는 호출하면 안되며, 클라이언트가 넘겨준 함수를 호출해서도 안된다. 

---
### 아이템80 스레드보다는 실행자, task, stream을 애용하라
- [Executor학습_예시](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/ch11/Example.java)
- ExecutorService.execute 는 Runnable을 통해 실행되며, 종료 여부를 알수 없다.
- ExecutorService.submit 은 Callable을 받아 실행되며, 종료 여부를 알 수 있고 wait 또한 할 수 있다. 
- Task?
  - Runnable 
  - Callable : 값을 반환하고, 임의의 예외를 던질 수 있다.
- fork-join task?
  - ForkJoinTask의 인스턴스는, 작은 하위 Task로 나뉠수 있고, ForkJoinPool을 구성하는 스레드들이 이 Task들을 처리하며, 일을 먼저 끝낸 Thread는 남은 Task를 가져와 대신 처리 할 수도 있다.
  - 
---
### 아이템 81 wait 와 notify보다는 동시성 유틸리티를 이용해라 
- wait와 notify는 올바르게 이용하기 매우 까다로우니, 고수준 동시성 유틸리티를 이용하자 
1. 동시성 컬렉션
   - List, Queue, Map 같은 표준 컬렉션 Interface에 동시성을 가미해 구현한 고성능 컬렉션
   - 동시성 컬렉션에서, 동시성을 무력화 하는것은 불가능하며, 외부에서 락을 추가로 사용하면 오히려 속도가 느려진다. 
     - ConcurrentHashMap 코드를 보니 synchronized 블럭을 통해 동시성 제어를 하는것 같다. 
   - 동시성 컬렉션이 나온 이후로, 동기화한 컬렉션을 많이 안쓰게 된다. 
   
   ``` 
   Map<String, String> syncMap = Collections.synchronizedMap(dummyMap); // 동기화한 컬렉션
    public V get(Object key) { // Mutex 자원을 통한 동시성처리
            synchronized (mutex) {return m.get(key);}
    }
   
   
   Map<String, String> concSyncMap = new ConcurrentHashMap<String, String>(); //동시성 컬렉션
   // 원자성을 위해 복사한 Map을 통해 데이터를 관리하고, Atmoic하게 내가 가져올 때의 값과 현재 값을 비교하여 오염되지 않은 값이면 뭔가 동작을 하게 한다.
   ```