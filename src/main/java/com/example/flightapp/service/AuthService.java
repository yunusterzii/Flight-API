package com.example.flightapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.flightapp.auth.JwtTokenProvider;
import com.example.flightapp.dto.LoginRequestDTO;
import com.example.flightapp.dto.LoginResponseDTO;
import com.example.flightapp.dto.RegisterRequestDTO;
import com.example.flightapp.entity.User;
import com.example.flightapp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getEmail(),
                dto.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        String jwtToken = tokenProvider.generateToken(auth);
        LoginResponseDTO loginResponseDto = new LoginResponseDTO();
        loginResponseDto.setEmail(dto.getEmail());
        loginResponseDto.setToken("Bearer " + jwtToken);
        return loginResponseDto;
    }

    public ResponseEntity<String> register(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("User with given email is already exist!");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Register Success!", HttpStatus.OK);
    }
}
