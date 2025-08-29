package com.agustin.challenge_possumus.controller;
import com.agustin.challenge_possumus.ChallengePossumusApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = ChallengePossumusApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@DisplayName("Converter Controller - Integration Tests")
public class ConverterControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/converter";
    }

    @Test
    @DisplayName("GET /api/converter/to-roman - Conversion exitosa y casos borde")
    void shouldConvertToRomanSuccessfully() {

        ResponseEntity<Map> response = restTemplate.getForEntity(
                getBaseUrl() + "/to-roman?number=1994", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("MCMXCIV", response.getBody().get("result"));

        ResponseEntity<Map> minResponse = restTemplate.getForEntity(
                getBaseUrl() + "/to-roman?number=1", Map.class);
        assertEquals("I", minResponse.getBody().get("result"));

        ResponseEntity<Map> maxResponse = restTemplate.getForEntity(
                getBaseUrl() + "/to-roman?number=3999", Map.class);
        assertEquals("MMMCMXCIX", maxResponse.getBody().get("result"));
    }

    @Test
    @DisplayName("GET /api/converter/to-roman - Manejo de errores")
    void shouldHandleToRomanErrors() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                getBaseUrl() + "/to-roman?number=0", Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertTrue(response.getBody().get("message").toString().contains("debe estar entre 1 y 3999"));
    }

    @Test
    @DisplayName("GET /api/converter/to-arabic - Conversion exitosa y casos borde")
    void shouldConvertToArabicSuccessfully() {

        ResponseEntity<Map> response = restTemplate.getForEntity(
                getBaseUrl() + "/to-arabic?roman=MCMXCIV", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1994, response.getBody().get("result"));

        ResponseEntity<Map> minResponse = restTemplate.getForEntity(
                getBaseUrl() + "/to-arabic?roman=I", Map.class);
        assertEquals(1, minResponse.getBody().get("result"));

        ResponseEntity<Map> maxResponse = restTemplate.getForEntity(
                getBaseUrl() + "/to-arabic?roman=MMMCMXCIX", Map.class);
        assertEquals(3999, maxResponse.getBody().get("result"));
    }

    @Test
    @DisplayName("GET /api/converter/to-arabic - Manejo de errores")
    void shouldHandleToArabicErrors() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                getBaseUrl() + "/to-arabic?roman=INVALID", Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertNotNull(response.getBody().get("message"));
    }
}
