package findYourPlace.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import findYourPlace.entity.User;
import findYourPlace.security.Constants;
import findYourPlace.service.TokenService;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class TokenServiceImpl implements TokenService {


    public String getToken(User user) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expired = nowMillis + Constants.TOKEN_EXPIRATION_TIME;
        
        String token = Jwts.builder().setIssuedAt(now)
        		.setSubject(user.getUsername())
        		.claim("Rol", user.getRole())
				.setExpiration(new Date(expired))
				.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();

        return token;
    }

    public String getUsername(String token) {

        Jws<Claims> claims = Jwts.parser()
				.setSigningKey(Constants.SUPER_SECRET_KEY)
				.parseClaimsJws(token);
				
        return (String) claims.getBody().get("Rol");
        
    }
    
    public String getRol(String token) {

        Jws<Claims> claims = Jwts.parser()
				.setSigningKey(Constants.SUPER_SECRET_KEY)
				.parseClaimsJws(token);
				
        return (String) claims.getBody().get("Rol");
        
    }

}
