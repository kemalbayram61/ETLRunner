package tr.com.kml.ETLRunnerBus.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Token'ın validity'sini kontrol et
     */
    public boolean validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }

            Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Authorization header'dan token'ı çıkar
     */
    public String getTokenFromRequest(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * JWT Token generate et
     */
    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        long expiryDate = now + jwtExpiration;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiryDate))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
    }

    /**
     * Token'dan username çıkar
     */
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            System.out.println("Failed to extract username from token: " + e.getMessage());
            return null;
        }
    }
}
