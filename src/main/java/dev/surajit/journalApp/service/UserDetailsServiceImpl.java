package dev.surajit.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.surajit.journalApp.entity.UserEntity;
import dev.surajit.journalApp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new UsernameNotFoundException("User has no password set: " + username);
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
