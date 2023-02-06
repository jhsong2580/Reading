package domain.ch05.item33;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.ParameterizedTypeReference;

public class Factories {

    private Map<Class<?>, Object> favorites = new HashMap<>();
    /**
      * Object로 받은 이유는, value를 받을때 런타임 내에서는 제네릭이 사라지고 Object만 남기 때문이다.  
     * 런타임 시에 T와 Object간 타입 체크는 따로 하지 않는다. (컴파일때 하기 때문에) 
    */
    
    
    public <T> void putFavorite(Class<T> key, T value) {
        favorites.put(Objects.requireNonNull(key), value);
    }

    public <T> T getFavorite(Class<T> key) {
        /**
          * 런타임 시에 value가 key 타입의 인스턴스라는 정보는 사라지지만, cast를 통해 다시 타입설정을 해준다.
        */
         return key.cast(favorites.get(Objects.requireNonNull(key)));

//        return (T) favorites.get(Objects.requireNonNull(key));
////        타입 미검사 경고 발생!!
////        Note: /Users/songjeunghun/Desktop/myfile/kyhSpringbootStudy/study/effectivejava/src/main/java/domain/ch05/item33/Factories.java uses unchecked or unsafe operations.
////            Note: Recompile with -Xlint:unchecked for details.

    }
}
