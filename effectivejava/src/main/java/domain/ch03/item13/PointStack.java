package domain.ch03.item13;

import domain.ch03.Point;

public class PointStack implements Cloneable {

    private Point[] elements;
    private int size = 0;

    public PointStack() {
        elements = new Point[16];
    }

    public void push(Point element) {
        elements[size] = element;
        size++;
    }

    public Point[] getElements() {
        return elements;
    }

    private void setElements(Point[] elements) {
        this.elements = elements;
    }

    @Override
    public PointStack clone() throws CloneNotSupportedException {
        PointStack clonePointStack = (PointStack) super.clone();
        clonePointStack.setElements(this.getElements().clone());

        return clonePointStack;
    }
}
