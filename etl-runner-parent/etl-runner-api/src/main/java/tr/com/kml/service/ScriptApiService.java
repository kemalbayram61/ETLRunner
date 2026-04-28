package tr.com.kml.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.kml.domain.entity.Script;
import tr.com.kml.dto.queue.ScriptCreateRequest;
import tr.com.kml.enums.ScriptStatus;

@Service
@RequiredArgsConstructor
public class ScriptApiService {

    private final ScriptCoreService scriptCoreService;

    @Transactional
    public void approveScript(Long scriptId) {
        scriptCoreService.changeStatus(scriptId, ScriptStatus.APPROVED);
    }

    public void rejectScript(Long id, String reason) {
        scriptCoreService.changeStatus(id, ScriptStatus.REJECTED);
    }

    public Script createNewScript(ScriptCreateRequest scriptCreateRequest) {
        return scriptCoreService.create(scriptCreateRequest);
    }
}
