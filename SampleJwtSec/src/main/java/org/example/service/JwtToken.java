package org.example.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.dto.AuthRequest;
import org.example.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtToken {

    private final String DECODE_KEY = "c2Vuc2Vzb21ldGhpbmdzcGVsbGNhdXNlc21lbGxzbGlwcGVkY2hhcmFjdGVybWl4dHU";
    public String authenticate(AuthRequest authRequest){
        Map<String,String> claims = new HashMap<>();
        String username = authRequest.getUsername();
        return generateToken(username,claims);
    }

    private String generateToken(String username, Map<String, String> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    public boolean validateToken(String token, UserDetails userDetails){
        return (userDetails.getUsername().equals(extractUsername(token)) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return new Date().before(extractExpirationDate(token));
    }

    private Key getSignKey() {
        byte[] baseKey = Decoders.BASE64.decode(DECODE_KEY);
        return Keys.hmacShaKeyFor(baseKey);
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }
}
