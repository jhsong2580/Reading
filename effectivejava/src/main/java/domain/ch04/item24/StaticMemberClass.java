package domain.ch04.item24;

import domain.ch04.item24.StaticMemberClass.Helper.flag;

public class StaticMemberClass {

    public void doSomething(String flag) {
        if(Helper.flag.valueOf(flag) == Helper.flag.T1){ // inner class의 정보를 톱 클래스에서 사용한다.
            //do someTHing
        }
    }


    static class Helper {
        enum flag {T1, T2}
    }

}
