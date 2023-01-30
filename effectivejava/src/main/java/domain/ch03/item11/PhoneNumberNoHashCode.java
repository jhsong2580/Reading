package domain.ch03.item11;

public class PhoneNumberNoHashCode {
    private String prefix;
    private String middle;
    private String suffix;

    public PhoneNumberNoHashCode(String prefix, String middle, String suffix) {
        this.prefix = prefix;
        this.middle = middle;
        this.suffix = suffix;
    }
}
