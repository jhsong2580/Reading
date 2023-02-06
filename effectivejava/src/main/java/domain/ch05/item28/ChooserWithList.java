package domain.ch05.item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ChooserWithList<T> {

    private final List<T> choiceList;

    public ChooserWithList(Collection<T> choices) {
        choiceList = new ArrayList<>(choices);
    }

    public T choose() {
        Random rnd = ThreadLocalRandom.current();
        int index = rnd.nextInt(choiceList.size());

        return choiceList.get(index);
    }
}
