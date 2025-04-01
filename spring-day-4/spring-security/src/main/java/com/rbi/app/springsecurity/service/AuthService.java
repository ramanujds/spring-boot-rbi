package com.rbi.app.springsecurity.service;

import com.rbi.app.springsecurity.dto.JwtToken;
import com.rbi.app.springsecurity.dto.UserCredentials;
import com.rbi.app.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;


    public JwtToken authenticate(UserCredentials userCredentials){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
                    String username = authentication.getName();
                    return new JwtToken(jwtUtil.generateToken(username));


        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Credentials", e);
        }




    }


}
