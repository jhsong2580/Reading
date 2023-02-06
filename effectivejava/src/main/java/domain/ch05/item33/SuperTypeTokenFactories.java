package domain.ch05.item33;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;

public class SuperTypeTokenFactories {

    private Map<ParameterizedTypeReference, Object> myData;

    public SuperTypeTokenFactories() {
        myData = new HashMap<>();
    }

    public <T> void put(ParameterizedTypeReference<T> key, T value) {
        myData.put(key, value);
    }

    public <T> T get(ParameterizedTypeReference<T> key) {
        Object data = myData.get(key);
        if (key.getType() instanceof ParameterizedType) {
            return ((Class<T>) ((ParameterizedType) key.getType())
                .getRawType())
                .cast(data);
        }
        return ((Class<T>) key.getType())
            .cast(data);
    }
}
