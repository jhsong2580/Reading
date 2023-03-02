import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StreamStudy {

    @Test
    public void StreamParallelTest() {
        //given
        List<String> strings = Arrays.asList("A", "B", "C", "D", "E", "F");

        //when
        Map<String, List<String>> notParallelList = strings.stream()
            .collect(
                groupingBy(
                    s -> Thread.currentThread().getName(),
                    toList()
                )
            );

        Map<String, List<String>> parallelList = strings.parallelStream()
            .collect(
                groupingBy(
                    s -> Thread.currentThread().getName(),
                    toList()
                )
            );

        //then
        assertAll(
            () -> assertThat(notParallelList).hasSize(1),
            // Thread 이름은 Test Worker밖에 없다. (단일 쓰레드가 하나씩 검사)
            () -> assertThat(notParallelList.keySet()).containsExactly("Test worker"),
            () -> assertThat(parallelList).hasSizeGreaterThan(2)
            // 쓰레드가 최소 2개 이상 동작한다. 그리고 쓰레드 이름의 prefix는 ForkJoinPool.commonPool-worker 로 붇는다

        );
    }

    @Test
    @DisplayName("한개의 요소를 대체하는 복수개의 요소들로 구성된 새로운 스트림을 리턴")
    public void FlatMapTest() {
        //given
        List<String> inputList = Arrays.asList("java8 lambda", "stream mapping");

        //when
        inputList.stream()
            .flatMap(data -> Arrays.stream(data.split(" "))) // [java8, lambda] 이렇게 스트림을 리턴한다.
            .forEach(System.out::println);

        List<String> inputList2 = Arrays.asList("10, 20, 30", "40, 50, 60");

        inputList2.stream()
            .flatMapToInt(data -> {
                String[] strArray = data.split(",");
                int[] intArray = parseIntArrayBy(strArray);
                return Arrays.stream(intArray);
            })
            .forEach(System.out::println);
    }

    private int[] parseIntArrayBy(String[] arr) {
        int[] intArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArray[i] = Integer.parseInt(arr[i].trim());
        }
        return intArray;
    }

    @Test
    @DisplayName("mapXXX는 요소를 대체하는 요소로 구성된 새로운 스트림을 리턴한다. ")
    public void MapTest() {
        //given
        String[] strings = new String[]{"a", "b", "c"};

        //when
        int sum = Arrays.stream(strings)
            .mapToInt(String::length)
            .sum();

        //then
        assertThat(sum).isEqualTo(3);
    }

    @Test
    @DisplayName("sort는 정렬해주는 중간처리 장치이다")
    public void SortTest() {
        //given
        List<String> inputList = Arrays.asList("java8 lambda", "tests mapping");

        //when
        List<Integer> collect = inputList.stream()
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .map(String::length)
            .sorted()
            .collect(toList());

        //then
        assertThat(collect)
            .containsExactly(5, 5, 6, 7); // java8, tests, lambda, mapping
    }

    @Test
    @DisplayName("box 는 boxing type으로 변경해준다.")
    public void BoxTest() {
        //given
        int[] ints = new int[]{1, 2, 3, 4, 5};

        //when
        List<Integer> lists = Arrays.stream(ints)
            .boxed()
            .collect(toList());

        //then
    }

    @Test
    @DisplayName("Match 함수를 통해 조건 결과를 얻을 수 있다.")
    public void MatchingTest() {
        //given
        int[] ints = {2, 4, 6, 8, 10, 1};
        IntPredicate intPredicate = (value) -> value % 2 == 0;

        //when
        boolean expectFalse = Arrays.stream(ints)
            .allMatch(intPredicate);
        boolean expectTrue = Arrays.stream(ints)
            .anyMatch(intPredicate);

        //then
        assertAll(
            () -> assertThat(expectTrue).isTrue(),
            () -> assertThat(expectFalse).isFalse()
        );
    }

    @Test
    @DisplayName("Aggregate를 통해 카운팅, 합계, 최대값, 평균 등 하나의 값으로 산출")
    public void AggregateTest() {
        //given
        int[] ints = new int[]{1, 2, 3, 4};

        //when
        long count = Arrays.stream(ints)
            .count();
        OptionalInt first = Arrays.stream(ints)
            .findFirst();
        OptionalInt max = Arrays.stream(ints)
            .max();
        OptionalInt min = Arrays.stream(ints)
            .min();
        OptionalDouble average = Arrays.stream(ints)
            .average();
        int sum = Arrays.stream(ints)
            .sum();

        //then
        assertAll(
            () -> assertThat(count).isEqualTo(4),
            () -> assertThat(first).isEqualTo(1),
            () -> assertThat(max).isEqualTo(4),
            () -> assertThat(min).isEqualTo(1),
            () -> assertThat(average).isEqualTo(2.5),
            () -> assertThat(sum).isEqualTo(10)
        );
    }

    @Test
    public void GroupByTest1() {
        //given
        List<Student> students = Arrays.asList(
            new Student("man1", "man"),
            new Student("man2", "man"),
            new Student("girl1", "girl"),
            new Student("girl2", "girl")
        );

        //when
        Map<String, List<Student>> collect = students.stream()
            .collect(
                groupingBy(
                    Student::getSex, // 성별을 key로 묶는다.
                    Collectors.mapping(student -> student, toList()) // 해당 값들을
                )
            );

        //then
        assertAll(
            () -> assertThat(collect.get("man"))
                .extracting("name")
                .containsExactly("man1", "man2"),
            () -> assertThat(collect.get("girl"))
                .extracting("name")
                .containsExactly("girl1", "girl2")

        );
    }

    @Test
    public void GroupByTest2() {
        //given
        List<Student> students = Arrays.asList(
            new Student("man1", "man"),
            new Student("man2", "man"),
            new Student("girl1", "girl"),
            new Student("girl2", "girl")
        );

        //when
        Map<String, List<Student>> collect = students.stream()
            .collect(
                groupingBy(
                    Student::getSex, // 성별을 key로 묶는다.
                    () -> new HashMap<String, List<Student>>(),
                    Collectors.mapping(student -> student, toList()) // 해당 값들을
                )
            );

        //then
        assertAll(
            () -> assertThat(collect.get("man"))
                .extracting("name")
                .containsExactly("man1", "man2"),
            () -> assertThat(collect.get("girl"))
                .extracting("name")
                .containsExactly("girl1", "girl2")

        );
    }
    private static class Student {

        private String name;
        private String sex;

        public Student(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public boolean isMan() {
            return sex.equals("man");
        }

        public boolean isGirl() {
            return sex.equals("girl");
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }
    }
}
