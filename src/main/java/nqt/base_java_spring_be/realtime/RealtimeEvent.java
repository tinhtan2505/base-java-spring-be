package nqt.base_java_spring_be.realtime;

import java.time.Instant;

public record RealtimeEvent<T>(
        RealtimeAction action,
        String resource,     // ví dụ: "projects", "orders" (dùng ở destination)
        String id,           // id của entity (toString để linh hoạt UUID/Long)
        String actor,        // username thực hiện
        Instant at,          // thời điểm phát sinh
        T body               // payload (entity hoặc projection tuỳ bạn)
) {}