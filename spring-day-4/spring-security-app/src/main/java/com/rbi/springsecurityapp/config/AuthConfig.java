package com.rbi.springsecurityapp.config;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {


    private static final Logger log = LoggerFactory.getLogger(AuthConfig.class);



    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                request -> request.requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/hello").permitAll()
        )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();

    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails adminUser = User.withUsername("admin")
////                .password("$2a$12$pkA4M.dVrkAuGAwaytPMpuq/4qC1JoHTWkeO6grjadMO7Yr91/2Ai")
//                .password("admin1234")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("user")
//                .password("pass1234")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(adminUser,user);
//    }

//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder getDefaultPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }



}
