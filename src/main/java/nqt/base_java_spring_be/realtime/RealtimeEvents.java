package nqt.base_java_spring_be.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealtimeEvents {

    private final ApplicationEventPublisher publisher;

    public <T> void emitCreated(T entity, String actor) {
        publisher.publishEvent(new RealtimeDomainEvent<>(RealtimeAction.CREATED, entity, actor));
    }
    public <T> void emitUpdated(T entity, String actor) {
        publisher.publishEvent(new RealtimeDomainEvent<>(RealtimeAction.UPDATED, entity, actor));
    }
    public <T> void emitDeleted(T entity, String actor) {
        publisher.publishEvent(new RealtimeDomainEvent<>(RealtimeAction.DELETED, entity, actor));
    }
}
