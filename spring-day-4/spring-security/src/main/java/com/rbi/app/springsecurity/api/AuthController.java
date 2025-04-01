package com.rbi.app.springsecurity.api;

import com.rbi.app.springsecurity.dto.JwtToken;
import com.rbi.app.springsecurity.dto.UserCredentials;
import com.rbi.app.springsecurity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public JwtToken login(@RequestBody UserCredentials userCredentials){

       return authService.authenticate(userCredentials);


    }




}
