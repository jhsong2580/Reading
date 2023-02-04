package ch04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch04.item18.InstrumentedHashSet;
import domain.ch04.item23.Circle;
import domain.ch04.item24.AnonymousClass;
import domain.ch04.item24.AnonymousParent;
import domain.ch04.item24.MemberClass;
import domain.ch04.item24.PrivateStaticMemberClass;
import domain.ch04.item24.PrivateStaticMemberClass.TestShape;
import domain.ch04.item25.TestTopClass;
import java.math.BigInteger;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void BigInteger은_불변객체이다() {
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
    public void 불변객체의단점은_값이다르면_무조건_새로운객체로만든다() { // 매우 시간이 오래걸린다.
        //given

        BigInteger a = BigInteger.valueOf(3L);
        BigInteger b = BigInteger.valueOf(5L);

        //when
        for (int i = 0; i < 1000000000; i++) {
            a = a.add(b);
        }

        //then
        assertThat(a.longValue()).isEqualTo(5L * 1000000000 + 3);
    }

    @Test
    public void 가변객체는_새로운객체로만들지않아_속도가빠르고_메모리를_효율적으로쓴다() {
        //given
        long a = 3L;
        long b = 5L;

        //when
        for (int i = 0; i < 1000000000; i++) {
            a += b;
        }

        //then
        assertThat(a).isEqualTo(5L * 1000000000L + 3L);
    }

    @Test
    public void 잘못된_상속은_예상치못한에러를_발생시킨다() {
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
    public void templateMethod() {
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
            ).isInstanceOf(UnsupportedOperationException.class)
            // set은 재정의하지 않으면 템플릿 메서드에 의해 에러를 발생시킨다
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
    public void MemberClassTest() {
        //given
        MemberClass memberClass = new MemberClass();

        //when
        int result = memberClass.doSomethingInTopClass();

        //then
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void HashMapAdapter() {
        //given
        HashMap<String, String> myMap = new HashMap<>();
        myMap.put("key", "value");

        //when
        Set<String> keySet = myMap.keySet(); // Set Interface로 보이나, Set을 구현한 HashMap.KeySet이다.

        //then
        keySet.forEach(s -> System.out.println(
            s)); // debug를 해보면 HashMap 의 멤버클래스인 KeySet.forEach가 구동되는 것을 볼수 있다.
    }

    @Test
    public void PrivateMemberClassTest() {
        //given
        PrivateStaticMemberClass dataFactory = new PrivateStaticMemberClass();

        //when
        TestShape circle = dataFactory.getCircle(1);
        TestShape rectangle = dataFactory.getRectangle(3, 5);

        //then
        assertAll(
            () -> assertThat(circle.area()).isEqualTo(3.141592653589793),
            () -> assertThat(rectangle.area()).isEqualTo(15.0)
        );
    }

    @Test
    public void AnonymousClassTest() {
        double LOCAL_ANONYMOUS_CLASS_AREA = 9998;
        double FIELD_ANONYMOUS_CLASS_AREA = 9999;

        //given
        AnonymousClass dataFactory = new AnonymousClass();

        //when
        Circle localAnonymous1 = dataFactory.getLocalAnonymous();
        Circle localAnonymous2 = dataFactory.getLocalAnonymous();
        Circle fieldAnonymous1 = dataFactory.getFieldAnonymous();
        Circle fieldAnonymous2 = dataFactory.getFieldAnonymous();

        //then
        assertAll(
            // 익명 클래스에서 선언한 area를 반환한다.
            () -> assertThat(localAnonymous1.area()).isEqualTo(LOCAL_ANONYMOUS_CLASS_AREA),
            () -> assertThat(fieldAnonymous1.area()).isEqualTo(FIELD_ANONYMOUS_CLASS_AREA),

            // local 익명클래스는 항상 새로 생성된다.
            () -> assertThat(localAnonymous1).isNotEqualTo(localAnonymous2),

            // field 익명 클래스는 항상 존재한다.
            () -> assertThat(fieldAnonymous1).isEqualTo(fieldAnonymous2)
        );
    }

    @Test
    public void AnonymousInterfaceTest() {
        //given
        AnonymousParent anonymousParent = (value) -> {
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        };

        //when & then
        assertAll(
            () -> assertThat(anonymousParent.valueOf("1000")).isEqualTo(1000),
            () -> assertThat(anonymousParent.valueOf("it will return 0")).isEqualTo(0)
        );
    }

    @Test

    public void TopLevelClassTest (){
        //given
        TestTopClass testTopClass = new TestTopClass();

        assertAll(
            () -> assertThat(testTopClass.getDessert()).isEqualTo("cake"),
            () -> assertThat(testTopClass.getUtensil()).isEqualTo("pan")
        );
    }
}
