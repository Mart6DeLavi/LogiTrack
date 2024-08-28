package com.dataverse.authservice.utils;

import com.dataverse.authservice.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
@Slf4j
public class JwtTokenUtils {
    @Value("${jwt.user_secret}")
    private String userSecret;

    @Value("${jwt.user_secret_lifetime}")
    private Duration userSecretLifetime;

    public String generationUserToken(UserDetails userDetails, Collection<Role> roles) {
        Map<String , Object> claims = new HashMap<>();
        claims.put("roles", roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + getUserSecretLifetime().toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, getUserSecret())
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getUserSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List getRolesFromToken(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
}
