package domain.ch05.item26;

import com.fasterxml.jackson.core.type.TypeReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClassLiteralSource {

    public ClassLiteralMap getUnSafeMap() {
        return new ClassLiteralMap();
    }

    public TypeReferenceMap getTypeReferenceMap() {
        return new TypeReferenceMap();
    }


    public static class ClassLiteralMap {
        private Map<Class<?>, Object> map = new HashMap<>();

        public <T> void put (Class<T> key, T value) {
            map.put(key, value);
        }

        public <T> T get(Class<T> key) {
            return key.cast(map.get(key));
        }
    }

    public static class TypeReferenceMap {

        Map<Type, Object> map = new HashMap<>();

        public <T> void put (TypeReference<T> key, T value) {
            map.put(key.getType(), value);
        }

        public <T> T get(TypeReference<T> key) {
            if (key.getType() instanceof ParameterizedType) {
                return ((Class<T>) ((ParameterizedType) key.getType())
                    .getRawType())
                    .cast(map.get(key.getType()));
            }
            return ((Class<T>) key.getType())
                .cast(map.get(
                    key.getType()
                ));
        }
    }
}
