package ch06;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch06.Operation;
import domain.ch06.item37.Phase;
import domain.ch06.item37.Phase.Transition_GOODCASE;
import domain.ch06.item38.BasicOperation;
import domain.ch06.item38.ExtendedOperation;
import domain.ch06.item39.CustomAnnotation;
import domain.ch06.item39.RepeatableCustomContainer;
import domain.ch06.item40.NotOverrideBigram;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
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
            System.out.println(operation.apply(x, y));
        }
    }

    @Test
    public void MakerAnnotationTest() throws ClassNotFoundException, NoSuchMethodException {
        //given
        Class<?> testClass = Class.forName("domain.ch06.item39.ClassForAnnotation");
        Method testMethod = testClass.getDeclaredMethod("testMethod");

        //when
        CustomAnnotation annotation = testMethod.getAnnotation(CustomAnnotation.class);

        //then
        assertThat(annotation.value())
            .isEqualTo(RuntimeException.class);
    }

    @Test
    public void RepeatableMarkerAnnotationTest()
        throws ClassNotFoundException, NoSuchMethodException {
        //given
        Class<?> testClass = Class.forName("domain.ch06.item39.ClassForAnnotation");
        Method repeatableAnnotationMethod = testClass.getDeclaredMethod(
            "repeatableCustomAnnotationMethod");
        Method oneAnnotationMethod = testClass.getDeclaredMethod("testMethod");

        //when (????????? ?????? factory, Annotation ????????? ????????? ??? ??????. ( ?????? assert ?????? ???????????? )
        RepeatableCustomContainer repeatableCustomContainer = repeatableAnnotationMethod.getAnnotation(
            RepeatableCustomContainer.class);
        CustomAnnotation[] customAnnotations = repeatableAnnotationMethod.getAnnotationsByType(
            CustomAnnotation.class);

        //then
        assertAll(
            () -> assertThat(repeatableAnnotationMethod.isAnnotationPresent(
                RepeatableCustomContainer.class)).isTrue(),
            // ?????? ?????????????????? ??????????????? Container Class??? ????????????
            () -> assertThat(
                oneAnnotationMethod.isAnnotationPresent(CustomAnnotation.class)).isTrue(),
            // ????????? ?????????????????? ???????????? ????????? ?????? ??????????????? ???????????? ????????????.
            () -> assertThat(customAnnotations)
                .extracting(CustomAnnotation::value)
                .extracting(Class::getName)
                .containsExactly(
                    RuntimeException.class.getName(),
                    Throwable.class.getName(),
                    Exception.class.getName()
                ),
            () -> assertThat(repeatableCustomContainer.value())
                .extracting(CustomAnnotation::value)
                .extracting(Class::getName)
                .containsExactly(
                    RuntimeException.class.getName(),
                    Throwable.class.getName(),
                    Exception.class.getName()
                )
        );

    }

    @Test
    public void UseOverrideAnnotationToUpperClassMethod() {
        //given
        Set<NotOverrideBigram> bigrams = new HashSet<>();

        //when
        for (int i = 0; i < 10; i++) {
            for (char chr = 'a'; chr <= 'z'; chr++) {
                bigrams.add(new NotOverrideBigram(chr, chr));
            }
        }

        //then
        //// equals, hashcode??? ??????????????????, set??? ????????? ???????????????.
        assertThat(bigrams).hasSize(10 * ('z' - 'a' + 1));
    }
}
