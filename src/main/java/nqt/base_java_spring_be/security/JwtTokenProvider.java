package nqt.base_java_spring_be.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final TokenBlacklist tokenBlacklist;

    // Tạo khóa bí mật 512-bit cho HS512
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final long jwtExpirationMs = 86400000; // 24 hours

    public JwtTokenProvider(TokenBlacklist tokenBlacklist) {
        this.tokenBlacklist = tokenBlacklist;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
                .signWith(jwtSecret, Jwts.SIG.HS512) // ⬅️ cú pháp mới
                .serializeToJsonWith(new JacksonSerializer<>()) // ⬅️ bắt buộc nếu dùng jackson
                .compact();
    }

    public boolean validateToken(String token) {
        if (tokenBlacklist.contains(token)) {
            System.out.println("Token đã bị thu hồi (đã logout)");
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(jwtSecret)
                    .json(new JacksonDeserializer<>()) // ⬅️ bắt buộc nếu dùng jackson
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token đã hết hạn");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token không được hỗ trợ");
        } catch (MalformedJwtException e) {
            System.out.println("Token không hợp lệ");
        } catch (SecurityException e) {
            System.out.println("Chữ ký token không hợp lệ");
        } catch (IllegalArgumentException e) {
            System.out.println("Yêu cầu token rỗng");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Jwt<Header, Claims> jwt = Jwts.parser()
                .verifyWith(jwtSecret)
                .json(new JacksonDeserializer<>())
                .build()
                .parseSignedClaims(token);
        return jwt.getPayload().getSubject();
    }

    public void blacklistToken(String token) {
        tokenBlacklist.add(token);
    }
}
