package study.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import study.decorator.HelloDecorator;

public class HelloServiceTest {

    @Test
    public void HelloDecoratorTest (){
        //given
        HelloDecorator helloDecorator = new HelloDecorator(name -> name);

        //when
        String result = helloDecorator.sayHello("test");

        //then
        assertThat(result).isEqualTo("*test*");
    }

}
