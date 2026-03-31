package tr.com.kml.ETLRunnerBus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String type;
    private long expiresIn;
    private String username;

    public LoginResponse(String token, String username, long expiresIn) {
        this.token = token;
        this.type = "Bearer";
        this.username = username;
        this.expiresIn = expiresIn;
    }
}
