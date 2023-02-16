import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
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
            () -> assertThat(notParallelList).hasSize(1), // Thread 이름은 Test Worker밖에 없다. (단일 쓰레드가 하나씩 검사)
            () -> assertThat(notParallelList.keySet()).containsExactly("Test worker"),
            () -> assertThat(parallelList).hasSizeGreaterThan(2) // 쓰레드가 최소 2개 이상 동작한다. 그리고 쓰레드 이름의 prefix는 ForkJoinPool.commonPool-worker 로 붇는다


        );
    }

    @Test
    @DisplayName("한개의 요소를 대체하는 복수개의 요소들로 구성된 새로운 스트림을 리턴")
    public void FlatMapTest (){
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
                int[] intArray = new int[strArray.length];
                for (int i = 0; i < strArray.length; i++) {
                    intArray[i] = Integer.parseInt(strArray[i].trim());
                }
                return Arrays.stream(intArray);
            })
            .forEach(System.out::println);
    }

}
