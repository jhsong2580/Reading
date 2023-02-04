package domain.ch04.item24;

import domain.ch04.item23.FigureGoodCase;

public class PrivateStaticMemberClass {
    /**
      * 바깥 클래스가 표현하는 객체의 모습을 멤버 클래스로 구현을 하였다.
     * 멤버 클래스에서 바깥 클래스를 참조하는 일이 없으니, 정적 멤버 클래스로 구현을 하여야 메모리를 효율적으로 사용할수 있다.
    */
    public TestShape getRectangle(double width, double height) {
        return new TestRectangle(width, height);
    }

    public TestShape getCircle(double radius) {
        return new TestCircle(radius);
    }

    public static interface TestShape {
        double area();
    }

    private static class TestRectangle implements TestShape {

        double width;
        double length;

        public TestRectangle(double width, double length) {
            this.width = width;
            this.length = length;
        }

        @Override
        public double area() {
            return length * width;
        }
    }

    private static class TestCircle implements TestShape {

        private double radius;

        public TestCircle(double radius) {
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * (radius * radius);
        }
    }
}
