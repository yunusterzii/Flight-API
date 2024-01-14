package com.example.flightapp.auth;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${application.jwt_secret}")
    private String jwtSecret;

    @Value("${application.jwt_expires_in}")
    private long jwtExpirationDate;

    public String generateToken(Authentication auth) {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + jwtExpirationDate);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("uid", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            return !Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration()
                    .before(new Date());
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
