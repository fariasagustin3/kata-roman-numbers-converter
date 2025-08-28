package com.agustin.challenge_possumus.service;

import com.agustin.challenge_possumus.ChallengePossumusApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ChallengePossumusApplication.class)
@ActiveProfiles("test")
@DisplayName("Roman Number Converter Service - Integration Tests")
public class RomanNumberConverterServiceIntegrationTest {

    @Autowired
    private RomanNumberConverterService converterService;

    @Test
    @DisplayName("Debe inyectar correctamente el servicio y realizar conversiones en contexto Spring")
    void shouldInjectServiceAndPerformConversionsInSpringContext() {

        assertNotNull(converterService, "El servicio debe ser inyectado correctamente por Spring");

        String roman = converterService.toRoman(1994);
        assertEquals("MCMXCIV", roman);

        int arabic = converterService.toArabic("MCMXCIV");
        assertEquals(1994, arabic);

        assertEquals(1994, converterService.toArabic(converterService.toRoman(1994)));
    }

    @Test
    @DisplayName("Debe manejar correctamente excepciones en contexto Spring Boot")
    void shouldHandleExceptionsCorrectlyInSpringBootContext() {

        assertThrows(com.agustin.challenge_possumus.exception.InvalidArabicNumberException.class,
                () -> converterService.toRoman(0));

        assertThrows(com.agustin.challenge_possumus.exception.InvalidRomanNumberException.class,
                () -> converterService.toArabic("INVALID"));
    }
}
