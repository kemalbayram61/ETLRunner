package tr.com.kml.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.kml.dto.TargetDatabaseCreateRequest;
import tr.com.kml.dto.TargetDatabaseResponse;
import tr.com.kml.dto.TargetDatabaseUpdateRequest;
import tr.com.kml.service.TargetDatabaseApiService;

import java.util.List;

@RestController
@RequestMapping("/target-databases")
@RequiredArgsConstructor
public class TargetDatabaseController {

    private final TargetDatabaseApiService targetDatabaseApiService;

    @PostMapping
    public ResponseEntity<TargetDatabaseResponse> create(@RequestBody TargetDatabaseCreateRequest request) {
        TargetDatabaseResponse response = targetDatabaseApiService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TargetDatabaseResponse>> getAll() {
        List<TargetDatabaseResponse> databases = targetDatabaseApiService.getAll();
        return ResponseEntity.ok(databases);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TargetDatabaseResponse>> getActive() {
        List<TargetDatabaseResponse> databases = targetDatabaseApiService.getActive();
        return ResponseEntity.ok(databases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetDatabaseResponse> getById(@PathVariable Long id) {
        TargetDatabaseResponse database = targetDatabaseApiService.getById(id);
        return ResponseEntity.ok(database);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TargetDatabaseResponse>> search(@RequestParam String name) {
        List<TargetDatabaseResponse> databases = targetDatabaseApiService.searchByName(name);
        return ResponseEntity.ok(databases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TargetDatabaseResponse> update(
            @PathVariable Long id,
            @RequestBody TargetDatabaseUpdateRequest request) {
        TargetDatabaseResponse response = targetDatabaseApiService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        targetDatabaseApiService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<Void> toggleActive(@PathVariable Long id) {
        targetDatabaseApiService.toggleActive(id);
        return ResponseEntity.ok().build();
    }
}
