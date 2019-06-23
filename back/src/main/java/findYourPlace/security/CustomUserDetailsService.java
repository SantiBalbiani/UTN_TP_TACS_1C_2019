package findYourPlace.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import findYourPlace.mongoDB.UserDao;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserDao users;
    public CustomUserDetailsService(UserDao users) {
        this.users = users;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) this.users.findByUsername(username);
    }
}