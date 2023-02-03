package domain.ch04.item23;

public class Rectangle implements FigureGoodCase {
    double width;
    double length;

    public Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
    }

    @Override
    public double area() {
        return length * width;
    }
}
