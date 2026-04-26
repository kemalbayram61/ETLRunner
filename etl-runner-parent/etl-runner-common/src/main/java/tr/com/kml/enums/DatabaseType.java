package tr.com.kml.enums;

import lombok.Getter;

@Getter
public enum DatabaseType {

    POSTGRESQL("PostgreSQL", "org.postgresql.Driver", 5432),
    ORACLE("Oracle", "oracle.jdbc.OracleDriver", 1521),
    MONGODB("MongoDB", null, 27017),
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver", 3306),
    MS_SQL("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver", 1433);

    private final String displayName;
    private final String driverClassName;
    private final int defaultPort;

    DatabaseType(String displayName, String driverClassName, int defaultPort) {
        this.displayName = displayName;
        this.driverClassName = driverClassName;
        this.defaultPort = defaultPort;
    }

    public static DatabaseType fromString(String text) {
        for (DatabaseType type : DatabaseType.values()) {
            if (type.name().equalsIgnoreCase(text) || type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Desteklenmeyen veritabanı tipi: " + text);
    }
}
