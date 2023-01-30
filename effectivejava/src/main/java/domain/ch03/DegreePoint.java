package domain.ch03;

public class DegreePoint extends Point {
    private int degree;

    public DegreePoint(int x, int y, int degree) {
        super(x, y);
        this.degree = degree;
    }
}
