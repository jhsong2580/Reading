package study.format;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyEvent extends BaseEvent {

    private String field1;
    private int field2;
    private MySubEvent mySub;

    public MyEvent(String field1, int field2, MySubEvent mySub) {
        this.field1 = field1;
        this.field2 = field2;
        this.mySub = mySub;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
            "field1='" + field1 + '\'' +
            ", field2=" + field2 +
            ", mySub=" + mySub +
            '}';
    }
}
