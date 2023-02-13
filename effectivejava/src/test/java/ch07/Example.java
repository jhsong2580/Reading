package ch07;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void AnonymousClassForStringSort() {
        //given
        List<String> words = Arrays.asList("abcde", "abcd", "abc", "ab", "a");

        //when
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });

        //then
        assertAll(
            () -> assertThat(words).containsExactly(
                "a", "ab", "abc", "abcd", "abcde"
            )
        );
    }

    @Test
    public void LamdaForStringSort() {
        //given
        List<String> words = Arrays.asList("abcde", "abcd", "abc", "ab", "a");

        //when
        Collections.sort(
            words,
            (o1, o2) -> Integer.compare(o1.length(), o2.length())
        );

        //then
        assertAll(
            () -> assertThat(words).containsExactly(
                "a", "ab", "abc", "abcd", "abcde"
            )
        );
    }

    @Test
    public void CacheWithHashMap() {
        //given
        Map<String, Integer> cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Entry<String, Integer> eldest) { // 사이즈가 5가 넘어가면 가장 오래된 데이터를 삭제한다.
                return size() > 5;
            }
        };

        //when
        for (int i = 0; i < 12; i++) {
            cache.put(String.valueOf(i), i);
        }

        //then
        assertAll(
            () -> assertThat(cache).hasSize(5), // 가장 오래된 데이터를 삭제하여, 최대 사이즈는 5로 유지된다.
            () -> assertThat(cache.values())
                .containsExactly(7, 8, 9, 10, 11) //가장 마지막에 넣은 5개의 데이터를 가지고 있다.
        );
    }

    @Test
    @DisplayName("UnaryOperator는 1개의 인수를 받아 같은 타입의 값을 리턴하는 함수를 말한다.")
    public void UnaryOperatorTest() {
        //given
        UnaryOperator<String> toLower = (s) -> s.toLowerCase();

        //then
        assertThat(toLower.apply("S")).isEqualTo("s");
    }

    @Test
    @DisplayName("Binary Operator는 2개의 인수를 받아 인수와 같은 타입의 값을 리턴하는 함수를 의미한다")
    public void BinaryOperatorTest (){
        //given
        BinaryOperator<Integer> getLower = (i1, i2) -> Math.min(i1, i2);

        //then
        assertThat(getLower.apply(3, 5)).isEqualTo(3);
    }

    @Test
    @DisplayName("Predicate Operator는 1개의 인수를 받아 boolean을 반환한다")
    public void PredicateTest (){
        //given
        List<String> list = new ArrayList<>();
        Predicate<List> predicate = (arr) -> arr.isEmpty();

        //then
        assertThat(predicate.test(list)).isTrue();
    }

    @Test
    @DisplayName("Suppier는 인수 없이 해당 타입의 값을 리턴하는 함수를 의미한다")
    public void SupplierTest (){
        //given
        Supplier<Integer> solution = () -> 42;

        //then
        assertThat(solution.get()).isEqualTo(42);
    }

    @Test
    @DisplayName("Consumer는 하나의 인수를 받고 리턴값이 없는 함수를 의미한다.")
    public void ConsumerTest (){
        //given
        Consumer<String> hi = (str) -> System.out.println(str);

        //when
        hi.accept("hihi");
    }
}