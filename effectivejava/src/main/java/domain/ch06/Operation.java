package domain.ch06;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Operation {


    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String name;

    Operation(String name) {
        this.name = name;
    }
    private static final Map<String, Operation> stringToEnum = Stream.of(values())
        .collect(
            toMap(Object::toString, e -> e)
        );
    public abstract double apply(double x, double y);

    @Override
    public String toString() {
        return this.name;
    }

    public static Optional<Operation> fromString(String symbol) {

        return Optional.ofNullable(stringToEnum.get(symbol));
    }
}
