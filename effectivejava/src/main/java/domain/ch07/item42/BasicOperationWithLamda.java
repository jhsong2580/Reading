package domain.ch07.item42;

import domain.ch06.item38.Operation;

public enum BasicOperationWithLamda {
    PLUS("+", (x, y) -> x+y),
    MINUS("-", (x, y) -> x - y),
    ;

    private final String symbol;
    private final Operation op;
    BasicOperationWithLamda(String symbol, Operation op) {
        this.symbol = symbol;
        this.op = op;
    }

    public double apply(double x, double y){
        return this.op.apply(x, y);
    }
}
