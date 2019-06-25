package findYourPlace.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import findYourPlace.mongoDB.UserDao;
import findYourPlace.security.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


@Component
public class JwtTokenProvider {

	String secretKey;
	
	Map<String, String> permisos = new HashMap<String, String>(); 
	
	@Autowired
	UserDao userDao;
	
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @PostConstruct
    protected void init() {
    	permisos.put("admin", "/user");
        secretKey = Constants.SUPER_SECRET_KEY;//Base64.getEncoder().encodeToString(Constants.SUPER_SECRET_KEY.getBytes());
    }
    public Authentication getAuthentication(String token) {
    	
    	System.out.println("Token es:" + token);
    	System.out.println("Username es:" + getUsername(token));
    	
    	System.out.println("userDetailsService es:" + userDetailsService);
    	
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        
        System.out.println("userDetails es:" + userDetails);
        
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
        	
        	String path = endpoint.startsWith("/user") ? "/user" : (endpoint.startsWith("/admin") ? "/admin" : null);
        	
            Jws<Claims> claims = Jwts.parser()
    				.setSigningKey(secretKey)
    				.parseClaimsJws(token);
            
            String rolClaim = (String) claims.getBody().get("Rol");
            
            if (claims.getBody().getExpiration().before(new Date()) || !rolValidoEndpoint(rolClaim, path)){
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
    	
    	System.out.println("permisoMap: " + permisoMap);
    	
    	System.out.println("rol: " + rol);
    	
    	System.out.println("endpoint: " + endpoint);
    	
    	Boolean permisoMapValido = permisoMap == null ? false : (permisoMap) == endpoint;
    	
    	return ( ("/" + rol).equals(endpoint) || permisoMapValido );


    }
}