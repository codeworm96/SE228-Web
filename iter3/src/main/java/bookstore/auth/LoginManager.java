package bookstore.auth;

import bookstore.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by codeworm on 5/24/16.
 */
public class LoginManager implements UserDetailsService {

    // UserService to be injected
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("User: " + username + " try to login");

        // find user with username
        bookstore.entity.User user = userService.get(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist!");
        }

        // set authority
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        if(user.isAdmin()) {
            setAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);

        return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }
}
