package com.agustin.challenge_possumus.exception;

import com.agustin.challenge_possumus.controller.ConverterController;
import com.agustin.challenge_possumus.service.RomanNumberConverterService;
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

@WebMvcTest({ConverterController.class, GlobalExceptionHandler.class})
@DisplayName("Global Exception Handler Tests")
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RomanNumberConverterService romanNumberConverterService;

    @Test
    @DisplayName("Debe manejar InvalidArabicNumberException correctamente")
    void shouldHandleInvalidArabicNumberException() throws Exception {

        String errorMessage = "El numero debe estar entre 1 y 3999";
        when(romanNumberConverterService.toRoman(0))
                .thenThrow(new InvalidArabicNumberException(errorMessage));

        mockMvc.perform(get("/api/converter/to-roman")
                        .param("number", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.result").doesNotExist());
    }

    @Test
    @DisplayName("Debe manejar InvalidRomanNumberException correctamente")
    void shouldHandleInvalidRomanNumberException() throws Exception {

        String errorMessage = "Numero romano invalido: INVALID";
        when(romanNumberConverterService.toArabic("INVALID"))
                .thenThrow(new InvalidRomanNumberException(errorMessage));

        mockMvc.perform(get("/api/converter/to-arabic")
                        .param("roman", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.result").doesNotExist());
    }

    @Test
    @DisplayName("Debe manejar InvalidArabicNumberException para numeros fuera de rango superior")
    void shouldHandleInvalidArabicNumberExceptionForUpperBound() throws Exception {

        String errorMessage = "El numero debe estar entre 1 y 3999";
        when(romanNumberConverterService.toRoman(4000))
                .thenThrow(new InvalidArabicNumberException(errorMessage));

        mockMvc.perform(get("/api/converter/to-roman")
                        .param("number", "4000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    @DisplayName("Debe manejar InvalidRomanNumberException para formato invalido")
    void shouldHandleInvalidRomanNumberExceptionForInvalidFormat() throws Exception {

        String errorMessage = "Formato de numero romano invalido: IIII";
        when(romanNumberConverterService.toArabic("IIII"))
                .thenThrow(new InvalidRomanNumberException(errorMessage));

        mockMvc.perform(get("/api/converter/to-arabic")
                        .param("roman", "IIII")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}
