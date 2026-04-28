package tr.com.kml.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tr.com.kml.enums.ScriptStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "SCRIPT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // SQL veya NoSQL komutu

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_db_id", nullable = false)
    private TargetDatabase targetDatabase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ScriptStatus status = ScriptStatus.DRAFT;

    // Denetim (Audit) Alanları
    @Column(nullable = false)
    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String approvedBy;
    private LocalDateTime approvedAt;

    private String lastExecutionStatus; // SUCCESS, FAILED
    private LocalDateTime lastExecutedAt;
}
