package com.someapp.backend.utils.jwt;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import com.someapp.backend.interfaces.extendedinterfaces.ExtendedUserDetails;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${env.SECRET}")
    private String secret;

    @Value("${env.EXPIRATION_TIME}")
    private String expirationTime;

    public String getUsernameFromToken(HttpServletRequest req) {
        String subject[] = getClaimFromToken(getTokenFromRequest(req), Claims::getSubject).split(";");
        return subject[1];
    }

    public UUID getIdFromToken(HttpServletRequest req) {
        String subject[] = getClaimFromToken(getTokenFromRequest(req), Claims::getSubject).split(";");
        return UUID.fromString(subject[0]);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(ExtendedUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), userDetails.getId());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, UUID id) {

        return Jwts.builder().setClaims(claims).setSubject(id.toString() + ';' + subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.valueOf(expirationTime)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(HttpServletRequest req, ExtendedUserDetails userDetails) {
        final String username = getUsernameFromToken(req);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(getTokenFromRequest(req)));
    }

    public String verifyAndDecodeToken(String token) throws Exception {
        SignatureAlgorithm sa = HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), sa.getJcaName());
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
        Base64.Decoder decoder = Base64.getDecoder();

        String[] chunks = token.split("\\.");
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        // Verify integrity of the token
        if (!validator.isValid(tokenWithoutSignature, signature)) {
            throw new Exception("Couldn't verify the token!");
        }

        // decode token
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        return "Boolean.TRUE;";
    }

    private String getTokenFromRequest(HttpServletRequest req) {
        return req.getHeader("Authorization").substring(7);
    }
}