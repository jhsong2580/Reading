package domain.ch02.finalizer_cleaner;

public class Resource1 implements AutoCloseable {

    public void doSomeThing(){};

    @Override
    public void close() {
        System.out.println("RESOURCE1_CLOSE");
        throw new RuntimeException("RESOURCE1_EXCEPTION");
    }
}
