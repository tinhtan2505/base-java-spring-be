package nqt.base_java_spring_be.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import nqt.base_java_spring_be.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import nqt.base_java_spring_be.authentication.dto.UserPrincipal;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final TokenBlacklist tokenBlacklist;
    private final UserRepository userRepository;

    // Tạo khóa bí mật 512-bit cho HS512
    private final SecretKey jwtSecret = Jwts.SIG.HS512.key().build();

    public JwtTokenProvider(TokenBlacklist tokenBlacklist, UserRepository userRepository) {
        this.tokenBlacklist = tokenBlacklist;
        this.userRepository = userRepository;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Lấy UUID user. Nếu principal là entity User hay custom principal, lấy id trực tiếp.
        UUID uid = null;
        if (userDetails instanceof nqt.base_java_spring_be.entity.User u) {
            uid = u.getId();
        } else if (userDetails instanceof UserPrincipal up) {
            uid = up.getId();
        } else {
            // fallback: tra DB
            uid = userRepository.findByUsername(userDetails.getUsername())
                    .map(nqt.base_java_spring_be.entity.User::getId)
                    .orElse(null);
        }

        // 24 hours
        long jwtExpirationMs = 86400000;
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
                .claims(Map.of("uid", uid != null ? uid.toString() : null))
                .signWith(jwtSecret, Jwts.SIG.HS512)
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
        Jws<Claims> jwt = Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token);
        return jwt.getPayload().getSubject();
    }

    public UUID getUserIdFromToken(String token) {
        Jws<Claims> jwt = Jwts.parser().verifyWith(jwtSecret).build().parseSignedClaims(token);
        String uid = (String) jwt.getPayload().get("uid");
        return uid != null ? UUID.fromString(uid) : null;
    }

    public void blacklistToken(String token) {
        tokenBlacklist.add(token);
    }
}
