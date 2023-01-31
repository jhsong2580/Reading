package ch04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
}
