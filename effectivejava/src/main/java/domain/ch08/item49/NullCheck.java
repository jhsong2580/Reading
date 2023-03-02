package domain.ch08.item49;

import java.util.Objects;

public class NullCheck {

    /**
     * @param integer int값
     * @throws NullPointerException "integer은 null일수 없습니다"
    */
    public void example(Integer integer) {
        integer = Objects.requireNonNull(integer, "integer은 null일수 없습니다");
    }

}
