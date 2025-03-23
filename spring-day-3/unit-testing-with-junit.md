# **Java Unit Testing with JUnit & Mockito**


## **What is Unit Testing?**
✅ **Unit Testing** → Testing individual components (e.g., services, repositories) in isolation.  
✅ **Mockito** → A framework to create **mock objects** (fake dependencies).  
✅ **JUnit** → A testing framework for writing **test cases** in Java.

---

# **Setting Up JUnit & Mockito in Spring Boot**
### **Add Dependencies (`pom.xml`)**
Spring Boot includes JUnit 5 by default, but we need **Mockito**:
```xml
<dependencies>
    <!-- Spring Boot Starter Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
</dependencies>
```
---

# **Writing Unit Tests with JUnit 5**
## **Scenario: Testing `CustomerService` in a Bank System**
We want to test:
1. Fetching all customers.
2. Finding customers by ID.
3. Saving a new customer.


## **Writing Unit Tests for `CustomerService` Using Mockito**
### **Test Class: `CustomerServiceTest`**
```java


class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;  // Mocked Repository

    @InjectMocks
    private CustomerService customerService;  // Service with Mocked Repo

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize Mocks
    }

    @Test
    void testGetAllCustomers() {
        // Given
        List<Customer> customers = Arrays.asList(new Customer(1L, "Alice", "alice@example.com"),
                                                 new Customer(2L, "Bob", "bob@example.com"));
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<Customer> result = customerService.getAllCustomers();

        // Then
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void testGetCustomerById() {
        // Given
        Customer customer = new Customer(1L, "Alice", "alice@example.com");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // When
        Optional<Customer> result = customerService.getCustomerById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void testSaveCustomer() {
        // Given
        Customer customer = new Customer(1L, "Alice", "alice@example.com");
        when(customerRepository.save(customer)).thenReturn(customer);

        // When
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Then
        assertNotNull(savedCustomer);
        assertEquals("Alice", savedCustomer.getName());
    }
}
```


# **Testing REST Controllers Using `@WebMvcTest`**
- **`@WebMvcTest`** loads only the web layer, making tests **faster**.
- Use **MockMvc** to **send HTTP requests** and verify responses.

---



### **Test Class: `CustomerControllerTest`**
```java
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        // Given
        List<Customer> customers = List.of(new Customer(1L, "Alice", "alice@example.com"));
        when(customerService.getAllCustomers()).thenReturn(customers);

        // When & Then
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        // Given
        Customer customer = new Customer(1L, "Alice", "alice@example.com");
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        // When & Then
        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }
}
```
