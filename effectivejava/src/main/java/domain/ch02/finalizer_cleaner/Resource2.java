package domain.ch02.finalizer_cleaner;

public class Resource2 implements AutoCloseable {

    public void doSomeThing(){};

    @Override
    public void close(){
        System.out.println("RESOURCE2_CLOSE");
    }
}
