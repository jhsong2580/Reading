package domain.ch05.item31;

public abstract class Parent implements Comparable<Parent> {

    private int size;



    public Parent(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(Parent o) {
        return Integer.compare(this.size, o.size);
    }
}
