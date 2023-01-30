package ch03;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.ch03.ColorPoint;
import domain.ch03.DegreePoint;
import domain.ch03.Point;
import domain.ch03.item11.PhoneNumberNoHashCode;
import domain.ch03.item11.PhoneNumberYesHashCode;
import domain.ch03.item13.PointStack;
import domain.ch03.item13.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Example {

    @Test
    public void equals는_반사성을_지켜야한다() {
        // 반사성 : x.equals(x)는 항상 true이다
        //given
        ColorPoint colorPoint1 = new ColorPoint(0, 1, "red");

        assertThat(colorPoint1).isEqualTo(colorPoint1);
    }

    @Test
    public void equals는_대칭성을_지켜야한다() {
        //대칭성 : x.equals(y) == y.equals(x)
        //given
        ColorPoint colorPoint = new ColorPoint(0, 1, "red");
        DegreePoint degreePoint = new DegreePoint(0, 1, 90);

        assertAll(
            () -> assertThat(colorPoint).isEqualTo(degreePoint),
            () -> assertThat(degreePoint).isEqualTo(colorPoint)
        );
    }

    @Test
    public void equals는_추이성을_지켜야한다() {
        //x.equals(y) == y.equals(z) == z.equals(x)
        //given
        Point point = new Point(0, 1);
        ColorPoint colorPoint = new ColorPoint(0, 1, "red");
        DegreePoint degreePoint = new DegreePoint(0, 1, 90);

        //when
        assertAll(
            () -> assertThat(colorPoint).isEqualTo(degreePoint),
            () -> assertThat(degreePoint).isEqualTo(point),
            () -> assertThat(point).isEqualTo(colorPoint)
        );
    }

    @Test
    public void equals는_일관성을_지켜야한다() {
        //무한대를 진행해도 결과는 같다
        //given
        ColorPoint colorPoint = new ColorPoint(0, 1, "red");
        DegreePoint degreePoint = new DegreePoint(0, 1, 90);
        boolean result = true;

        //when
        for(int i = 0; i< 100; i++){
            result = colorPoint.equals(degreePoint);
        }

        assertThat(result).isTrue();
    }

    @Test
    public void equals는_null과비교하면_항상false이다 (){
        //given
        Point point = new Point(0, 1);
        ColorPoint colorPoint = new ColorPoint(0, 1, "red");
        DegreePoint degreePoint = new DegreePoint(0, 1, 90);

        //when
        assertAll(
            () -> assertThat(colorPoint.equals(null)).isFalse(),
            () -> assertThat(degreePoint.equals(null)).isFalse(),
            () -> assertThat(point.equals(null)).isFalse()
        );
    }

    @Test
    public void Collection내부_값비교는_equals가한다 (){
        //given
        Set<Point> pointSet = new HashSet<>();
        ColorPoint colorPoint = new ColorPoint(0, 1, "yellow");
        DegreePoint degreePoint = new DegreePoint(0, 1, 60);

        //when
        pointSet.add(colorPoint);
        pointSet.add(degreePoint);

        //then
        assertThat(pointSet)
            .hasSize(1)
            .contains(colorPoint, degreePoint) // 실제 degreePoint는 없으나, equals 연산으론 같기 때문에 포함된닫고 나온다.
            .extracting("color").containsExactly("yellow"); // 각 entity들의 getColor()를 통해 얻어온 값이 yellow이다.
    }

    @Test
    public void hashCode선언하지않으면_hashCollection이_정상적으로_동작하지않는다 (){
        //given
        Map<PhoneNumberNoHashCode, String> 전화번호부 = new HashMap<>();
        PhoneNumberNoHashCode 제니전화번호 = new PhoneNumberNoHashCode("707", "867", "5309");
        전화번호부.put(제니전화번호, "제니");

        //when
        String expectFalse = 전화번호부.get(new PhoneNumberNoHashCode("707", "867", "5309"));
        String expectTrue = 전화번호부.get(제니전화번호);

        //then
        assertAll(
            () -> assertThat(expectFalse).isNotEqualTo("제니"),
            () -> assertThat(expectTrue).isEqualTo("제니")
        );
    }

    @Test
    public void hashCode선언하면_hashCollection이_정상적으로_동작한다 (){
        //given
        Map<PhoneNumberYesHashCode, String> 전화번호부 = new HashMap<>();
        PhoneNumberYesHashCode 제니전화번호 = new PhoneNumberYesHashCode("707", "867", "5309");
        전화번호부.put(제니전화번호, "제니");

        //when
        String expectFalse = 전화번호부.get(new PhoneNumberYesHashCode("707", "867", "5309"));
        String expectTrue = 전화번호부.get(제니전화번호);

        //then
        assertAll(
            () -> assertThat(expectFalse).isEqualTo("제니"),
            () -> assertThat(expectTrue).isEqualTo("제니")
        );
    }

    @Test
    public void SUPER_CLONE_사용시문제점 () throws CloneNotSupportedException {
        // stack과 clonedStack은 각기 다른 인스턴스지만 내부의 Array는 얕은복사가 이루어져 데이터가 공유된다
        //given
        Stack stack = new Stack();
        Stack clonedStack = stack.clone();

        //when
        stack.push(3);

        //then
        assertAll(
            () -> assertThat(stack).isNotEqualTo(clonedStack),
            () -> assertThat(stack.getElements().hashCode()).isEqualTo(clonedStack.getElements().hashCode()),
            () -> assertThat(stack.getElements().length).isEqualTo(clonedStack.getElements().length)
        );
    }

    @Test
    public void CLONE_메서드_재정의시_얕은복사를_피해간다 () throws CloneNotSupportedException {
        //given
        PointStack pointStack = new PointStack();
        PointStack clonedPointStack = pointStack.clone();

        //when
        pointStack.push(new Point(3, 2));

        //then
        assertAll(
            () -> assertThat(pointStack).isNotEqualTo(clonedPointStack),
            () -> assertThat(pointStack.getElements().hashCode()).isNotEqualTo(clonedPointStack.getElements().hashCode()),
            () -> assertThat(pointStack.getElements()[0]).isNotNull(), // pointStack에는 데이터가 들어가있고
            () -> assertThat(clonedPointStack.getElements()[0]).isNull() //cloned된 결과에는 데이터가 들어가 있지 않다!(깊은복사)
        );
    }
}
