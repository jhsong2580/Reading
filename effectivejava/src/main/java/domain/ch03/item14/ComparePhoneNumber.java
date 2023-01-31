package domain.ch03.item14;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComparePhoneNumber {
    private int areaCode;
    private int prefix;
    private int lineNum;

    private static final Comparator<ComparePhoneNumber> COMPARATOR =
        Comparator.comparingInt((ComparePhoneNumber pn) -> pn.getAreaCode())
            .thenComparingInt(pn -> pn.getPrefix())
            .thenComparingInt(pn -> pn.getLineNum());

    public int compareTo(ComparePhoneNumber pn) {
        return COMPARATOR.compare(this, pn);
    }

}
