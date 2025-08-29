package com.agustin.challenge_possumus.controller;

import com.agustin.challenge_possumus.service.RomanNumberConverterService;
import com.agustin.challenge_possumus.exception.InvalidArabicNumberException;
import com.agustin.challenge_possumus.exception.InvalidRomanNumberException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConverterController.class)
@DisplayName("Converter Controller - Unit Tests")
public class ConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RomanNumberConverterService romanNumberConverterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/converter/to-roman - Conversion exitosa")
    void shouldConvertToRomanSuccessfully() throws Exception {

        Integer number = 1994;
        String expectedRoman = "MCMXCIV";
        when(romanNumberConverterService.toRoman(number)).thenReturn(expectedRoman);

        mockMvc.perform(get("/api/converter/to-roman")
                        .param("number", number.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result").value(expectedRoman));
    }

    @Test
    @DisplayName("GET /api/converter/to-roman - Border case: numero minimo")
    void shouldConvertMinimumNumberToRoman() throws Exception {

        Integer number = 1;
        String expectedRoman = "I";
        when(romanNumberConverterService.toRoman(number)).thenReturn(expectedRoman);

        mockMvc.perform(get("/api/converter/to-roman")
                        .param("number", number.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result").value(expectedRoman));
    }

    @Test
    @DisplayName("GET /api/converter/to-roman - Border case: numero maximo")
    void shouldConvertMaximumNumberToRoman() throws Exception {

        Integer number = 3999;
        String expectedRoman = "MMMCMXCIX";
        when(romanNumberConverterService.toRoman(number)).thenReturn(expectedRoman);

        mockMvc.perform(get("/api/converter/to-roman")
                        .param("number", number.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result").value(expectedRoman));
    }

    @Test
    @DisplayName("GET /api/converter/to-roman - Excepcion por numero invalido")
    void shouldHandleInvalidArabicNumberException() throws Exception {

        Integer invalidNumber = 0;
        when(romanNumberConverterService.toRoman(invalidNumber))
                .thenThrow(new InvalidArabicNumberException("El numero debe estar entre 1 y 3999"));

        mockMvc.perform(get("/api/converter/to-roman")
                        .param("number", invalidNumber.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("El numero debe estar entre 1 y 3999"));
    }

    @Test
    @DisplayName("GET /api/converter/to-arabic - Conversion exitosa")
    void shouldConvertToArabicSuccessfully() throws Exception {

        String roman = "MCMXCIV";
        Integer expectedArabic = 1994;
        when(romanNumberConverterService.toArabic(roman)).thenReturn(expectedArabic);

        mockMvc.perform(get("/api/converter/to-arabic")
                        .param("roman", roman)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result").value(expectedArabic));
    }

    @Test
    @DisplayName("GET /api/converter/to-arabic - Border case: numero romano minimo")
    void shouldConvertMinimumRomanToArabic() throws Exception {

        String roman = "I";
        Integer expectedArabic = 1;
        when(romanNumberConverterService.toArabic(roman)).thenReturn(expectedArabic);

        mockMvc.perform(get("/api/converter/to-arabic")
                        .param("roman", roman)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result").value(expectedArabic));
    }

    @Test
    @DisplayName("GET /api/converter/to-arabic - Border case: numero romano maximo")
    void shouldConvertMaximumRomanToArabic() throws Exception {

        String roman = "MMMCMXCIX";
        Integer expectedArabic = 3999;
        when(romanNumberConverterService.toArabic(roman)).thenReturn(expectedArabic);

        mockMvc.perform(get("/api/converter/to-arabic")
                        .param("roman", roman)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.result").value(expectedArabic));
    }

    @Test
    @DisplayName("GET /api/converter/to-arabic - Excepcion por numero romano invalido")
    void shouldHandleInvalidRomanNumberException() throws Exception {

        String invalidRoman = "INVALID";
        when(romanNumberConverterService.toArabic(invalidRoman))
                .thenThrow(new InvalidRomanNumberException("Numero romano invalido"));

        mockMvc.perform(get("/api/converter/to-arabic")
                        .param("roman", invalidRoman)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Numero romano invalido"));
    }
}
