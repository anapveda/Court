package com.Court.Courtbooking.Service.JwtUtilService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {


    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        byte [] keyBytes= Decoders.BASE64.decode("0928309823098409840928340928340982309482093840923840980923848");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
