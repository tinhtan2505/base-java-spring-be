package nqt.base_java_spring_be.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker  // Kích hoạt WebSocket server với STOMP
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Đọc các cấu hình từ application.properties
    @Value("${spring.rabbitmq.host}")   private String rabbitHost;
    @Value("${spring.rabbitmq.username}") private String rabbitUser;
    @Value("${spring.rabbitmq.password}") private String rabbitPass;
    @Value("${rabbitmq.stomp.port}")   private Integer stompPort;
    @Value("${app.websocket.endpoint}") private String websocketEndpoint;
    @Value("${app.websocket.application-destination-prefix}") private String appDestinationPrefix;
    @Value("${app.websocket.broker-prefixes}") private String brokerPrefixes;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Khai báo endpoint WebSocket cho client kết nối
        registry.addEndpoint(websocketEndpoint)
                .setAllowedOriginPatterns("*")   // Cho phép mọi nguồn (có thể cấu hình domain cụ thể)
                .withSockJS();                  // Kích hoạt SockJS fallback (hỗ trợ các trình duyệt không hỗ trợ WebSocket)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Định nghĩa prefix cho các message từ client gửi vào (route tới controller)
        registry.setApplicationDestinationPrefixes(appDestinationPrefix);
        // Kích hoạt Stomp broker relay để chuyển tiếp tới RabbitMQ các destination ngoại vi (topic/queue)
        // Tách chuỗi các broker prefixes cấu hình (vd "/topic,/queue")
        String[] relayPrefixes = brokerPrefixes.split(",");
        registry.enableStompBrokerRelay(relayPrefixes)
                .setRelayHost(rabbitHost)
                .setRelayPort(stompPort)
                .setClientLogin(rabbitUser)
                .setClientPasscode(rabbitPass)
                .setSystemLogin(rabbitUser)
                .setSystemPasscode(rabbitPass);
    }
}
