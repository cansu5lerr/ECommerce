package com.example.ecommerce.service.jwt;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.ecommerce.service.security.UserDetailsImpl;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${example.app.jwtSecret}")
    private String jwtSecret;

    @Value("${example.app.jwtExpirationMs}")
    private long jwtExpirationMs;

 public String generateJwtToken(Authentication authentication) {
     UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

     Map<String, Object> claims = new HashMap<>();
     claims.put("username", userPrincipal.getUsername());
     claims.put("email", userPrincipal.getEmail());
     claims.put("id", userPrincipal.getId());
     return Jwts.builder()
             .setClaims(claims)
             .setSubject(userPrincipal.getId().toString())
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
             .signWith(key(), SignatureAlgorithm.HS256)
             .compact();
 }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = (String) claims.get("username");
            return username;
        } catch (Exception e) {
            return null;
        }
    }

    public String extractId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Integer id = (Integer) claims.get("id");
            return id.toString();
        } catch (Exception e) {
            return null;
        }
    }
    public boolean validateJwtToken(String authToken) { //tokenın geçerliliğini doğrulamak için kullanılır
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}