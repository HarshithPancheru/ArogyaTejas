package com.devmosaic.arogyatejas.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
   private final String jwtSecret = "supersecurekey123supersecurekey123";
   private final long jwtExpirationMs = 86400000L;

   private Key getSigningKey() {
      return Keys.hmacShaKeyFor(jwtSecret.getBytes());
   }

   public String generateToken(Long id, String userType) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("userId", id);
      claims.put("userType", userType);
      return Jwts.builder()
         .setClaims(claims)
         .setSubject(String.valueOf(id))
         .setIssuedAt(new Date())
         .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // ← use the field
         .signWith(getSigningKey(), SignatureAlgorithm.HS256)
         .compact();
   }

   public String extractEmail(String token) {
      return Jwts.parserBuilder()
         .setSigningKey(getSigningKey())
         .build()
         .parseClaimsJws(token)
         .getBody()
         .getSubject();
   }

   public Map<String, Object> extractAllClaims(String token) {
      return Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
  }
}
