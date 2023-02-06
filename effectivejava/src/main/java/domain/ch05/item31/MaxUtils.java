package domain.ch05.item31;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaxUtils<E> {

    /**
      * list1, list2는 이 함수에서 사용하는 데이터의 "공급자" 이기 때문에, List<? extends E> 를 사용한다
     * 반환값은, 함수 내에서 추출되는 E 타입 값을 사용하여 max를 계산하여, 반환하는 "소비자"이기 때문에 반환타입을 super을 사용하여 반환한다.
         * 값 비교를 위해 Recursive Type Bound로 Comparable을 추가하였다.
    */

    public static <E extends Comparable<? super E>> E max(List<? extends E> list1,
        List<? extends E> list2) {

        E maxOfList1 = Collections.max(list1);
        E maxOfList2 = Collections.max(list2);
        return Collections.max(Arrays.asList(maxOfList1, maxOfList2));
    }

}
