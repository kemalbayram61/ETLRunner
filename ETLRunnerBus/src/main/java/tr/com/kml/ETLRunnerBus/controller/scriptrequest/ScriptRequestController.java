package tr.com.kml.ETLRunnerBus.controller.scriptrequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.kml.ETLRunnerBus.dto.ProcessResponse;

@RestController
@RequestMapping("/script-requests")
public class ScriptRequestController {
    @PostMapping("add-request")
    public ProcessResponse addScriptRequest() {
        ProcessResponse response = new ProcessResponse();
        response.setProcessName("Sample Script Request");
        response.setStatus("SUCCESS");
        response.setMessage("Script request added successfully.");
        response.setCompletionTime(0.5);
        return response;
    }
}
