package nqt.base_java_spring_be.realtime;

import nqt.base_java_spring_be.entity.Project;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RealtimeRegistryConfig {

    @Bean
    public RealtimeRegistry realtimeRegistry() {
        RealtimeRegistry registry = new RealtimeRegistry();

        // Project → resource "projects"
        registry.register(Project.class, new RealtimeRegistry.Registration<>(
                "projects",
                p -> p.getId().toString(),
                p -> p.getOwner() != null && !p.getOwner().isBlank()
                        ? List.of(p.getOwner())     // gửi 1-1 cho owner
                        : List.of()                 // hoặc List.of("ketoan","admin") nếu muốn cứng
        ));

        // Thêm entity khác chỉ 1 dòng:
        // registry.register(Order.class, new RealtimeRegistry.Registration<>(
        //        "orders",
        //        o -> o.getId().toString(),
        //        o -> List.of(o.getCreatedBy())
        // ));

        return registry;
    }
}