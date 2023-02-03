package ch04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch04.item18.InstrumentedHashSet;
import domain.ch04.item24.MemberClass;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void BigInteger은_불변객체이다 (){
        //given
        BigInteger a = BigInteger.valueOf(3L);
        BigInteger b = BigInteger.valueOf(4L);

        //when
        BigInteger c = a.add(b);

        //then
        assertAll(
            () -> assertThat(a.intValue()).isEqualTo(3), // a는 값이 그대로 있다.
            () -> assertThat(b.intValue()).isEqualTo(4), // b는 값이 그대로 있다.
            () -> assertThat(c.intValue()).isEqualTo(7)
        );
    }

    @Test
    public void 불변객체의단점은_값이다르면_무조건_새로운객체로만든다 (){ // 매우 시간이 오래걸린다.
        //given

        BigInteger a = BigInteger.valueOf(3L);
        BigInteger b = BigInteger.valueOf(5L);

        //when
        for(int i=0; i<1000000000; i++){
            a = a.add(b);
        }

        //then
        assertThat(a.longValue()).isEqualTo(5L * 1000000000 + 3);
    }

    @Test
    public void 가변객체는_새로운객체로만들지않아_속도가빠르고_메모리를_효율적으로쓴다 (){
        //given
        long a = 3L;
        long b = 5L;

        //when
        for(int i=0; i<1000000000; i++){
            a+=b;
        }

        //then
        assertThat(a).isEqualTo(5L * 1000000000L + 3L);
    }

    @Test
    public void 잘못된_상속은_예상치못한에러를_발생시킨다 (){
        //given
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();

        //when
        s.addAll(List.of("hey", "hello", "houe"));
        /**
          * addCount는 3이여야 맞지만, hashSet.addAll() 에서 호출하는 add()는 InstrumentedHashSet.add()를 호출하게 되어 addCount가 두번씩 저장되어 배가 된다.
        */

        //then
        assertThat(s.getAddCount()).isNotEqualTo(3);
    }

    @Test
    public void templateMethod (){
        //given & when
        List<Integer> listByTemplateMethod = getListByArray(new int[]{1, 2, 3, 4, 5});

        //then
        assertAll(
            () -> assertThat(listByTemplateMethod.size()).isEqualTo(5),
            () -> assertThat(listByTemplateMethod.get(0)).isEqualTo(1),
            () -> assertThat(listByTemplateMethod.get(5)).isEqualTo(null),
            () -> assertThat(listByTemplateMethod.isEmpty()).isEqualTo(false),
            () -> assertThatThrownBy(
                () -> listByTemplateMethod.add(4)
            ).isInstanceOf(UnsupportedOperationException.class) // set은 재정의하지 않으면 템플릿 메서드에 의해 에러를 발생시킨다
        );
    }

    private List<Integer> getListByArray(int[] arr) {
        return new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                if (checkOutOfIndex(index)) {
                    return null;
                }
                return arr[index];
            }

            @Override
            public Integer set(int index, Integer element) {
                return super.set(index, element);
            }

            private boolean checkOutOfIndex(int index) {
                return index < 0 || index >= arr.length;
            }

            @Override
            public int size() {
                return arr.length;
            }
        };
    }

    @Test
    public void MemberClassTest (){
        //given
        MemberClass memberClass = new MemberClass();

        //when
        int result = memberClass.doSomethingInTopClass();

        //then
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void HashMapAdapter (){
        //given
        HashMap<String, String> myMap = new HashMap<>();
        myMap.put("key", "value");

        //when
        Set<String> keySet = myMap.keySet(); // Set Interface로 보이나, Set을 구현한 HashMap.KeySet이다.

        //then
        keySet.forEach(s -> System.out.println(s)); // debug를 해보면 HashMap 의 멤버클래스인 KeySet.forEach가 구동되는 것을 볼수 있다.

    }
}
