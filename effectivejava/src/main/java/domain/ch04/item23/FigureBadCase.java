package domain.ch04.item23;

public class FigureBadCase {
    enum Shape { RECTANGLE, CIRCLE };
    // 태그 필드
    final Shape shape;

    // shape 이 RECTANGLE일때만 쓰인다.
    double length;
    double width;

    // shape 이 CIRCLE일때만 쓰인다.
    double radius;

    public FigureBadCase(double radius) {
        this.shape = Shape.CIRCLE;
        this.radius = radius;
    }

    public FigureBadCase(double length, double width) {
        this.shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area () {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
