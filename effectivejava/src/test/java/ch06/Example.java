package ch06;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch06.Operation;
import domain.ch06.item37.Phase;
import domain.ch06.item37.Phase.Transition_GOODCASE;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void EnumAbstractFunctionTest (){
        //given
        double x = 5;
        double y = 2;

        //when & then
        assertAll(
            () -> assertThat(Operation.PLUS.apply(x, y)).isEqualTo(7.0),
            () -> assertThat(Operation.MINUS.apply(x, y)).isEqualTo(3.0),
            () -> assertThat(Operation.TIMES.apply(x, y)).isEqualTo(10.0),
            () -> assertThat(Operation.DIVIDE.apply(x, y)).isEqualTo(2.5)
        );

        //then
    }

    @Test
    public void check2DimensionEnums (){
        Transition_GOODCASE deposit = Transition_GOODCASE.getStatus(Phase.GAS, Phase.SOLID);
        Transition_GOODCASE deposit1 = Transition_GOODCASE.getStatus(Phase.LIQUID, Phase.SOLID);

        assertAll(
            () -> assertThat(deposit).isEqualTo(Transition_GOODCASE.DEPOSIT),
            () -> assertThat(deposit1).isEqualTo(Transition_GOODCASE.DEPOSIT1)
        );
        //then
    }
}
