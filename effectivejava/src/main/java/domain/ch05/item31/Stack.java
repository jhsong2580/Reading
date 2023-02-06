package domain.ch05.item31;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Stack<E> {

    protected List<E> source;

    public Stack() {
        source = new ArrayList<E>();
    }

    public void push(E e) {
        source.add(e);
    }

    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        return source.remove(0);
    }

    public void popAll(Collection<? super E> dst) { // 제너릭 타입에 대해 이 클래스/함수가 "소비자"라면 <? super E>를, "공급자라면 <? extends E>를 사용하라
        while (!isEmpty()) {
            dst.add(pop());
        }
    }


    public boolean isEmpty() {
        return source.isEmpty();
    }

    public void pushAll(Iterator<? extends E> src) {
        src.forEachRemaining(e ->
            this.push(e));
    }
}
