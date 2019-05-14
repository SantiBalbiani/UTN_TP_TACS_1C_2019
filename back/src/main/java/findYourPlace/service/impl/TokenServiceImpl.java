package findYourPlace.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import findYourPlace.entity.User;
import findYourPlace.service.TokenService;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;


@Service
public class TokenServiceImpl implements TokenService {

    private static String USERNAME = "username";
    private static String SECRET_KEY = "pepe";

    public String getToken(User user) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject("user/" + user.getUsername())
                .claim(USERNAME, user.getUsername())
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public String getUsername(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);

        return (String) claims.getBody().get(USERNAME);
    }

}
