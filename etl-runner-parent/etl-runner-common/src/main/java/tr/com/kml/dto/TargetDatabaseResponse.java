package tr.com.kml.dto;

import tr.com.kml.enums.DatabaseType;

public record TargetDatabaseResponse(
        Long id,
        String name,
        DatabaseType type,
        String host,
        Integer port,
        String databaseName,
        String serviceName,
        String instanceName,
        Boolean integratedSecurity,
        String username,
        Integer connectionTimeout,
        Integer connectionPoolSize,
        Integer maxConnections,
        Integer idleTimeout,
        Boolean sslEnabled,
        String sslMode,
        Boolean sslVerify,
        String description,
        Boolean active,
        Boolean testConnection,
        String connectionUrl
) {
}

