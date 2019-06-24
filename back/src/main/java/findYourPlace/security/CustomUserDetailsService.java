package findYourPlace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import findYourPlace.entity.User;
import findYourPlace.mongoDB.UserDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserDao users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	final User user = this.users.findByUsername(username);
    	
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
          }
        
        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(user.getPassword())//
                .authorities(user.getRole())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
        
    }
}