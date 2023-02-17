# 스트림 학습 

---
### 스트림의 장점 
- 내부 반복자를 사용하므로 병렬 처리가 쉽다
  - 외부 반복자 : 개발자가 코드로 직접 컬렉션 요소를 반복해서 요청하고 가져오는 코드 패턴 
  - 내부 반복자 : 컬렉션 내부에서 요소를 반복시키고, 개발자는 요소 당 처리해야하는 코드만 제공하는 패턴 
  - 내부 반복자의 이점
    - 개발자는 요소 처리 코드에만 집중할 수 있다. 
    - 멀티코어 CPU를 최대한 활용하기 위해 요소들을 분배시켜 병렬 처리 작업을 할 수 있다. 
  - 병렬 처리 
    - 한가지 작업을 서브 작업으로 나누고, 서브 작업들을 분리된 스레드에서 병렬적으로 처리한 후, 서브 작업들의 처리 결과를 최종 결합하는 방법 
    - JAVA는 ForkJoinPool 프레임 워크를 이용하여 병렬처리를 한다. 
    - [StreamParallelTest](https://github.com/jhsong2580/Reading/blob/master/effectivejava/src/test/java/StreamStudy.java)

---
### 스트림의 종류 
- 자바 8 부터 java.util.stream 패키지 에서 인터페이스 타입으로 제공한다. 
- 모든 스트림에서 사용할 수 있는 공통 메서드들이 정의 되어 있는 BaseStream 아래 객체와 타입 요소를 처리하는 스트림이 있다
  - BaseStream은 공통 메소드 들이 정의되어 있고 코드에서 직접 사용하지 않는다. 
- 스트림 구현 객체를 얻는 방법 

| 리턴 타입                                          |                                 메소드                                  |                         소스                         |
|:-----------------------------------------------|:--------------------------------------------------------------------:|:--------------------------------------------------:|
| Stream<T>                                      |           Collection.stream(), Collection.parallelStream()           |                        컬렉션                         |
| Stream<T>, IntStream, LongStream, DoubleStream |                  Arrays.stream(T[]), Stream.of(T[])                  |                         배열                         |
| IntStream                                      |      IntStream.range(int, int), IntStream.rangeClosed(int, int)      | int 숫자이며, rangedClose()는 끝값을 포함, range()는 포함하지 않는다 |
| LongStream                                     |   LongStream.range(long, long), LongStream.rangeClosed(long, long)   |                   IntStream과 동일                    |
| Stream<Path>                                   | Files.find(Path, int BiPredicate, FileVisitOption), Files.list(Path) |   Path경로에서 BiPredicate가 True인 Path의 Stream을 얻는다    |
| Stream<String>                                 |           Files.lines(Path, Charset), BufferReader.lines()           |                file의 라인에 대한 Stream                 |
| DoubleStream, IntStream, LongStream            |          Random.doubles(..), Random.ints(), Random.longs()           |                file의 라인에 대한 Stream                 |

---
### 스트림 파이프라인
- 중간 처리와 최종 처리 
  - 리덕션(Reduction)
    - 대량의 데이터를 가공해서 축소하는 것을 말한다. 
      - 합계, 평균값, 카운팅, 최대값, 최소값등을 집계한다. 
    - 요소가 리덕션의 결과물로 바로 집계할 수 없는 경우중간 처리가 필요하다. 
      - 중간처리 : 필터링, 매핑, 정렬, 그루핑 
    - 중간 처리한 요소를 최종 처리해서 리덕션 결과물을 산출한다. 
  - 스트림은 중간 처리와 최종 처리를 파이프라인으로 해결한다. 
    - 파이프라인 : 스트림을 파이프처럼 이어 놓은것을 말한다. 
      - 중간처리 메소드(필터링, 매핑, 정렬)는 중간 처리된 스트림을 리턴하고, 이 스트림에서 다시 중간 처리 메소드를 호출해서 파이프라인을 형성하게 된다. 
    - 최종 스트림의 집계 기능이 시작되기 전까지 중간 처리는 지연된다. 
      - 최종 스트림이 시작하면 비로소 컬렉션에서 요소가 하나씩 중간 스트림에서 처리되고 최종 스트림까지 오게된다. 
---
### 매핑 중간 처리 (flatMapXXX, mapXXX, asXXXStream, box)
- 매핑은 중간처리 기능으로 스트림의 요소를 다른 요소로 대체한다. 
  - 객체를 정수로, double로, boolean값으로 ...
- flatMapXXX();
  - 한개의 요소를 대체하는 복수개의 요소들로 구성된 새로운 스트림을 리턴
- mapXXX();
  - 요소를 대체하는 요소로 구성된 새로운 스트림을 리턴 
- sorted()
  - 정렬 중간처리
- allMatch / anyMatch / noneMatch 
  - 주어진 요소들에 대한 점검 처리 