package domain.ch03.item11;

import java.util.Objects;

public class PhoneNumberYesHashCode {

    private String prefix;
    private String middle;
    private String suffix;

    private int hashCode = -1;
    public PhoneNumberYesHashCode(String prefix, String middle, String suffix) {
        this.prefix = prefix;
        this.middle = middle;
        this.suffix = suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneNumberYesHashCode)) {
            return false;
        }
        PhoneNumberYesHashCode that = (PhoneNumberYesHashCode) o;
        return Objects.equals(prefix, that.prefix) && Objects.equals(middle,
            that.middle) && Objects.equals(suffix, that.suffix);
    }

    @Override
    public int hashCode() {
        return normalHashCode();
    }

    private int normalHashCode() { // hash를 위해 배열을 만들고, 박싱과 언박싱이 필요한 타입이라면 추가 작업이 있어 속도가 느리다.
        return Objects.hash(prefix, middle, suffix);
    }

    private int customhashCode() { // 타입에 대해 추가 작업이 없고, 배열에 대한 오버헤드가 필요 없어 속도가 빠르다.
        int result = prefix.hashCode();
        result = 31 * result + middle.hashCode();
        result = 31 * result + suffix.hashCode();
        return result;
    }

    private int cacheHashCode () { // 해시를 계산하는 비용이 크다면 이렇게 캐싱을 할수도 있다.
        if(hashCode == -1){
            hashCode = customhashCode();
        }
        return hashCode;
    }


    /**
      * 전화번호에 대한 문자열 반환
    */
    @Override
    public String toString() {
        return String.format("%03d-%03d-%04d", prefix, middle, suffix);
    }
}
