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

    @Column(nullable = false)
    private String name; // Örn: "Oracle_Prod_Finans"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DatabaseType type;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Integer port;

    private String databaseName; // Schema veya DB ismi

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String encryptedPassword;

    @Builder.Default
    private boolean active = true;
}
