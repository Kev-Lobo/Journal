package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUserName(username);
            if (user != null) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword())
                        .roles(user.getRoles().toArray(new String[0]))
                        .build();
            }
            throw new UsernameNotFoundException("User not found with username: " + username);
        }catch (UsernameNotFoundException e) {
            log.warn("Login attempt failed - user not found: {}", username);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while loading user: {}", username, e);
            throw new RuntimeException("Error loading user: " + username, e);
        }
    }
}
