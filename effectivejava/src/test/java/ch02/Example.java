package ch02;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch02.Human;
import domain.ch02.NyPizza;
import domain.ch02.NyPizza.Builder;
import domain.ch02.NyPizza.Size;
import domain.ch02.Pizza;
import domain.ch02.Pizza.Topping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Example {

    @DisplayName("static factory method는 반환 타입의 하위 타입 객체를 반환할수 있다")
    @ParameterizedTest
    @CsvSource(value = {"SJH,SJH", "YSA,YSA"}, delimiter = ',')
    public void example1(String name, String expectName) {
        //given
        Human human = Human.from(name);

        //when
        String realName = human.getName();

        //then
        assertThat(realName).isEqualTo(expectName);
    }

    @DisplayName("Builder패턴도 상속 관계에서 사용이 가능하다")
    @Test
    public void example2() {
        //given
        Pizza pizza = new Builder(Size.LARGE)
            .addTopping(Topping.MUSHROOM).addTopping(Topping.SAUSAGE)
            .build();

        //when & then
        assertAll(
            () -> assertThat(pizza.getToppings())
                .contains(Topping.MUSHROOM, Topping.SAUSAGE)
                .doesNotContain(Topping.NAM),
            () -> assertThat(pizza instanceof NyPizza).isTrue(),
            () -> assertThat(((NyPizza) pizza).getSize()).isEqualTo(Size.LARGE)
        );
    }
}
