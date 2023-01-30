package domain.ch03.item13;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack implements Cloneable{
    private Object[] elements;
    private int size;
    private static final int DEAFULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEAFULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object element = elements[--size];
        elements[size] = null; //할당해제
        return element;
    }

    private void ensureCapacity() {
        if(elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    @Override
    public Stack clone() throws CloneNotSupportedException {
        return (Stack)super.clone();
    }

    public Object[] getElements() {
        return elements;
    }
}
