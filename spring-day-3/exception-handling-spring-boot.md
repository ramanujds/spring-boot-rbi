# **Exception Handling in Spring Boot REST APIs**


## **1. Types of Exceptions in Spring Boot REST APIs**

1. **Client-Side Errors (4xx)**
    - `400 Bad Request` → Invalid input data
    - `401 Unauthorized` → Authentication failure
    - `403 Forbidden` → Insufficient permissions
    - `404 Not Found` → Resource not found

2. **Server-Side Errors (5xx)**
    - `500 Internal Server Error` → Unexpected system failure
    - `503 Service Unavailable` → Service temporarily unavailable


## **2. Handling Exceptions Using `@ExceptionHandler`**

The `@ExceptionHandler` annotation is used to handle specific exceptions within a controller.

### **Example: Handling a Custom `ResourceNotFoundException`**
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

```java
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
```
This ensures that when a customer is not found, the API returns a **404 Not Found** status instead of a generic error.

---

## **3. Handling Multiple Exceptions with `@ControllerAdvice`**

To centralize exception handling for multiple controllers, use `@ControllerAdvice`.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation failed", errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
This ensures that all controllers benefit from centralized error handling.

---

## **4. Creating a Standard Error Response**

A standard error response ensures consistency across APIs.

```java
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    // Getters and setters
}
```

Now, every API error response follows a structured format.

**Example Response for a 404 Error:**
```json
{
    "status": "NOT_FOUND",
    "message": "Customer not found with ID: 10"
}
```

**Example Response for Validation Errors:**
```json
{
    "status": "BAD_REQUEST",
    "message": "Validation failed",
    "errors": ["Name must not be blank", "Email is invalid"]
}
```

---

## **5. Handling Validation Errors Using `@Valid`**

Spring Boot supports request validation using `@Valid` and `@Validated`.

```java
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email(message = "Email is invalid")
    private String email;
}
```

```java
@PostMapping
public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequest request) {
    Customer customer = customerService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(customer);
}
```
If validation fails, Spring Boot triggers a `MethodArgumentNotValidException`, which is handled in `GlobalExceptionHandler`.

---

## **6. Using `ResponseStatusException` for Custom API Responses**

Instead of manually throwing exceptions, we can use `ResponseStatusException`.

```java
@GetMapping("/{id}")
public Customer getCustomer(@PathVariable Long id) {
    return customerService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
}
```
This simplifies error handling without requiring custom exception classes.

---

## **7. Best Practices for Exception Handling**

- **Use meaningful HTTP status codes** (`400`, `404`, `500` instead of `200 OK` with an error message).
- **Standardize error response format** using a common `ApiError` class.
- **Centralize exception handling** using `@ControllerAdvice`.
- **Validate request payloads** with `@Valid` and handle validation errors properly.
- **Avoid exposing stack traces** in API responses.

