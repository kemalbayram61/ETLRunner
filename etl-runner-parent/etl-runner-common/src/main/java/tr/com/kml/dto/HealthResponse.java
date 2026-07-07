package tr.com.kml.dto;

import java.time.LocalDateTime;

public record HealthResponse(
        String status,
        String application,
        LocalDateTime timestamp,
        String message
) {
}
