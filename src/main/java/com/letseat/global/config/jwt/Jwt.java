package com.letseat.global.config.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;

@Component
public abstract class Jwt<T> {

    @Value("${jwt.secret-key}")
    protected String secretKey;

    protected Key key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public abstract String generateToken(T info);

    public abstract T getInfoFromToken(String token);
}
