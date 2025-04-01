package com.rbi.springsecurityapp.api;

import com.rbi.springsecurityapp.dto.UserCredentialsDto;
import com.rbi.springsecurityapp.dto.UserDto;
import com.rbi.springsecurityapp.model.UserEntity;
import com.rbi.springsecurityapp.repository.UserRepository;
import com.rbi.springsecurityapp.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepo;

    private JwtUtil jwtUtil;

    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepo, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public UserDto signUp(@RequestBody UserDto user){
        String encodedPassword = passwordEncoder.encode(user.password());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.username());
        userEntity.setPassword(encodedPassword);
        userEntity.setRoles(user.roles());
        userEntity =  userRepo.save(userEntity);

        return new UserDto(userEntity.getUsername(),"*****",user.roles());
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody UserCredentialsDto userCredentials) {

        UserEntity user = userRepo.findByUsername(userCredentials.username()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(!passwordEncoder.matches(userCredentials.password(),user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return jwtUtil.generateToken(user.getUsername());

    }




}
