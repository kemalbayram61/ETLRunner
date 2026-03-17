package tr.com.kml.ETLRunnerBus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.kml.ETLRunnerBus.dto.LoginRequest;
import tr.com.kml.ETLRunnerBus.dto.LoginResponse;
import tr.com.kml.ETLRunnerBus.util.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Basit login endpoint - hardcoded credential kullanıyor
     * Üretimde bunu database'den yapmalısın
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // Giriş bilgilerini doğrula (örnek: test kullanıcısı)
        if (!authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 401);
            errorResponse.put("message", "Invalid username or password");
            errorResponse.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.status(401).body(errorResponse);
        }

        // Token generate et
        String token = jwtTokenProvider.generateToken(loginRequest.getUsername());

        // Response oluştur
        LoginResponse response = new LoginResponse(
                token,
                loginRequest.getUsername(),
                86400000  // 24 saat
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Kullanıcı bilgilerini doğrula
     */
    private boolean authenticateUser(String username, String password) {
        // Basit örnek - gerçekte UserService kullan
        return "kemal".equals(username) && "kemal".equals(password);
    }

    /**
     * Token'ı yenile (refresh token pattern)
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || authHeader.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 401);
            errorResponse.put("message", "Authorization header missing");
            return ResponseEntity.status(401).body(errorResponse);
        }

        String token = jwtTokenProvider.getTokenFromRequest(authHeader);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 401);
            errorResponse.put("message", "Invalid or expired token");
            return ResponseEntity.status(401).body(errorResponse);
        }

        // Token'dan username çıkar ve yeni token generate et
        String username = jwtTokenProvider.getUsernameFromToken(token);
        String newToken = jwtTokenProvider.generateToken(username);

        LoginResponse response = new LoginResponse(
                newToken,
                username,
                86400000
        );

        return ResponseEntity.ok(response);
    }
}
