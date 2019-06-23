package findYourPlace.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import findYourPlace.entity.User;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.security.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider {

	String secretKey;
	
	Map<String, String> permisos = new HashMap<String, String>(); 
	
	
	
	@Autowired
	UserDao userDao;
	
    @Autowired
    private UserDetailsService userDetailsService;
    @PostConstruct
    protected void init() {
    	permisos.put("admin", "/user");
        secretKey = Constants.SUPER_SECRET_KEY;//Base64.getEncoder().encodeToString(Constants.SUPER_SECRET_KEY.getBytes());
    }
    /*
    public String createToken(String username, String rol) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("rol", rol);
        Date now = new Date();
        Date validity = new Date(now.getTime() + Constants.TOKEN_EXPIRATION_TIME);
        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, Constants.SUPER_SECRET_KEY)//
            .compact();
    }
    */
    
    public Authentication getAuthentication(String token) {
    	
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (token != null ) {
            return token;
        }
        return null;
    }
    public boolean validateToken(String token, String endpoint)  {
        try {
            Jws<Claims> claims = Jwts.parser()
    				.setSigningKey(secretKey)
    				.parseClaimsJws(token);
            
            String rolClaim = (String) claims.getBody().get("Rol");
            
            if (claims.getBody().getExpiration().before(new Date()) || !rolValidoEndpoint(rolClaim, endpoint)){
                return false;
            }

            return true;
            
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException  ("Expired or invalid JWT token");
        }
    }
    
    
    public Boolean rolValidoEndpoint(String rol, String endpoint)
    {
    	String permisoMap = permisos.get(rol);
    	
    	Boolean permisoMapValido = permisoMap == null ? false : ("/" + permisoMap) == endpoint;
    	
    	return ( ("/" + rol).equals(endpoint.startsWith("/user")) || permisoMapValido );

    }
}