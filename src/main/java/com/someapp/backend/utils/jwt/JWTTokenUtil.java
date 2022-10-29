package com.someapp.backend.utils.jwt;

import com.someapp.backend.utils.ExtendedUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${gimmevibe.app.SECRET}")
    private String secret;

    @Value("${gimmevibe.app.EXPIRATION_TIME}")
    private String expirationTime;

    public String getUsernameFromToken(HttpServletRequest req) {
        String subject[] = getClaimFromToken(getTokenFromRequest(req),
                Claims::getSubject).split(";");
        return subject[1];
    }

    public UUID getIdFromToken(HttpServletRequest req) {
        String subject[] = getClaimFromToken(getTokenFromRequest(req),
                Claims::getSubject).split(";");
        return UUID.fromString(subject[0]);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token,
                                   Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(ExtendedUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(),
                userDetails.getId());
    }

    private String doGenerateToken(Map<String, Object> claims,
                                   String subject, UUID id) {

        return Jwts.builder().setClaims(claims).setSubject(
                id.toString() + ';' + subject).setIssuedAt(
                        new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                                + Integer.parseInt(expirationTime)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(HttpServletRequest req,
                                 ExtendedUserDetails userDetails) {
        final String username = getUsernameFromToken(req);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(getTokenFromRequest(req)));
    }

    private String getTokenFromRequest(HttpServletRequest req) {
        return req.getHeader("Authorization").substring(7);
    }
}