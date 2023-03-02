package ch08;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch08.item50.Period;
import domain.ch08.item52.Champagne;
import domain.ch08.item52.CollectionClassifier;
import domain.ch08.item52.SparklingWine;
import domain.ch08.item52.Wine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void 방어적_복사본_실패_예시() {
        //given
        Date start = new Date(10000000);
        Date end = new Date(30000000);
        Period period = Period.of(start, end);

        //when
        boolean expectFalse = period.checkDateValid();
        start.setTime(40000000);
        boolean expectTrue = period.checkDateValid();

        //then
        assertAll(
            () -> assertThat(expectFalse).isFalse(), // 처음엔 assert를 통과하지만
            () -> assertThat(expectTrue).isTrue()    // 참조 타입이기 때문에 값이 외부에서 변경되는걸 막을수 없다.
        );
    }

    @Test
    public void 방어적_복사본_성공_예시() {
        //given
        Date start = new Date(10000000);
        Date end = new Date(30000000);
        Period period = Period.defenceCopyOf(start, end);

        //when
        boolean expectFalse = period.checkDateValid();
        start.setTime(40000000);
        boolean expectFalse1 = period.checkDateValid();

        //then
        assertAll(
            () -> assertThat(expectFalse).isFalse(), // 처음에 assert를 통과하고
            () -> assertThat(expectFalse1).isFalse()    // 방어적 복사를 했기 때문에 외부에서 변경이 불가능하다
        );
    }

    @Test
    public void 다중정의_실패예시() {
        //given
        Collection<?>[] collections = {
            new HashMap<String, String>().values(),
            new ArrayList<Integer>(), new HashSet<String>()
        };

        //when
        List<String> result = Arrays.stream(collections).map(
            CollectionClassifier::classify
        ).collect(Collectors.toList());

        //then
        assertThat(result).containsExactly("그 외", "그 외", "그 외");
    }

    @Test
    public void 오버라이딩_성공예시() {
        //given
        List<Wine> wines = Arrays.asList(new Wine(), new SparklingWine(), new Champagne());

        //when
        List<String> wineNames = wines.stream()
            .map(Wine::name)
            .collect(Collectors.toList());

        //then
        assertThat(wineNames).containsExactly(
            "포도주", "발포성 포도주", "샴페인"
        );
    }

    @Test
    public void 다중정의_실패예시_2() {
        //given
        List<Wine> wines = Arrays.asList(new Wine(), new SparklingWine(), new Champagne());

        //when
        List<String> wineNames = wines.stream()
            .map(CollectionClassifier::classify)
            .collect(Collectors.toList());

        //then
        assertThat(wineNames).containsExactly(
            "포도주", "포도주", "포도주"
        );
    }

    @Test
    public void List클래스의_잘못된_오버로딩_예시() {
        //given
        Set<Integer> set = new TreeSet<Integer>();
        List<Integer> list = new ArrayList<>();

        //when
        for (int i = -3; i < 3; i++) {
            set.add(i);  // -3, -2, -1, 0, 1, 2
            list.add(i); // -3, -2, -1, 0, 1, 2
        }

        for (int i = 0; i < 3; i++) {
            set.remove(i); // Set.remove(Object o),
            list.remove(i); // List.remove(Integer index), List.remove(Object o);
        }

        //then
        assertAll(
            () -> assertThat(set).containsExactly(-3, -2, -1),
            () -> assertThat(list).containsExactly(-2, 0, 2)
            // 우리는 Object를 지운걸로 생각하지만, 오버로딩된 메서드기 때문에
            // 컴파일 단계에서 Integer가 채택되어 "인덱스 기반으로 삭제가 된다"
        );
    }
}
