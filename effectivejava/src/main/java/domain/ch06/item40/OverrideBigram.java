package domain.ch06.item40;



public class OverrideBigram {
    private final char first;
    private final char second;

    public OverrideBigram(char first, char second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
      * 잘못된 재정의에 대해서 Override 애너테이션이 컴파일 에러를 발생시키는 예다.
        * 기존 테스트코드들을 사용할수 없어 주석으로 해놓는다.
    */
//    @Override
//    public boolean equals(OverrideBigram obj) {
//        return this.first == obj.first && this.second == obj.second;
//    }
}
