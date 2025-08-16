package com.Court.Courtbooking.Service.JwtUtilService;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private static final String SECRET="0928309823098409840928340928340982309482093840923840980923848";

    public Claims extractAllClaims(String token) {
        System.out.println("extacting claims");
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("userRole", String.class);
    }


    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}
