package domain.ch02.finalizer_cleaner;

public class MyTester {

    public void execute() {
        Resource resource = new Resource(3);
        System.out.println("execute");
    }

    public void executeTCR() {
        try (Resource resource = new Resource(3)) {
            System.out.println("execute");
        } catch (Exception e) {

        }
    }
}
