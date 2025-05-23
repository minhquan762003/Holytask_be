package com.mq76.holyTask_be.auth;

import com.mq76.holyTask_be.model.Role;
import com.mq76.holyTask_be.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày


    public String generateToken(User user) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("profilePictureUrl", user.getProfilePictureUrl())
                .claim("isActive", user.getIsActive())
                .claim("createdAt",user.getCreatedAt())
                .claim("updatedAt",  user.getUpdatedAt())

                .setIssuedAt(new Date()) // Ngày phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Hạn token
                .signWith(key)
                .compact();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }



    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }


    public Long extractUserId(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(subject);
    }

    // Xác thực token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true; // Token hợp lệ
        } catch (ExpiredJwtException e) {
            System.err.println("Token đã hết hạn: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token không đúng định dạng: " + e.getMessage());
        }catch (JwtException e) {
            System.err.println("Token không hợp lệ: " + e.getMessage());
        }
        return false; // Token không hợp lệ
    }
}