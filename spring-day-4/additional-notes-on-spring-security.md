# **Advanced Spring Security Concepts**

## **1. Principal**

### **What is a Principal?**
- A **Principal** represents the currently authenticated user in a system.
- It can be accessed through `SecurityContextHolder`.
- In Spring Security, the `Principal` is typically an instance of `UserDetails`.

### **How to Access the Principal in a Controller**
```java
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/me")
    public String getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "Current user: " + userDetails.getUsername();
    }
}
```
- This returns the currently authenticated user's username.

### **Accessing Principal Using SecurityContextHolder**
```java
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
```
---

## **2. Roles and Authorities**

### **What are Roles?**
- Roles define a **broad category of permissions** assigned to a user (e.g., `ADMIN`, `USER`).
- Roles in Spring Security are **prefixed with `ROLE_`** (e.g., `ROLE_ADMIN`).

### **What are Authorities?**
- Authorities define **fine-grained permissions** (e.g., `READ_PRIVILEGES`, `WRITE_PRIVILEGES`).
- Unlike roles, authorities **do not require a prefix**.

### **Roles vs. Authorities Example**
| Feature          | Roles             | Authorities |
|-----------------|------------------|-------------|
| Prefix required | `ROLE_` (e.g., `ROLE_USER`) | No prefix (e.g., `WRITE_PRIVILEGES`) |
| Granularity     | Broad category (Admin/User) | Fine-grained permissions (Read/Write) |
| Storage         | Typically stored in **User entity** | Stored in a separate table for flexibility |

---

## **3. Granting Roles and Authorities in Spring Security**

### **Step 1: Define a User Entity with Roles and Authorities**
```java
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    // Getters and Setters
}
```

### **Step 2: Load Roles in `UserDetailsService`**
```java
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.stream.Collectors;

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

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
        );
    }
}
```
- This assigns **roles** as **authorities** to the user.

---

## **4. Securing APIs Based on Roles and Authorities**

### **Using `@Secured` for Role-Based Access**
```java
@Secured("ROLE_ADMIN")
@GetMapping("/admin")
public String adminAccess() {
    return "Admin Content";
}
```
- Only users with `ROLE_ADMIN` can access this API.

### **Using `@PreAuthorize` for Fine-Grained Access**
```java
@PreAuthorize("hasAuthority('WRITE_PRIVILEGES')")
@PostMapping("/create")
public String createResource() {
    return "Resource Created";
}
```
- Only users with `WRITE_PRIVILEGES` authority can access this API.

### **Using Multiple Role-Based Conditions**
```java
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
@GetMapping("/management")
public String managementAccess() {
    return "Management Access";
}
```
- Both `ADMIN` and `MANAGER` roles can access this endpoint.

---

## **5. SecurityContext and Authentication Object**

Spring Security stores authentication information in a **SecurityContext**.

### **How to Retrieve SecurityContext in a Service Layer**
```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
```
- This method retrieves the **current authentication object** containing user details.

---

## **6. Customizing User Authentication and Authorization**

### **Step 1: Custom Authentication Provider**
```java
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("admin".equals(username) && "admin123".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, List.of());
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
```
- This custom provider authenticates users without a database.

### **Step 2: Register Custom Provider in Security Configuration**
```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
}
```
- Spring will use the custom authentication provider for login.

---

## **7. Security for Method-Level Authorization**

Spring Security allows securing **methods** at the service level.

### **Using `@PreAuthorize` on a Service Method**
```java
@PreAuthorize("hasRole('ADMIN')")
public void performAdminTask() {
    System.out.println("Admin task executed!");
}
```
- Only **ADMIN** users can execute this method.

### **Using `@PostAuthorize` for Response Validation**
```java
@PostAuthorize("returnObject.owner == authentication.name")
public Document getDocument(Long id) {
    return documentRepository.findById(id).orElseThrow();
}
```
- Ensures that only the **owner** of the document can access it.

---

## **8. Implementing Two-Factor Authentication (2FA) with Spring Security**

Spring Security allows integrating **2FA using OTPs (One-Time Passwords)**.

### **Step 1: Generate OTP for User Login**
```java
import java.security.SecureRandom;

public class OTPGenerator {

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
```
- This generates a **6-digit OTP**.

### **Step 2: Verify OTP Before Granting Access**
```java
@PreAuthorize("@otpService.validateOTP(authentication.name, #otp)")
@PostMapping("/verify-otp")
public String verifyOtp(@RequestParam String otp) {
    return "OTP Verified!";
}
```
- The method ensures that **only valid OTPs** grant access.

---
