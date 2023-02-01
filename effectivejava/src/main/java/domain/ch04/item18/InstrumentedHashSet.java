package domain.ch04.item18;

import java.util.Collection;
import java.util.HashSet;

/**
  * 잘못된 상속
*/
public class InstrumentedHashSet<E> extends HashSet<E> {
    //추가된 원소의 수
    private int addCount = 0;
    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
