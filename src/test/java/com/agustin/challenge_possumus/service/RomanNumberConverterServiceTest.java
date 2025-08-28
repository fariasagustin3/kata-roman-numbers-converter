package com.agustin.challenge_possumus.service;

import com.agustin.challenge_possumus.exception.InvalidArabicNumberException;
import com.agustin.challenge_possumus.exception.InvalidRomanNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Roman Number Converter Service Tests")
public class RomanNumberConverterServiceTest {

    private RomanNumberConverterService service;

    @BeforeEach
    void setUp() {
        service = new RomanNumberConverterService();
    }

    @Nested
    @DisplayName("Conversion de arabico a romano")
    class ArabicToRomanTests {

        @ParameterizedTest
        @DisplayName("Casos básicos de conversión")
        @CsvSource({
                "1, I",
                "2, II",
                "3, III",
                "4, IV",
                "5, V",
                "9, IX",
                "21, XXI",
                "50, L",
                "100, C",
                "500, D",
                "1000, M"
        })
        void shouldConvertBasicNumbers(int arabic, String expectedRoman) {
            assertEquals(expectedRoman, service.toRoman(arabic));
        }

        @ParameterizedTest
        @DisplayName("Casos adicionales de conversión")
        @CsvSource({
                "27, XXVII",
                "48, XLVIII",
                "94, XCIV",
                "141, CXLI",
                "163, CLXIII",
                "402, CDII",
                "575, DLXXV",
                "911, CMXI",
                "1024, MXXIV",
                "3000, MMM"
        })
        void shouldConvertComplexNumbers(int arabic, String expectedRoman) {
            assertEquals(expectedRoman, service.toRoman(arabic));
        }

        @Test
        @DisplayName("Caso borde: numero minimo permitido (1)")
        void shouldConvertMinimumValue() {
            assertEquals("I", service.toRoman(1));
        }

        @Test
        @DisplayName("Caso borde: numero maximo permitido (3999)")
        void shouldConvertMaximumValue() {
            assertEquals("MMMCMXCIX", service.toRoman(3999));
        }

        @Test
        @DisplayName("Debe lanzar excepcion para numeros menores a 1")
        void shouldThrowExceptionForNumbersLessThanOne() {
            InvalidArabicNumberException exception = assertThrows(
                    InvalidArabicNumberException.class,
                    () -> service.toRoman(0)
            );
            assertTrue(exception.getMessage().contains("debe estar entre 1 y 3999"));
        }

        @Test
        @DisplayName("Debe lanzar excepcion para numeros mayores a 3999")
        void shouldThrowExceptionForNumbersGreaterThan3999() {
            InvalidArabicNumberException exception = assertThrows(
                    InvalidArabicNumberException.class,
                    () -> service.toRoman(4000)
            );
            assertTrue(exception.getMessage().contains("debe estar entre 1 y 3999"));
        }
    }

    @Nested
    @DisplayName("Conversion de romano a arabigo")
    class RomanToArabicTests {

        @ParameterizedTest
        @DisplayName("Casos basicos de conversion")
        @CsvSource({
                "I, 1",
                "II, 2",
                "III, 3",
                "IV, 4",
                "V, 5",
                "IX, 9",
                "XXI, 21",
                "L, 50",
                "C, 100",
                "D, 500",
                "M, 1000"
        })
        void shouldConvertBasicRomanNumbers(String roman, int expectedArabic) {
            assertEquals(expectedArabic, service.toArabic(roman));
        }

        @ParameterizedTest
        @DisplayName("Casos basicos de conversion")
        @CsvSource({
                "XXVII, 27",
                "XLVIII, 48",
                "XCIV, 94",
                "CXLI, 141",
                "CLXIII, 163",
                "CDII, 402",
                "DLXXV, 575",
                "CMXI, 911",
                "MXXIV, 1024",
                "MMM, 3000"
        })
        void shouldConvertComplexRomanNumbers(String roman, int expectedArabic) {
            assertEquals(expectedArabic, service.toArabic(roman));
        }

        @Test
        @DisplayName("Debe manejar numeros romanos en minusculas")
        void shouldHandleLowerCaseRomanNumbers() {
            assertEquals(1994, service.toArabic("mcmxciv"));
            assertEquals(42, service.toArabic("xlii"));
        }

        @Test
        @DisplayName("Caso borde: numero romano minimo (I)")
        void shouldConvertMinimumRomanValue() {
            assertEquals(1, service.toArabic("I"));
        }

        @Test
        @DisplayName("Caso borde: número romano máximo (MMMCMXCIX)")
        void shouldConvertMaximumRomanValue() {
            assertEquals(3999, service.toArabic("MMMCMXCIX"));
        }

        @Test
        @DisplayName("Debe lanzar excepcion para numero romano null")
        void shouldThrowExceptionForNullRomanNumber() {
            InvalidRomanNumberException exception = assertThrows(
                    InvalidRomanNumberException.class,
                    () -> service.toArabic(null)
            );
            assertTrue(exception.getMessage().contains("no puede ser null o vacio"));
        }

        @Test
        @DisplayName("Debe lanzar excepcion para numero romano vacio")
        void shouldThrowExceptionForEmptyRomanNumber() {
            InvalidRomanNumberException exception = assertThrows(
                    InvalidRomanNumberException.class,
                    () -> service.toArabic("")
            );
            assertTrue(exception.getMessage().contains("no puede ser null o vacio"));
        }

        @ParameterizedTest
        @DisplayName("Debe lanzar excepción para caracteres inválidos")
        @ValueSource(strings = {"ABC", "123", "IVZ", "XYZ"})
        void shouldThrowExceptionForInvalidCharacters(String invalidRoman) {
            assertThrows(InvalidRomanNumberException.class,
                    () -> service.toArabic(invalidRoman));
        }

        @ParameterizedTest
        @DisplayName("Debe lanzar excepción para formatos inválidos")
        @ValueSource(strings = {"IIII", "VV", "XXXX", "LL", "CCCC", "DD", "MMMM"})
        void shouldThrowExceptionForInvalidFormats(String invalidRoman) {
            assertThrows(InvalidRomanNumberException.class,
                    () -> service.toArabic(invalidRoman));
        }
    }
}
