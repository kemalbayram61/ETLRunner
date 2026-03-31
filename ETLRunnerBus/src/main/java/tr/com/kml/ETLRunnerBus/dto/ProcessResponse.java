package tr.com.kml.ETLRunnerBus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessResponse implements Serializable {
    private String processName;
    private String status;
    private String message;
    private double completionTime;
}
