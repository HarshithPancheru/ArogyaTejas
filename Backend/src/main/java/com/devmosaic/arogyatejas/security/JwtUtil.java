package com.devmosaic.arogyatejas.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devmosaic.arogyatejas.model.User;
import com.devmosaic.arogyatejas.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
   private final String jwtSecret = "supersecurekey123supersecurekey123";
   private final long jwtExpirationMs = 86400000L;
   
   @Autowired
   private UserRepository userRepository;

   private Key getSigningKey() {
      return Keys.hmacShaKeyFor(jwtSecret.getBytes());
   }

   public String generateToken(User user) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("userId", user.getUserId());
      return Jwts.builder()
         .setClaims(claims)
         .setSubject(user.toString())
         .setIssuedAt(new Date())
         .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // ← use the field
         .signWith(getSigningKey(), SignatureAlgorithm.HS256)
         .compact();
   }

  public Optional<User> extractAllClaims(String token) {
        try {
            // 1. Parse the token to get the claims
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

            // 2. Extract the userId from the claims
            // Assuming your User ID is a UUID
            UUID userId = UUID.fromString(claims.get("userId").toString());

            // 3. Find the user in the database.
            // findById already returns an Optional<User>, which is perfect.
            return userRepository.findById(userId);

        } catch (JwtException e) {

            // This catches any token-related error (expired, malformed, etc.)
            // If the token is invalid, we can't trust it, so we return empty.
            return Optional.empty();
        }
    }
}
