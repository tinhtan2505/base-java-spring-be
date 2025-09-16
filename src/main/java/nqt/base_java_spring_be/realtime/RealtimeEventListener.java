package nqt.base_java_spring_be.realtime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealtimeEventListener {

    private final RealtimeRegistry registry;
    private final SimpMessagingTemplate messaging;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public <T> void onDomainEvent(RealtimeDomainEvent<T> de) {
        var reg = registry.resolve(de.entity());
        var resource = reg.resource();
        var id = reg.idOf(de.entity());
        var actor = de.actor();

        var event = new RealtimeEvent<>(
                de.action(),
                resource,
                id,
                actor,
                Instant.now(),
                de.entity() // hoặc map sang DTO nhẹ hơn nếu muốn
        );

        // 1) broadcast cho tất cả: /topic/{resource}
        messaging.convertAndSend("/topic/" + resource, event);

        // 2) gửi 1-1 nếu có user targets
        for (var user : reg.usersOf(de.entity())) {
            if (user != null && !user.isBlank()) {
                messaging.convertAndSendToUser(user, "/queue/" + resource, event);
            }
        }

        log.debug("Realtime event sent: {}/{} action={} actor={}", resource, id, de.action(), actor);
    }
}