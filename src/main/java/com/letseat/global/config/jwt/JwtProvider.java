package com.letseat.global.config.jwt;

import com.letseat.application.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.apache.tomcat.websocket.Constants.AUTHORIZATION_HEADER_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider extends Jwt<String> {

    private final UserDetailsService userService;

    private long tokenValidTime = 30 * 24 * 60 * 60 * 1000L;

    private long refreshTokenValidTime = 365 * 24 * 60 * 60 * 1000L;

    public String generateToken(String sub) {
        Claims claims = Jwts.claims().setSubject(sub);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String sub) {
        Claims claims = Jwts.claims().setSubject(sub);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + refreshTokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getInfoFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String getInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        checkEmptyToken(request);
        return request.getHeader(AUTHORIZATION_HEADER_NAME).replace("Bearer", "").trim();
    }

    private void checkEmptyToken(HttpServletRequest request) {
        if (request.getHeader(AUTHORIZATION_HEADER_NAME) == null) {
            log.warn("empty token", new RuntimeException());
        }
    }

    public boolean validateTokenIssuedDate(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER_NAME);
    }
}
