package domain.ch07.item47;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PowerSet {
    public static final <E> Collection<Set<E>> of(Set<E> s) {
        List<E> src = new ArrayList<>(s);
        if(src.size() >= 30) {
            throw new IllegalArgumentException("집합에 원소가 너무 많습니다");
        }

        return new AbstractList<Set<E>>() {
            @Override
            public Set<E> get(int index) {
                Set<E> result = new HashSet<>();

                // index >>= 1   : index 를 오른쪽으로 shift 한다.  7(111) >>= 1 은 3(11) 이다.
                for(int i= 0; index != 0; i++, index >>=1) {
                    if((index & 1) == 1) { // index 2비트 맨 앞자리가 1인가?
                        result.add(src.get(i));
                    }
                }
                return result;
            }/// 1,2,3,
            ///{},1 / 2 / 3 / 1,2 / 1,3 / 2,3 / 1,2,3

            @Override
            public int size() {
                return 1 << src.size(); // 2^n, n개의 원소에 대한 멱집합은 2^n개이다.
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set) o);
            }
        };
    }
}
