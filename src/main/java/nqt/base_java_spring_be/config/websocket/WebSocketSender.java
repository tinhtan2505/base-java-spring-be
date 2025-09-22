package nqt.base_java_spring_be.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketSender {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Gửi message chung đến 1 topic/queue
     */
    public void send(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }

    /**
     * Gửi message đến 1 user cụ thể (Spring sẽ map /user/{userId}/queue/...)
     */
    public void sendToUser(String userId, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(userId, destination, payload);
    }
}
