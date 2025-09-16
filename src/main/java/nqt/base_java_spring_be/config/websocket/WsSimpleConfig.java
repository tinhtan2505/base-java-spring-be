package nqt.base_java_spring_be.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;

@Configuration
@ConditionalOnProperty(name = "app.ws.useRelay", havingValue = "false", matchIfMissing = true)
public class WsSimpleConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${app.websocket.application-destination-prefix:/app}")
    private String appDestinationPrefix;

    @Value("${app.websocket.broker-prefixes:/topic,/queue}")
    private String brokerPrefixes;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        String[] prefixes = Arrays.stream(brokerPrefixes.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).toArray(String[]::new);

        registry.enableSimpleBroker(prefixes);
        registry.setApplicationDestinationPrefixes(appDestinationPrefix);
        registry.setUserDestinationPrefix("/user");
    }
}
