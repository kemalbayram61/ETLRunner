package tr.com.kml.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.kml.domain.entity.TargetDatabase;
import tr.com.kml.dto.TargetDatabaseCreateRequest;
import tr.com.kml.dto.TargetDatabaseUpdateRequest;
import tr.com.kml.repository.TargetDatabaseRepository;
import tr.com.kml.service.TargetDatabaseCoreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetDatabaseCoreServiceImpl implements TargetDatabaseCoreService {

    private final TargetDatabaseRepository targetDatabaseRepository;

    @Override
    @Transactional
    public TargetDatabase create(TargetDatabaseCreateRequest request) {
        targetDatabaseRepository.findByName(request.name())
                .ifPresent(db -> {
                    throw new IllegalArgumentException("Veritabanı adı zaten mevcut: " + request.name());
                });

        TargetDatabase targetDatabase = TargetDatabase.builder()
                .name(request.name())
                .type(request.type())
                .host(request.host())
                .port(request.port())
                .databaseName(request.databaseName())
                .username(request.username())
                .encryptedPassword(encryptPassword(request.password()))
                .active(true)
                .build();

        return targetDatabaseRepository.save(targetDatabase);
    }

    @Override
    public TargetDatabase findById(Long id) {
        return targetDatabaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veritabanı bulunamadı: " + id));
    }

    @Override
    public List<TargetDatabase> findAll() {
        return targetDatabaseRepository.findAll();
    }

    @Override
    public List<TargetDatabase> findActive() {
        return targetDatabaseRepository.findByActive(true);
    }

    @Override
    public List<TargetDatabase> findByName(String name) {
        return targetDatabaseRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional
    public TargetDatabase update(Long id, TargetDatabaseUpdateRequest request) {
        TargetDatabase targetDatabase = findById(id);

        if (!targetDatabase.getName().equals(request.name())) {
            targetDatabaseRepository.findByName(request.name())
                    .ifPresent(db -> {
                        throw new IllegalArgumentException("Veritabanı adı zaten mevcut: " + request.name());
                    });
        }

        targetDatabase.setName(request.name());
        targetDatabase.setType(request.type());
        targetDatabase.setHost(request.host());
        targetDatabase.setPort(request.port());
        targetDatabase.setDatabaseName(request.databaseName());
        targetDatabase.setUsername(request.username());
        targetDatabase.setEncryptedPassword(encryptPassword(request.password()));
        targetDatabase.setActive(request.active());

        return targetDatabaseRepository.save(targetDatabase);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TargetDatabase targetDatabase = findById(id);
        targetDatabaseRepository.delete(targetDatabase);
    }

    @Override
    @Transactional
    public void toggleActive(Long id) {
        TargetDatabase targetDatabase = findById(id);
        targetDatabase.setActive(!targetDatabase.isActive());
        targetDatabaseRepository.save(targetDatabase);
    }

    private String encryptPassword(String password) {
        // TODO: Gerçek şifreleme uygulanmalı (AES-256 veya Spring Security Crypto)
        // Şimdilik placeholder
        return "encrypted_" + password;
    }
}
