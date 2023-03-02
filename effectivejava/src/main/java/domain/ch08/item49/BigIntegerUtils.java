package domain.ch08.item49;

import java.math.BigInteger;

public class BigIntegerUtils {

    /**
     * @param m 계수(양수여야 한다)
     * @return 현재 값 mod m
     * @throws ArithmeticException m이 0보다 작거나 같으면 발생한다.
    */
    public BigInteger mod(BigInteger m) {
        if(m.signum() <= 0) {
            throw new ArithmeticException("계수(m)는 양수여야 합니다. " + m);
        }
        return null; // 테스트용이므로 별도 처리는 하지 않는다. 관심가지지말자
    }

}
