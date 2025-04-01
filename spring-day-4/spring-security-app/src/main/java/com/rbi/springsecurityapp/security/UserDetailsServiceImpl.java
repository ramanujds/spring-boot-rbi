package com.rbi.springsecurityapp.security;

import com.rbi.springsecurityapp.model.UserEntity;
import com.rbi.springsecurityapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepo.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User with name "+username+" Not Found")
        );
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return entity.getRoles().stream().map(r->new SimpleGrantedAuthority("ROLE_"+r.toUpperCase()))
                        .toList();
            }

            @Override
            public String getPassword() {
                return entity.getPassword();
            }

            @Override
            public String getUsername() {
                return entity.getUsername();
            }
        };
    }
}
