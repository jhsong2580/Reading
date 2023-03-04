package ch09;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void 잘못된_double_계산_예시 (){
        double a = 1.03;
        double b = 0.42;

        assertThat(a - b).isNotEqualTo(0.61); //0.6100000000000001이 계산된다.
    }

    @Test
    public void BoxingType_식별성_예시 (){
        //given
        Integer a = Integer.valueOf(3);
        Integer b = Integer.valueOf(3);
        Integer c = new Integer(3);
        //then
        assertThat(a == b).isTrue();
    }

    @Test
    public void Refelection_예제() {
        //given
        Class<? extends Set<String>> c1 = null;
        try {
            c1 = (Class<? extends Set<String>>) Class.forName("java.util.HashSet");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Constructor<? extends Set<String>> constructor = null;
        try {
            constructor = c1.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("c1 doesn't have NoArgConstructor");
        }

        Set<String> set = null;
        try {
            set = constructor.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Can not make Instance");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("can not access NoArgConstructor");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Constructor invoke Exception");
        }

        //when
        set.add("1");

        //then
        assertThat(set.contains("1")).isTrue();
    }
}
