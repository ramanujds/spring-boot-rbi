package api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.SimpleService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SimpleApiTest {

    @Mock
    SimpleService service;

    @InjectMocks
    SimpleApi api;

    @Test
    void getUpperCaseStringResponse() {

        // Given
        String str = "hello";
        // When
        Mockito.when(service.getStringInUpperCase(str)).thenReturn("HELLO");
        // Then
        assertEquals("HELLO",api.getUpperCaseStringResponse(str));
        // Verify it called the service method 1 time
        Mockito.verify(service, Mockito.times(1)).getStringInUpperCase(str);

    }
}