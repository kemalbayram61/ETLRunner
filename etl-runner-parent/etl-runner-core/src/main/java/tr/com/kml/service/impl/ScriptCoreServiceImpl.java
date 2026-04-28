package tr.com.kml.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.kml.domain.entity.Script;
import tr.com.kml.domain.entity.TargetDatabase;
import tr.com.kml.dto.queue.ScriptCreateRequest;
import tr.com.kml.enums.ScriptStatus;
import tr.com.kml.repository.ScriptRepository;
import tr.com.kml.repository.TargetDatabaseRepository;
import tr.com.kml.service.ScriptCoreService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScriptCoreServiceImpl implements ScriptCoreService {

    private final ScriptRepository scriptRepository;
    private final TargetDatabaseRepository targetDbRepository;

    @Override
    @Transactional
    public Script create(ScriptCreateRequest request) {
        TargetDatabase db = targetDbRepository.findById(request.targetDbId())
                .orElseThrow(() -> new RuntimeException("Hedef veritabanı bulunamadı"));

        Script script = Script.builder()
                .title(request.title())
                .content(request.content())
                .targetDatabase(db)
                .status(ScriptStatus.DRAFT)
                .createdBy("system_user") // İleride SecurityContext'ten gelecek
                .build();

        return scriptRepository.save(script);
    }

    @Override
    @Transactional
    public Script changeStatus(Long id, ScriptStatus newStatus) {
        Script script = findById(id);

        // Statü Geçiş Kontrolü (Business Logic)
        validateStatusTransition(script.getStatus(), newStatus);

        script.setStatus(newStatus);

        if (newStatus == ScriptStatus.APPROVED) {
            script.setApprovedAt(LocalDateTime.now());
            script.setApprovedBy("admin_user"); // SecurityContext'ten
        }

        return scriptRepository.save(script);
    }

    @Override
    public Script findById(Long id) {
        return scriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Script bulunamadı: " + id));
    }

    @Override
    @Transactional
    public void updateExecutionResult(Long id, String status, String log) {
        Script script = findById(id);
        script.setLastExecutionStatus(status);
        script.setLastExecutedAt(LocalDateTime.now());
        // Loglar için ayrı bir AuditLog entity'si kullanmak daha sağlıklı olur
        scriptRepository.save(script);
    }

    private void validateStatusTransition(ScriptStatus current, ScriptStatus next) {
        // Java 23 Switch Expression ile geçiş kontrolü
        boolean isValid = switch (current) {
            case DRAFT -> next == ScriptStatus.PENDING_APPROVAL;
            case PENDING_APPROVAL -> next == ScriptStatus.APPROVED || next == ScriptStatus.REJECTED;
            case REJECTED -> next == ScriptStatus.DRAFT; // Reddedileni tekrar taslağa çek
            case APPROVED -> next == ScriptStatus.QUEUED;
            case QUEUED -> next == ScriptStatus.RUNNING;
            case RUNNING -> next == ScriptStatus.COMPLETED || next == ScriptStatus.FAILED;
            default -> false;
        };

        if (!isValid) {
            throw new IllegalStateException("Hatalı statü geçişi: " + current + " -> " + next);
        }
    }
}
