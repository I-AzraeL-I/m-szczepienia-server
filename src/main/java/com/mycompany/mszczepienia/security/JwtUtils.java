package com.mycompany.mszczepienia.security;

import com.mycompany.mszczepienia.dto.auth.UserDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    public String generateJwt(UserDto userDto) {
        return generateTokenWithExpiration(userDto, jwtExpirationMs);
    }

    public String generateRefreshJwt(UserDto userDto) {
        return generateTokenWithExpiration(userDto, jwtRefreshExpirationMs);
    }

    public Claims getAllClaimsFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubjectFromJwt(String token) {
        return getAllClaimsFromJwt(token).getSubject();
    }

    public List<GrantedAuthority> getAuthoritiesFromJwt(String token) {
        @SuppressWarnings("unchecked")
        var authorities = (List<String>) getAllClaimsFromJwt(token).get(JwtProperties.TOKEN_CLAIM_AUTHORITIES);

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getIdFromJwt(String token) {
        return Long.valueOf((String) getAllClaimsFromJwt(token).get(JwtProperties.TOKEN_CLAIM_USER_ID));
    }

    public boolean isJwtValid(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String generateTokenWithExpiration(UserDto userDto, int tokenExpirationMs) {
        return Jwts.builder()
                .setSubject(userDto.getEmail())
                .claim(JwtProperties.TOKEN_CLAIM_AUTHORITIES, List.of(userDto.getRole()))
                .claim(JwtProperties.TOKEN_CLAIM_USER_ID, userDto.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

}
