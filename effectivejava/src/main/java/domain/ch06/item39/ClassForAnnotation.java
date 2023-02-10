package domain.ch06.item39;

public class ClassForAnnotation {

    @CustomAnnotation(value = RuntimeException.class)
    public void testMethod() {

    }

    @CustomAnnotation(value = RuntimeException.class)
    @CustomAnnotation(value = Throwable.class)
    @CustomAnnotation(value = Exception.class)
    public void repeatableCustomAnnotationMethod() {

    }
}
