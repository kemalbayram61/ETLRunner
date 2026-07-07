package tr.com.kml.dto;

import tr.com.kml.enums.DatabaseType;

public record TargetDatabaseResponse(
    Long id,
    String name,
    DatabaseType type,
    String host,
    Integer port,
    String databaseName,
    String username,
    boolean active
) {}
