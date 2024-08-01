package com.SpringBoot.SpringSecurity.Config;

import com.SpringBoot.SpringSecurity.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class Jwt {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String TokenG(Map<String,Object> ExtraClaims, User userDetails
    ){
        ExtraClaims.put("email",userDetails.getUsername());
        ExtraClaims.put("role",userDetails.getAuthorities());
        return Jwts
                .builder()
                .setClaims(ExtraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(signInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String TokenG(User userDetails) {
        return TokenG(new HashMap<>(), userDetails);
    }

    public Key signInkey(){
        byte[] KeyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyByte);
    }

    public String extractEmail(String token) {
        return extractClaim(token).get("email").toString();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername())) && !    isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return  extractClaim(token).getExpiration();
    }

    public Claims extractClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(signInkey()).build()
                .parseClaimsJws(token).getBody();
    }

}

