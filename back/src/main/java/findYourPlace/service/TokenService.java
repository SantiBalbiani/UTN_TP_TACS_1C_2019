package findYourPlace.service;

import findYourPlace.entity.User;

public interface TokenService {
	
	public String getToken(User user);
	
	public String getUsername(String token);

}
