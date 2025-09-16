package nqt.base_java_spring_be.realtime;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class RealtimeRegistry {

    public static final class Registration<T> {
        private final String resource;                       // vd "projects"
        private final Function<T, String> idFn;              // lấy id -> String
        private final Function<T, Collection<String>> usersFn; // user đích (1-1), có thể rỗng

        public Registration(String resource,
                            Function<T, String> idFn,
                            Function<T, Collection<String>> usersFn) {
            this.resource = resource;
            this.idFn = idFn;
            this.usersFn = usersFn != null ? usersFn : t -> List.of();
        }

        public String resource() { return resource; }
        public String idOf(T entity) { return idFn.apply(entity); }
        public Collection<String> usersOf(T entity) { return usersFn.apply(entity); }
    }

    private final Map<Class<?>, Registration<?>> map = new ConcurrentHashMap<>();

    public <T> RealtimeRegistry register(Class<T> type, Registration<T> reg) {
        map.put(type, reg);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Registration<T> get(Class<T> type) {
        Registration<?> reg = map.get(type);
        if (reg == null) throw new IllegalStateException("No realtime registration for " + type.getName());
        return (Registration<T>) reg;
    }

    public Registration<Object> resolve(Object entity) {
        return get((Class<Object>) entity.getClass());
    }
}
