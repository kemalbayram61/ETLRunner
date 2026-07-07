package tr.com.kml.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.kml.domain.entity.TargetDatabase;
import tr.com.kml.dto.TargetDatabaseCreateRequest;
import tr.com.kml.dto.TargetDatabaseResponse;
import tr.com.kml.dto.TargetDatabaseUpdateRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetDatabaseApiService {

    private final TargetDatabaseCoreService targetDatabaseCoreService;

    public TargetDatabaseResponse create(TargetDatabaseCreateRequest request) {
        TargetDatabase targetDatabase = targetDatabaseCoreService.create(request);
        return toResponse(targetDatabase);
    }

    public TargetDatabaseResponse getById(Long id) {
        TargetDatabase targetDatabase = targetDatabaseCoreService.findById(id);
        return toResponse(targetDatabase);
    }

    public List<TargetDatabaseResponse> getAll() {
        return targetDatabaseCoreService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TargetDatabaseResponse> getActive() {
        return targetDatabaseCoreService.findActive()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TargetDatabaseResponse> searchByName(String name) {
        return targetDatabaseCoreService.findByName(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TargetDatabaseResponse update(Long id, TargetDatabaseUpdateRequest request) {
        TargetDatabase targetDatabase = targetDatabaseCoreService.update(id, request);
        return toResponse(targetDatabase);
    }

    public void delete(Long id) {
        targetDatabaseCoreService.delete(id);
    }

    public void toggleActive(Long id) {
        targetDatabaseCoreService.toggleActive(id);
    }

    private TargetDatabaseResponse toResponse(TargetDatabase entity) {
        return new TargetDatabaseResponse(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getHost(),
                entity.getPort(),
                entity.getDatabaseName(),
                entity.getUsername(),
                entity.isActive()
        );
    }
}
