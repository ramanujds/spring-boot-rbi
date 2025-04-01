package com.rbi.springsecurityapp.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello from Spring Boot";
    }

    @GetMapping("/admin")
    public String sayHelloAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/user")
    public String sayHelloUser(){
        return "Hello User";
    }


}
