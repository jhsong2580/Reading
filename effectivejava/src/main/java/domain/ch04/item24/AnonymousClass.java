package domain.ch04.item24;

import domain.ch04.item23.Circle;
import domain.ch04.item23.FigureGoodCase;

public class AnonymousClass {
    // 방법 1. 필드에 익명 자식 객체를 생성
    private Circle colorCircle = new Circle(3) {
        String color = "red";

        String getColor() {
            return this.color;
        }

        @Override
        public double area() {
            return 9999;
        }
    };

    public Circle getFieldAnonymous() {
        return this.colorCircle;
    }

    // 방뻡2. 메서드의 로컬 변수 값으로 생성. 테스트를 위해서 hashCode값을 반환
    public Circle getLocalAnonymous() {
        Circle colorCircle = new Circle(3) {
            String color = "yellow";

            String getColor() {
                return this.color;
            }

            @Override
            public double area() {
                return 9998;
            }
        };

        return colorCircle;
    }


}
