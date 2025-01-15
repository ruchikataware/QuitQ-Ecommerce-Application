package com.hexaware.QuitQApplication.security;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationDate;
    
    //generate token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); // Use the authentication username
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // Convert authorities to a comma-separated string for roles
        String roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        
        // Build the JWT token
        return Jwts.builder()
                   .setSubject(username) // Use 'username' from authentication
                   .claim("role", roles) // Set roles in token
                   .setIssuedAt(currentDate)
                   .setExpiration(expireDate)
                   .signWith(key())
                   .compact();
    }


    private Key key() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalStateException("JWT secret is not configured properly.");
        }
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Get username from token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    // Extract authentication information (username and roles) from token
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = getUsername(token);
        
        // Fetch roles from the token
        String roles = getClaimsFromToken(token).get("role", String.class);
        List<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                                                  .map(SimpleGrantedAuthority::new)
                                                  .collect(Collectors.toList());
        
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    // Extract claims from token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
