package tr.com.kml.ETLRunnerBus.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tr.com.kml.ETLRunnerBus.util.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestPath = request.getRequestURI();
        String requestMethod = request.getMethod();

        System.out.println("Interceptor: " + requestMethod + " " + requestPath);

        // Health check endpoint'i whitelist et (token kontrol yapma)
        if (isPublicEndpoint(requestPath)) {
            return true;
        }

        // Authorization header'ı al
        String authHeader = request.getHeader("Authorization");

        // Token kontrol et
        if (authHeader == null || authHeader.isEmpty()) {
            sendErrorResponse(response, 401, "Authorization header missing");
            return false;
        }

        String token = jwtTokenProvider.getTokenFromRequest(authHeader);

        if (token == null) {
            sendErrorResponse(response, 401, "Invalid Authorization header format");
            return false;
        }

        // Token validity kontrol et
        if (!jwtTokenProvider.validateToken(token)) {
            sendErrorResponse(response, 401, "Invalid or expired token");
            return false;
        }

        // Token valid, request'i devam ettir
        System.out.println("Token valid - request allowed");
        return true;
    }

    /**
     * Public endpoint'leri kontrol et (token gerektirmeyenler)
     */
    private boolean isPublicEndpoint(String path) {
        return path.contains("/health") ||
                path.contains("/swagger") ||
                path.contains("/login") ||
                path.contains("/register");
    }

    /**
     * Error response gönder
     */
    private void sendErrorResponse(HttpServletResponse response,
                                   int status,
                                   String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", System.currentTimeMillis());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
