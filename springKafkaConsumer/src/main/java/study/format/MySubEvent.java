package study.format;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MySubEvent extends BaseEvent{
    private int field3;
    private long field4;

    public MySubEvent(int field3, long field4) {
        this.field3 = field3;
        this.field4 = field4;
    }

    @Override
    public String toString() {
        return "MySubEvent{" +
            "field3=" + field3 +
            ", field4=" + field4 +
            '}';
    }
}
