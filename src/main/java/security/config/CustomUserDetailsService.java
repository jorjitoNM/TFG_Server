package security.config;


import lombok.RequiredArgsConstructor;
import org.server.domain.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        org.server.dao.model.user.User user = service.findByUsername(email);
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
