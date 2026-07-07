package tr.com.kml.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import tr.com.kml.enums.DatabaseType;

@Entity
@Table(name = "TARGET_DATABASE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TargetDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Örn: "Oracle_Prod_Finans", "PostgreSQL_Dev"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DatabaseType type;

    // ======================== Ortak Alan ========================
    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Integer port;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String encryptedPassword;

    // ======================== Database-Specific Fields ========================
    // PostgreSQL: "public" schema varsayılan
    // Oracle: SID veya Service Name
    // MSSQL: Database name
    // MySQL: Database name
    private String databaseName;

    // Oracle-specific: Service Name (SID yerine kullanılabilir)
    private String serviceName;

    // MSSQL-specific: Instance name (localhost\SQLEXPRESS gibi)
    private String instanceName;

    // Windows Authentication (MSSQL) veya Integrated Security
    private Boolean integratedSecurity;

    // ======================== Bağlantı Ayarları ========================
    @Builder.Default
    private Integer connectionTimeout = 30; // saniye

    @Builder.Default
    private Integer connectionPoolSize = 10;

    @Builder.Default
    private Integer maxConnections = 20;

    @Builder.Default
    private Integer idleTimeout = 900; // 15 dakika

    // ======================== SSL/TLS Ayarları ========================
    @Builder.Default
    private Boolean sslEnabled = false;

    private String sslMode; // PostgreSQL: require, prefer, disable vb.

    private String sslCertPath; // CA certificate path

    private Boolean sslVerify; // Certificate verification

    // ======================== Durum Alanları ========================
    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean testConnection = false; // Son bağlantı test sonucu

    private String testConnectionMessage; // Test sonuç mesajı

    // ======================== Ek Ayarlar ========================
    // JSON formatında ek konfigürasyonlar (compatibility string, charset vs.)
    @Column(columnDefinition = "TEXT")
    private String additionalProperties;

    private String description; // Veritabanı açıklaması

    // ======================== Audit Fields ========================
    @Column(nullable = false, updatable = false)
    private Long createdBy; // User ID

    @Column(nullable = false)
    private Long updatedBy; // User ID

    // ======================== Methods ========================
    public String getConnectionUrl() {
        return switch (type) {
            case POSTGRESQL -> String.format("jdbc:postgresql://%s:%d/%s", host, port, databaseName != null ? databaseName : "postgres");
            case ORACLE -> {
                if (serviceName != null) {
                    yield String.format("jdbc:oracle:thin:@%s:%d/%s", host, port, serviceName);
                } else {
                    yield String.format("jdbc:oracle:thin:@%s:%d:%s", host, port, databaseName);
                }
            }
            case MS_SQL -> {
                String url = String.format("jdbc:sqlserver://%s:%d", host, port);
                if (instanceName != null) {
                    url += ";instanceName=" + instanceName;
                }
                if (databaseName != null) {
                    url += ";databaseName=" + databaseName;
                }
                if (integratedSecurity != null && integratedSecurity) {
                    url += ";integratedSecurity=true";
                }
                yield url;
            }
            case MYSQL -> String.format("jdbc:mysql://%s:%d/%s", host, port, databaseName != null ? databaseName : "mysql");
            case MONGODB -> String.format("mongodb://%s:%d", host, port);
        };
    }

    public Boolean isSSLRequired() {
        return sslEnabled != null && sslEnabled;
    }

    public String getDisplayInfo() {
        return String.format("%s (%s@%s:%d)", name, type.getDisplayName(), host, port);
    }
}

