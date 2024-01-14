package com.example.flightapp.auth;

import com.example.flightapp.entity.User;
import com.example.flightapp.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AuthenticationException("Not found!") {
                });

        return new UserDetailsImpl(user);
    }

}
