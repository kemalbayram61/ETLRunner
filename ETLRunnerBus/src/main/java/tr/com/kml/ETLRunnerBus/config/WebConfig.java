package tr.com.kml.ETLRunnerBus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tr.com.kml.ETLRunnerBus.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/etlrunnerbus/v1/health",
                        "/etlrunnerbus/v1/health/**",
                        "/etlrunnerbus/v1/auth/login",      // ← Ekle
                        "/etlrunnerbus/v1/auth/refresh",    // ← Ekle
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }
}
