# **Key Concepts in Spring Security**


## **1. Authentication and Authorization**

### **Authentication**
- Authentication verifies **who you are**.
- A user provides credentials (username & password), and the system verifies them.
- Implemented using **UsernamePasswordAuthenticationToken** in Spring Security.

### **Authorization**
- Authorization determines **what you can do** after authentication.
- It checks **roles and permissions** before allowing access to resources.
- Implemented using **@PreAuthorize, @PostAuthorize, @Secured** annotations.

#### **Example: Securing Endpoints with Authorization**
```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin")
public String adminEndpoint() {
    return "Admin Access";
}
```
Only users with the **ADMIN** role can access this endpoint.

---

## **2. SecurityFilterChain (Spring Boot 3.x Update)**

Spring Security **3.x** removes `WebSecurityConfigurerAdapter` and replaces it with `SecurityFilterChain`.

### **Example of Custom Security Configuration**
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
                .requestMatchers("/public").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin()
            .and()
            .httpBasic();
        return http.build();
    }
}
```
- **`permitAll()`** → Anyone can access `/public`
- **`hasRole("ADMIN")`** → Only `ADMIN` can access `/admin`
- **`hasRole("USER")`** → Only `USER` can access `/user`

---

## **3. UserDetails and UserDetailsService**

Spring Security uses **UserDetailsService** to load user details from a database.

### **Implementing Custom UserDetailsService**
```java
import org.springframework.security.core.userdetails.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
```
This service loads users from a database and provides authentication.

---

## **4. Password Encoding and Security**

Spring Security does **not store plain text passwords**. Always use hashing.

### **Using BCrypt for Password Hashing**
```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("password123"));
    }
}
```
Use `BCryptPasswordEncoder` when saving passwords:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

## **5. Role-Based Access Control (RBAC)**

Role-based access ensures users can access only authorized resources.

### **Using @Secured Annotation**
```java
@Secured("ROLE_ADMIN")
@GetMapping("/admin")
public String adminAccess() {
    return "Admin Content";
}
```

### **Using @PreAuthorize Annotation**
```java
@PreAuthorize("hasRole('USER')")
@GetMapping("/user")
public String userAccess() {
    return "User Content";
}
```

---

## **6. JWT (JSON Web Token) Authentication**

JWT provides **stateless authentication** for APIs.

### **Generating JWT Token**
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
}
```

### **Validating JWT Token in a Filter**
```java
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {
    
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = JwtUtil.extractUsername(token);

            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
            }
        }
        chain.doFilter(request, response);
    }
}
```

---

## **7. CSRF Protection**

- **Enabled by default** for non-REST applications
- **Disabled for REST APIs** since tokens (JWT) provide protection

To disable CSRF (for APIs only):
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .httpBasic();
    return http.build();
}
```

---

## **8. OAuth2 and Social Login**

Spring Security **supports OAuth2 login** for Google, GitHub, etc.

### **Step 1: Add OAuth2 Dependencies**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```

### **Step 2: Configure OAuth2 in application.properties**
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

### **Step 3: Enable OAuth2 Login**
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2Login(); // Enable OAuth2 login
    return http.build();
}
```
