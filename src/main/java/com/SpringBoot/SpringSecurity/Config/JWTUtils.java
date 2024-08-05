package com.SpringBoot.SpringSecurity.Config;

import com.SpringBoot.SpringSecurity.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTUtils {

    @Value("${spring.jwtToken.secretKey}")
    private String SECRET_KEY;

    public String extractEmail(String Token) {
        return extractClaim(Token).get("email").toString();
    }

    public Collection<? extends GrantedAuthority> extractRole (String Token) {
        Object roleClaim = extractClaim(Token).get("role");
        System.out.println("test2");

        if(roleClaim instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<HashMap<String,String>> roles = (List<HashMap<String,String>>) roleClaim;
            return roles.stream()
                    .map(x-> new SimpleGrantedAuthority(x.get("authority")))
                    .collect(Collectors.toSet());

        }else {

            return List.of(new SimpleGrantedAuthority(null));

        }

    }

    public String generateToken( Map<String, Object> extraClaims, User userDetails) {

        extraClaims.put("email",userDetails.getEmail());
        extraClaims.put("role",userDetails.getAuthorities());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10 ))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String Token, UserDetails userDetails) {
        final String username = extractEmail(Token);
        return (username.equals(userDetails.getUsername())) && !    isTokenExpired(Token);
    }

    private boolean isTokenExpired(String Token) {
        return extractExpiration(Token).before(new Date());
    }

    private Date extractExpiration(String Token) {
        return  extractClaim(Token).getExpiration();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaim(String Token) {
        return Jwts.parser().setSigningKey(getSignInKey()).build()
                .parseClaimsJws(Token).getBody();
    }

}

