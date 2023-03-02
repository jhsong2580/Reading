package domain.ch08.item50;

import java.util.Date;
import java.util.Objects;

public class Period {
    private final Date start;
    private final Date end;

    private Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @param start 시작시간
     * @param end 종료시간
     * @throws NullPointerException start 나 end가 null이면 발생한다
     * @throws IllegalArgumentException start가 end보다 늦을 때 발생한다
    */
    public static Period of(Date start, Date end) {
        start = Objects.requireNonNull(start);
        end = Objects.requireNonNull(end);

        if(start.compareTo(end) > 0) {
            throw new IllegalArgumentException();
        }

        Period period = new Period(start, end);

        return period;
    }

    /**
     * @param start 시작시간
     * @param end 종료시간
     * @throws NullPointerException start 나 end가 null이면 발생한다
     * @throws IllegalArgumentException start가 end보다 늦을 때 발생한다
     */
    public static Period defenceCopyOf(Date start, Date end) {
        start = Objects.requireNonNull(start);
        end = Objects.requireNonNull(end);

        if(start.compareTo(end) > 0) {
            throw new IllegalArgumentException();
        }

        Period period = new Period(new Date(start.getTime()), new Date(end.getTime()));

        return period;
    }

    public boolean checkDateValid() {
        return start.compareTo(end) > 0;
    }
}
