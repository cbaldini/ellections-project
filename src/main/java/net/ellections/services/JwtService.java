package net.ellections.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.ellections.dto.domain.JwtUser;
import net.ellections.entities.User;
import net.ellections.entities.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateJwt(User user) {
        return this.createJwt(JwtUser.builder()
            .subject(user.getName())
            .id(user.getUuid().toString())
            .expiration(calculateExpirationTime())
            .roles(user.getRoles())
            .build());
    }

    public String createJwt(JwtUser user) {
        if (user.getSubject() == null) {
            log.error("User not found: {}", user.getSubject());
            return null;
        }

        return Jwts
            .builder()
            .setId(user.getId())
            .setSubject(user.getSubject())
            .setIssuedAt(new Date())
            .setExpiration(user.getExpiration())
            .claim("roles", user.getRoles())
            .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    @SuppressWarnings(value = "unchecked")
    public JwtUser getUserDetails(String token, String key) {
        Claims claims = Jwts.parser()
            .setSigningKey(key.getBytes())
            .parseClaimsJws(token)
            .getBody();

        return JwtUser.builder()
            .expiration(claims.getExpiration())
            .id(claims.getId())
            .subject(claims.getSubject())
            .issuedAt(claims.getIssuedAt())
            .roles((List<Role>) claims.get("roles"))
            .build();
    }

    private Date calculateExpirationTime() {
        return new Date(System.currentTimeMillis() + (expirationTime * 1000 * 60));
    }

    public String getJwtSecret() {
        return secret;
    }
}
