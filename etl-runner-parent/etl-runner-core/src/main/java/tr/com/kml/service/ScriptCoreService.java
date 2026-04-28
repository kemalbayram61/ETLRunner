package tr.com.kml.service;

import tr.com.kml.domain.entity.Script;
import tr.com.kml.dto.queue.ScriptCreateRequest;
import tr.com.kml.enums.ScriptStatus;

public interface ScriptCoreService {
    Script create(ScriptCreateRequest request);

    Script changeStatus(Long id, ScriptStatus newStatus);

    Script findById(Long id);

    void updateExecutionResult(Long id, String status, String log);
}
