package tr.com.kml.service;

import tr.com.kml.domain.entity.TargetDatabase;
import tr.com.kml.dto.TargetDatabaseCreateRequest;
import tr.com.kml.dto.TargetDatabaseUpdateRequest;

import java.util.List;

public interface TargetDatabaseCoreService {
    TargetDatabase create(TargetDatabaseCreateRequest request);

    TargetDatabase findById(Long id);

    List<TargetDatabase> findAll();

    List<TargetDatabase> findActive();

    List<TargetDatabase> findByName(String name);

    TargetDatabase update(Long id, TargetDatabaseUpdateRequest request);

    void delete(Long id);

    void toggleActive(Long id);
}
