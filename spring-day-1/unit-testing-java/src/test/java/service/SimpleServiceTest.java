package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceTest {

    @Test
    void testGetStringInUpperCase(){
        // Given
        SimpleService service = new SimpleService();
        String str = "hello";
        // When
        // Then
        Assertions.assertEquals("HELLO",service.getStringInUpperCase(str));
        Assertions.assertThrows(NullPointerException.class,()->service.getStringInUpperCase(null));
    }

}