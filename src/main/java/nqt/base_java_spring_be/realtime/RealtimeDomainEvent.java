package nqt.base_java_spring_be.realtime;

public record RealtimeDomainEvent<T>(
        RealtimeAction action,
        T entity,
        String actor
) {}
