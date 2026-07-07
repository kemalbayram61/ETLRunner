package tr.com.kml.dto;

import tr.com.kml.enums.DatabaseType;

public record TargetDatabaseUpdateRequest(
        String name,
        DatabaseType type,
        String host,
        Integer port,
        String databaseName,
        String username,
        String password,
        String serviceName,          // Oracle Service Name
        String instanceName,          // MSSQL Instance Name
        Boolean integratedSecurity,   // MSSQL Windows Auth
        Integer connectionTimeout,
        Integer connectionPoolSize,
        Integer maxConnections,
        Integer idleTimeout,
        Boolean sslEnabled,
        String sslMode,
        String sslCertPath,
        Boolean sslVerify,
        String additionalProperties,
        String description,
        Boolean active
) {
}

