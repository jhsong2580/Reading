package domain.ch04.item24;

public class MemberClass {

    private final InnerClass innerClass;
    public MemberClass() {
        innerClass = new InnerClass();
    }

    public int doSomethingInTopClass() {
        return innerClass.doSomethingInInnerClass();
    }

    private int return3() {
        return 3;
    }

    private class InnerClass {
        int doSomethingInInnerClass() {
            return MemberClass.this.return3(); // 정적 멤버 클래스는 정규화된 this(클래스명.this)로 바깥 클래스 함수를 접근할 수 있다.
        }
    }
}
