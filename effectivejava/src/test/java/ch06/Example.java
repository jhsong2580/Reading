package ch06;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch06.Operation;
import domain.ch06.item37.Phase;
import domain.ch06.item37.Phase.Transition_GOODCASE;
import domain.ch06.item38.BasicOperation;
import domain.ch06.item38.ExtendedOperation;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void EnumAbstractFunctionTest() {
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
    public void check2DimensionEnums() {
        Transition_GOODCASE deposit = Transition_GOODCASE.getStatus(Phase.GAS, Phase.SOLID);
        Transition_GOODCASE deposit1 = Transition_GOODCASE.getStatus(Phase.LIQUID, Phase.SOLID);

        assertAll(
            () -> assertThat(deposit).isEqualTo(Transition_GOODCASE.DEPOSIT),
            () -> assertThat(deposit1).isEqualTo(Transition_GOODCASE.DEPOSIT1)
        );
        //then
    }

    @Test
    public void OperationEnumCheck() {
        //given
        execute(BasicOperation.class, 3, 1);
        execute(ExtendedOperation.class, 3, 1);
        //when

        //then
    }

    private <T extends Enum<T> & domain.ch06.item38.Operation> void execute(
        Class<T> opEnumType, double x, double y) {
        for (domain.ch06.item38.Operation operation : opEnumType.getEnumConstants()) {
            System.out.println(operation.name() + " " + operation.apply(x, y));
        }
    }

}
