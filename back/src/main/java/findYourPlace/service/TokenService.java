package findYourPlace.service;

import org.json.JSONObject;

import findYourPlace.entity.User;

public interface TokenService {
	
	public String getToken(User user);
	
	public String getUsername(String token);
	
	public String getRol(String token);

}
