package domain.ch05.item28;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ChooserWithArray<T> {
    private final T[] choiceArray;

    /**
      * version 1
         * T 가 무슨 타입인지 알 수 없어 컴파일러는 이 형변환이 런타임에도 안전한지 보장할수 없다는 의미이다.
    */
    public ChooserWithArray(Collection<T> choices) {
        this.choiceArray = (T[]) choices.toArray();
    }

    public T choose() {
        Random rnd = ThreadLocalRandom.current();
        int index = rnd.nextInt(choiceArray.length);

        return choiceArray[index];
    }
}
