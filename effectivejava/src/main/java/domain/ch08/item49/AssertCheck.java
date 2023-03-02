package domain.ch08.item49;

public class AssertCheck {

    /**
     * @param long 배열 a
     * @param offset, length >= 0
     * @throws AssertionError
    */
    public void example(long a[], int offset, int length) {
        assert a != null;
        assert offset >= 0;
        assert length >= 0;
    }

}
