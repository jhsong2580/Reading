package ch02;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch02.Human;
import domain.ch02.NyPizza;
import domain.ch02.NyPizza.Builder;
import domain.ch02.NyPizza.Size;
import domain.ch02.Pizza;
import domain.ch02.Pizza.Topping;
import domain.ch02.finalizer_cleaner.MyTester;
import domain.ch02.finalizer_cleaner.Resource1;
import domain.ch02.finalizer_cleaner.Resource2;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class Example {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

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

    @DisplayName("자원에 대한 반환은 try-catch-resource로 진행하자")
    @Test
    public void example3() {
        final String CLEANING_EXECUTED = "cleaning";
        final String CREATE_EXECUTED = "execute";

        //given
        MyTester myTester = new MyTester();

        //when
        myTester.executeTCR();

        //then
        Assertions.assertAll(
            () -> assertThat(standardOutputContainString(CREATE_EXECUTED)).isTrue(),
            () -> assertThat(standardOutputContainString(CLEANING_EXECUTED)).isTrue()
        );
    }

    @DisplayName("자원에 대한 반환은 Cleaning을 사용하면 정상적으로 동작하지 않을수 있다. - 가끔 테스트가 실패할수 있다")
    @Test
    public void example4() {
        final String CLEANING_EXECUTED = "cleaning";
        final String CREATE_EXECUTED = "execute";

        //given
        MyTester myTester = new MyTester();

        //when
        myTester.executeTCR();

        //then
        Assertions.assertAll(
            () -> assertThat(standardOutputContainString(CREATE_EXECUTED)).isTrue(),
            () -> assertThat(standardOutputContainString(CLEANING_EXECUTED)).isFalse()
        );
    }

    @DisplayName("finally 내에서 자원 반환을 할시 정상 동작을 안할수 있다.")
    @Test
    public void example5 () throws RuntimeException{
        final String RESOURCE1_CLOSE = "RESOURCE1_CLOSE";
        final String RESOURCE2_CLOSE = "RESOURCE2_CLOSE";
        final String RESOURCE1_EXCEPTION = "RESOURCE1_EXCEPTION";
        //given
        Resource1 resource1 = new Resource1();
        Resource2 resource2 = new Resource2();
        //when
        try {
            resource1.doSomeThing();
            resource2.doSomeThing();
        }catch (Exception e){

        }finally {
            assertAll(
                () -> assertThatThrownBy(() -> resource1.close()).isInstanceOf(RuntimeException.class)
                    .hasMessage(RESOURCE1_EXCEPTION),
                () -> assertThat(standardOutputContainString(RESOURCE1_CLOSE)).isTrue(),
                () -> assertThat(standardOutputContainString(RESOURCE2_CLOSE)).isFalse()
            );
        }
    }

    @DisplayName("try-catch-resource는 예외가 있어도 정상동작한다")
    @Test
    public void example6 (){
        final String RESOURCE1_CLOSE = "RESOURCE1_CLOSE";
        final String RESOURCE2_CLOSE = "RESOURCE2_CLOSE";
        final String RESOURCE1_EXCEPTION = "RESOURCE1_EXCEPTION";

        //when
        try(
            Resource1 resource1 = new Resource1();
            Resource2 resource2 = new Resource2();
            ) {
            resource1.doSomeThing();
            resource2.doSomeThing();
        }catch (Exception e){

        }

        assertAll(
            () -> assertThat(standardOutputContainString(RESOURCE1_CLOSE)).isTrue(),
            () -> assertThat(standardOutputContainString(RESOURCE2_CLOSE)).isTrue()
        );
    }

    private boolean standardOutputContainString(String expected){
        return outputStreamCaptor.toString().trim().contains(expected);
    }
}

