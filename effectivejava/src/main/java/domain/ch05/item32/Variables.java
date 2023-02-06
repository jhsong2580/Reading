package domain.ch05.item32;

import java.util.List;

public class Variables<T> {

    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = List.of(42);
        Object[] objects = stringLists; // stringList는 가변변수로, 배열로 묶이기 때문에 상위 Object[]가 받을 수 있다.

        objects[0] = intList; //오염발생
    }

    public <T> T[] toArray(T... args) {
        // 여기서 Object[]로 반환된다.
        return args;
    }

    public <T> T[] pickTwo(T a, T b, T c) {
        return toArray(a, b);
    }
}
