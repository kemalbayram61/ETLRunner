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
    boolean active
) {}
