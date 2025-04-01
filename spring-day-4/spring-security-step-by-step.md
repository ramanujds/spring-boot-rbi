# **Spring Security in a Spring Boot Project**


## **1. Adding Spring Security to a Spring Boot Project**

First, add the required dependency in `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
This automatically enables basic security, requiring authentication for all endpoints.

---

## **2. Default Security Behavior**

Once you add Spring Security, Spring Boot:
- Secures all endpoints by default
- Uses **Basic Authentication**
- Generates a default password at startup (found in logs)
- Uses an in-memory user with the username **`user`**

Example log:
```
Using generated security password: 3a4b5c6d7e8f
```

You can log in using:
- Username: `user`
- Password: `3a4b5c6d7e8f`

To change the default credentials, update `application.properties`:
```properties
spring.security.user.name=admin
spring.security.user.password=admin123
```

---

## **3. Custom User Authentication with Spring Security**

Instead of using the default user, we can define our own user authentication logic.

### **Defining a Custom User in Memory**
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
```
This sets up a user (`admin/password123`) with the role `USER`.

---

## **4. Role-Based Authorization**

We can restrict access based on user roles.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin()
            .and()
            .httpBasic();

        return http.build();
    }
}
```
- `"/admin/**"` is accessible only to users with the `ADMIN` role.
- `"/user/**"` is accessible only to users with the `USER` role.
- All other endpoints require authentication.

---

## **5. Custom User Authentication with Database (JPA + MySQL)**

Instead of in-memory users, we can use a database for authentication.

### **Step 1: Create a `User` Entity**
```java
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    // Getters and Setters
}
```

### **Step 2: Create a `UserRepository`**
```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

### **Step 3: Implement `UserDetailsService`**
```java
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
```

### **Step 4: Configure Security with Database Authentication**
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
This ensures that passwords are stored securely using **BCrypt hashing**.

---

## **6. Implementing JWT Authentication**

### **Step 1: Add Dependencies**
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.11.5</version>
</dependency>
```

### **Step 2: Create JWT Utility Class**
```java
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
```

### **Step 3: Implement JWT Authentication Filter**
```java
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {
    
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Extract JWT token and authenticate
}
```

---

## **7. Disabling Default Security (For Development Only)**
If you want to temporarily disable security (not recommended for production), use:
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        .csrf().disable();
    return http.build();
}
```

---

## **8. Best Practices for Securing a Spring Boot Application**

- **Use JWT for stateless authentication** instead of session-based authentication.
- **Hash passwords** using `BCryptPasswordEncoder`.
- **Implement role-based access control** for APIs.
- **Enable CSRF protection** for non-REST applications.
- **Use HTTPS** to encrypt API communications.
- **Store secrets securely** using environment variables or Spring Cloud Config.
