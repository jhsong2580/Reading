package domain.ch02;

public interface Human {

    static Human from(String name) {
        if("SJH".equals(name)){
            return new JeungHoon();
        }else if("YSA".equals(name)){
            return new SeolAh();
        }

        throw new IllegalArgumentException();
    }

    String getName();
}
