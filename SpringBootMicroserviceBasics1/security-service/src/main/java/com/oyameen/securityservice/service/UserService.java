package com.oyameen.securityservice.service;

import com.oyameen.securityservice.model.LoginDto;
import com.oyameen.securityservice.model.User;
import com.oyameen.securityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    public User register(LoginDto loginDto) {
        User user = new User(loginDto.getUserName(), loginDto.getUserEmail(),
                passwordEncoder.encode(loginDto.getPassword()),"USER","");
        userRepository.save(user);
        return user;
    }

    public String verify(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserEmail(), loginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            return jwtService.generateToken(loginDto.getUserEmail(), authorities);
        } else {
            return "verifying JWT token failed.";
        }
    }
}