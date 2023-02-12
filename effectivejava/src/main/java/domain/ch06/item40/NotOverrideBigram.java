package domain.ch06.item40;

public class NotOverrideBigram {

    private final char first;
    private final char second;

    public NotOverrideBigram(char first, char second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(NotOverrideBigram obj) {
        return this.first == obj.first && this.second == obj.second;
    }
}
