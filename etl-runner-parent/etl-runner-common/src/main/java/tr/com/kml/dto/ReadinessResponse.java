package tr.com.kml.dto;

import java.time.LocalDateTime;

public record ReadinessResponse(
        String status,
        String application,
        LocalDateTime timestamp,
        DatabaseStatus database,
        String message
) {
    public record DatabaseStatus(
            boolean connected,
            String database,
            String version
    ) {
    }
}
