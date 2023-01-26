package domain.ch02.finalizer_cleaner;

import java.lang.ref.Cleaner;

public class Resource implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    private static class State implements Runnable {
        int myResource;

        public State(int myResource) {
            this.myResource = myResource;
        }

        @Override
        public void run() {
            System.out.println("cleaning");
            myResource = 0;
        }
    }

    private final State state;

    private final Cleaner.Cleanable cleanable;

    public Resource(int myResource) {
        state = new State(myResource);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean(); // state의 rud() 을 실행
    }
}
