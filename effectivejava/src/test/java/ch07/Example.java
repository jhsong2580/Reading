package ch07;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch07.Item45.Anagrams;
import domain.ch07.item47.SubLists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
            protected boolean removeEldestEntry(Entry<String, Integer> eldest) { // ???????????? 5??? ???????????? ?????? ????????? ???????????? ????????????.
                return size() > 5;
            }
        };

        //when
        for (int i = 0; i < 12; i++) {
            cache.put(String.valueOf(i), i);
        }

        //then
        assertAll(
            () -> assertThat(cache).hasSize(5), // ?????? ????????? ???????????? ????????????, ?????? ???????????? 5??? ????????????.
            () -> assertThat(cache.values())
                .containsExactly(7, 8, 9, 10, 11) //?????? ???????????? ?????? 5?????? ???????????? ????????? ??????.
        );
    }

    @Test
    @DisplayName("UnaryOperator??? 1?????? ????????? ?????? ?????? ????????? ?????? ???????????? ????????? ?????????.")
    public void UnaryOperatorTest() {
        //given
        UnaryOperator<String> toLower = (s) -> s.toLowerCase();

        //then
        assertThat(toLower.apply("S")).isEqualTo("s");
    }

    @Test
    @DisplayName("Binary Operator??? 2?????? ????????? ?????? ????????? ?????? ????????? ?????? ???????????? ????????? ????????????")
    public void BinaryOperatorTest (){
        //given
        BinaryOperator<Integer> getLower = (i1, i2) -> Math.min(i1, i2);

        //then
        assertThat(getLower.apply(3, 5)).isEqualTo(3);
    }

    @Test
    @DisplayName("Predicate Operator??? 1?????? ????????? ?????? boolean??? ????????????")
    public void PredicateTest (){
        //given
        List<String> list = new ArrayList<>();
        Predicate<List> predicate = (arr) -> arr.isEmpty();

        //then
        assertThat(predicate.test(list)).isTrue();
    }

    @Test
    @DisplayName("Suppier??? ?????? ?????? ?????? ????????? ?????? ???????????? ????????? ????????????")
    public void SupplierTest (){
        //given
        Supplier<Integer> solution = () -> 42;

        //then
        assertThat(solution.get()).isEqualTo(42);
    }

    @Test
    @DisplayName("Consumer??? ????????? ????????? ?????? ???????????? ?????? ????????? ????????????.")
    public void ConsumerTest (){
        //given
        Consumer<String> hi = (str) -> System.out.println(str);

        //when
        hi.accept("hihi");
    }

    @Test
    public void Anagrams (){
        //given
        Anagrams anagrams = new Anagrams();

        //when
        Map<String, List<String>> dictionaryMap = anagrams.getDictionaryMapNoStream();
        Map<String, List<String>> dictionaryMapWithBadStream = anagrams.getDictionaryMapWithBadStream();
        Map<String, List<String>> dictionaryMapWithGoodStream = anagrams.getDictionaryMapWithGoodStream();

        //then
        check(dictionaryMap);
        check(dictionaryMapWithBadStream);
        check(dictionaryMapWithGoodStream);
    }

    private void check(
        Map<String, List<String>> dictionaryMap) {
        assertAll(
            () -> assertThat(dictionaryMap.keySet())
                .containsExactly("aelpst", "abcde"),
            () -> assertThat(dictionaryMap.get("aelpst"))
                .containsExactly("staple", "stplea", "petals", "ptleas"),
            () -> assertThat(dictionaryMap.get("abcde"))
                .containsExactly("abdec")
        );
    }

    @Test
    @DisplayName("???????????? ????????? ????????????")
    public void StreamBadCase1Test (){
        //given
        Map<String, Long> freq = new HashMap<>();
        List<String> strings = Arrays.asList("Abcd", "aBcd", "abCd", "abcD");

        //when
        strings.forEach(
            word -> {
                freq.merge(word.toLowerCase(), 1L, Long::sum);
            }
        );

        //then
        assertThat(freq.get("abcd")).isEqualTo(4);
    }

    @Test
    public void StreamGoodCaseTest (){
        //given
        Map<String, Long> freq ;
        List<String> strings = Arrays.asList("Abcd", "aBcd", "abCd", "abcD");

        //when
        freq = strings.stream().collect(
            Collectors.groupingBy(
                String::toLowerCase,
                Collectors.counting()
            )
        );

        //then
        assertThat(freq.get("abcd")).isEqualTo(4);
    }

    @Test
    @DisplayName("??????????????? ?????? ?????? ?????? 10?????? ???????????? ???????????????")
    public void StreamGetFreqTest (){
        //given
        List<String> strings = Arrays.asList("Abcd", "aBcd", "abCd", "abcD", "Abcd");

        //when
        List<String> collect = strings.stream()
            .sorted(comparing(String::toString).reversed()) // ??????????????? ????????? ?????? ?????? ??? ??? ????????????.
            .limit(10)
            .collect(Collectors.toList());

        //then
        assertThat(collect)
            .containsExactly("abcD", "abCd", "aBcd", "Abcd", "Abcd");
    }

    private static enum TestEnum {
        V1, V2, V3
    }


    @Test
    @DisplayName("toMap ???????????? ???????????? ???????????? ?????? ?????? ????????? ??????")
    public void StreamToMapTest() {
        //when
        Map<String, TestEnum> enums = Stream.of(TestEnum.values())
            .collect(
                Collectors.toMap(
                    Enum::name, //key setting
                    testEnum -> testEnum //value setting
                )
            );

        assertAll(
            () -> assertThat(enums.keySet())
                .containsExactly("V1", "V2", "V3"),
            () -> assertThat(enums.values())
                .containsExactly(TestEnum.V1, TestEnum.V2, TestEnum.V3)
        );
    }

    private static class Album {

        private String name;
        private long sales;

        public Album(String name, long sales) {
            this.name = name;
            this.sales = sales;
        }

        public String getName() {
            return name;
        }

        public long getSales() {
            return sales;
        }

        @Override
        public String toString() {
            return "Album{" +
                "name='" + name + '\'' +
                ", sales=" + sales +
                '}';
        }
    }
    @Test
    @DisplayName("toMap ??????????????? ?????? ????????? ??????????????? ??????")
    public void StreamToMapTestWithMergeMax (){
        //given
        List<Album> albums = Arrays.asList(
            new Album("a1", 1_000),
            new Album("a1", 3_000),
            new Album("c1", 2_000),
            new Album("d1", 1_500),
            new Album("d1", 4_500)
        );

        //when
        Map<String, Album> collect = albums.stream()
            .collect(
                Collectors.toMap(
                    Album::getName, //key??? Album??? ????????????
                    a -> a,         //value??? Album ????????????
                    BinaryOperator.maxBy(comparing(Album::getSales)) //?????? ????????? ?????? Sales??? ??? ?????? ?????????.
                )
            );

        //then
        assertAll(
            () -> assertThat(collect).hasSize(3),
            () -> assertThat(collect.get("a1").getSales()).isEqualTo(3_000),
            () -> assertThat(collect.get("c1").getSales()).isEqualTo(2_000),
            () -> assertThat(collect.get("d1").getSales()).isEqualTo(4_500)
        );
    }

    @Test
    @DisplayName("toMap ??????????????? ?????? ????????? ??????????????? ??????")
    public void StreamToMapTestWithMergeLast (){
        //given
        List<Album> albums = Arrays.asList(
            new Album("a1", 1_000),
            new Album("a1", 3_000),
            new Album("c1", 2_000),
            new Album("d1", 4_500),
            new Album("d1", 1_500)
        );

        //when
        Map<String, Album> collect = albums.stream()
            .collect(
                Collectors.toMap(
                    Album::getName, //key??? Album??? ????????????
                    a -> a,         //value??? Album ????????????
                    (t, t2) -> t2  //?????? ????????? ?????? ?????? ?????? Album??? ?????????.
                )
            );

        //then
        assertAll(
            () -> assertThat(collect).hasSize(3),
            () -> assertThat(collect.get("a1").getSales()).isEqualTo(3_000),
            () -> assertThat(collect.get("c1").getSales()).isEqualTo(2_000),
            () -> assertThat(collect.get("d1").getSales()).isEqualTo(1_500)
        );
    }

    @Test
    public void StreamGroupByTest (){
        //given
        List<String> strings = Arrays.asList("Abcd", "aBcd", "abCd", "abcD", "Abcd");

        //when
        Map<String, List<String>> collects = strings.stream().collect(
            Collectors.groupingBy(
                word -> alphabetize(word), // ???????????? ????????? ???????????? key??? ??????
                Collectors.toList() //value??? List??? ?????????
            )
        );

        //then
        assertAll(
            () -> assertThat(collects.get("abcd"))
                .contains("Abcd", "aBcd", "abCd", "abcD", "Abcd")
        );


    }

    private String alphabetize(String s) {
        s = s.toLowerCase();
        char[] a = s.toCharArray();

        Arrays.sort(a);
        return new String(a);
    }

}