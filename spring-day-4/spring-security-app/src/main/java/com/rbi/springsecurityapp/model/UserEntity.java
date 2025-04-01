package com.rbi.springsecurityapp.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;


}
