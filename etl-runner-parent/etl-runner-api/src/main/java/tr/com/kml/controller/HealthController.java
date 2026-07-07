package tr.com.kml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.kml.dto.HealthResponse;
import tr.com.kml.dto.ReadinessResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping("/live")
    public ResponseEntity<HealthResponse> liveness() {
        HealthResponse response = new HealthResponse(
            "UP",
            "etl-runner-api",
            LocalDateTime.now(),
            "Application is running"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ready")
    public ResponseEntity<?> readiness() {
        try {
            if (dataSource != null) {
                try (Connection connection = dataSource.getConnection()) {
                    String databaseProductName = connection.getMetaData().getDatabaseProductName();
                    String databaseVersion = connection.getMetaData().getDatabaseProductVersion();

                    ReadinessResponse response = new ReadinessResponse(
                        "READY",
                        "etl-runner-api",
                        LocalDateTime.now(),
                        new ReadinessResponse.DatabaseStatus(
                            true,
                            databaseProductName,
                            databaseVersion
                        ),
                        "Application is ready to serve requests"
                    );
                    return ResponseEntity.ok(response);
                }
            } else {
                ReadinessResponse response = new ReadinessResponse(
                    "READY",
                    "etl-runner-api",
                    LocalDateTime.now(),
                    new ReadinessResponse.DatabaseStatus(
                        false,
                        "N/A",
                        "N/A"
                    ),
                    "Application is ready (database not configured)"
                );
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            HealthResponse errorResponse = new HealthResponse(
                "DOWN",
                "etl-runner-api",
                LocalDateTime.now(),
                "Database connection failed: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
        }
    }
}
