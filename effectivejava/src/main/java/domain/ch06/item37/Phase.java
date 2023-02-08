package domain.ch06.item37;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Phase {
    SOLID, LIQUID, GAS;

    /**
      * 아누 안좋은 예다. Phase나 Transition을 수정할 경우, TRNASITIONS를 수정하지 않으면 에러가 발생할수 있고, 2차원 배열의 크기가 배로 넓어진다. (메모리 비효율)
    */
    public enum Transition_BADCASE {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

        private static final Transition_BADCASE[][] TRANSITION_BADCASES = {
            {null, MELT, SUBLIME},
            {FREEZE, null, BOIL},
            {DEPOSIT, CONDENSE, null}
        }; // 각 phase별 동작 순서

        public static Transition_BADCASE from(Phase from, Phase to) {
            return TRANSITION_BADCASES[from.ordinal()][to.ordinal()];
        }
    }

    @Getter
    public enum Transition_GOODCASE {
        MELT(SOLID, LIQUID),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),
        DEPOSIT(GAS, SOLID),
        DEPOSIT1(LIQUID, SOLID);
        private final Phase from;
        private final Phase to;

        Transition_GOODCASE(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        public Phase getFrom() {
            return from;
        }

        public static final Map<Phase, Map<Phase, Transition_GOODCASE>> TRANSITION_MAP =
            Stream.of(values())
                .collect(
                    groupingBy(t -> t.from,                 // t.from으로 group by 를 하여
                        () -> new EnumMap<>(Phase.class),   // Enum Map으로 묶는다. <Phase, Map>
                        toMap(
                            t -> t.to,                      // 묶인 애들에 대해서, key값은 Transition_GOODCASE.to
                            t -> t,                         // value는 Transition_GOODCASE로 한다.
                            (x,y) -> y,                     // 만약 같은 t.from에 대해서, 같은 t.to가 존재한다면 뒤에 Transition_GOODCASE를 value로 한다.
                            () -> new EnumMap<>(Phase.class) // 그리고 이 Map을 EnumMap으로 생성한다. Key -> Phase, Value -> Transition_GOODCASE
                    ))
                );
            /**
              * merge에 대해서 위의 예로 들어보자면, 먼저
             * 1. t.from에 대해서 group by를 진행한다.
                 * t.from이 LIQUID일때를 알아보자.
             * 2. t.to를 key로 한 Entry를 Value에 넣는다. 아래 3개이므로, SOLID라는 key에 대해서 2개, GAS란 KEY에 대해서 1개가 발생된다.
                 * FREEZE(LIQUID, SOLID)
                 * BOIL(LIQUID, GAS)
                 * DEPOSIT1(LIQUID, SOLID)
             * 3. GAS라는 Key는 1개밖에 없으므로 바로 t(BOIL)를 Value로 넣는다.
                * <LIQUID , <GAS, BOIL>>
             * 4. SOLID라는 Key로는 FREEZE, DEPOSIT1 이라는 2개의 Value가 발생된다.
                 * merge 전략에 의해 (x,y) -> y이므로 ordinal 뒤 순번인 DEPOSIT1이 Value로 선정된다.
                 * <LIQUID, <SOLID, DEPOSIT1>>
            */
        public static Transition_GOODCASE getStatus(Phase from, Phase to) {
            return TRANSITION_MAP.get(from).get(to);
        }
    }
}
