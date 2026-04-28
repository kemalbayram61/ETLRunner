package tr.com.kml.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.kml.domain.entity.Script;
import tr.com.kml.dto.queue.ScriptCreateRequest;
import tr.com.kml.service.ScriptApiService;

@RestController
@RequestMapping("/api/v1/scripts")
@RequiredArgsConstructor
public class ScriptApiController {

    private final ScriptApiService scriptApiService;

    @PostMapping
    public ResponseEntity<Script> create(@RequestBody ScriptCreateRequest request) {
        return ResponseEntity.ok(scriptApiService.createNewScript(request));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        scriptApiService.approveScript(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id, @RequestBody String reason) {
        scriptApiService.rejectScript(id, reason);
        return ResponseEntity.ok().build();
    }
}
