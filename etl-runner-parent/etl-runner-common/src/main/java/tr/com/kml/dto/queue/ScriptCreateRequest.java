package tr.com.kml.dto.queue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScriptCreateRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull Long targetDbId
) {}
