package domain.ch04.item20;

public abstract class Parent implements GreateParent{

    abstract void overrideMe();

    void useIt() {
        System.out.println("hello user");
    }

    /**
      * @implSpec
     * 템플릿메서드로 Parent 사용시 overrideThisIfYouWantUse 를 override하지 않으면 사용하지 마세요
    */

    @Override
    public void overrideThisIfYouWantUse() {
        throw new UnsupportedOperationException();
    }
}
