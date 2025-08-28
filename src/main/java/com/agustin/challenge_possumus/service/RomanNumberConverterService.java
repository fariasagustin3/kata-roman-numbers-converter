package com.agustin.challenge_possumus.service;

import com.agustin.challenge_possumus.exception.InvalidArabicNumberException;
import com.agustin.challenge_possumus.exception.InvalidRomanNumberException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RomanNumberConverterService {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 3999;

    private static final Map<Integer, String> ARABIC_TO_ROMAN = new LinkedHashMap<>();

    static {
        ARABIC_TO_ROMAN.put(1000, "M");
        ARABIC_TO_ROMAN.put(900, "CM");
        ARABIC_TO_ROMAN.put(500, "D");
        ARABIC_TO_ROMAN.put(400, "CD");
        ARABIC_TO_ROMAN.put(100, "C");
        ARABIC_TO_ROMAN.put(90, "XC");
        ARABIC_TO_ROMAN.put(50, "L");
        ARABIC_TO_ROMAN.put(40, "XL");
        ARABIC_TO_ROMAN.put(10, "X");
        ARABIC_TO_ROMAN.put(9, "IX");
        ARABIC_TO_ROMAN.put(5, "V");
        ARABIC_TO_ROMAN.put(4, "IV");
        ARABIC_TO_ROMAN.put(1, "I");
    }

    public String toRoman(int arabicNumber) {

        if (arabicNumber < MIN_VALUE || arabicNumber > MAX_VALUE) {
            throw new InvalidArabicNumberException(
                    String.format("El número debe estar entre %d y %d, recibido: %d",
                            MIN_VALUE, MAX_VALUE, arabicNumber));
        }

        StringBuilder roman = new StringBuilder();

        for (Map.Entry<Integer, String> entry : ARABIC_TO_ROMAN.entrySet()) {
            int value = entry.getKey();
            String numeral = entry.getValue();

            int count = arabicNumber / value;
            if (count > 0) {
                roman.append(numeral.repeat(count));
                arabicNumber %= value;
            }
        }

        return roman.toString();
    }

    public int toArabic(String romanNumber) {

        if (romanNumber == null || romanNumber.trim().isEmpty()) {
            throw new InvalidRomanNumberException("El numero romano no puede ser null o vacio");
        }

        String upperRoman = romanNumber.toUpperCase().trim();
        int result = 0;
        int i = 0;

        while (i < upperRoman.length()) {
            if (i + 1 < upperRoman.length()) {
                String twoChar = upperRoman.substring(i, i + 2);
                Integer twoCharValue = getRomanValue(twoChar);
                if (twoCharValue != null) {
                    result += twoCharValue;
                    i += 2;
                    continue;
                }
            }

            String oneChar = upperRoman.substring(i, i + 1);
            Integer oneCharValue = getRomanValue(oneChar);
            if (oneCharValue != null) {
                result += oneCharValue;
                i++;
            } else {
                throw new InvalidRomanNumberException("Caracter invalido en numero romano: " + oneChar);
            }
        }

        if (result < MIN_VALUE || result > MAX_VALUE) {
            throw new InvalidRomanNumberException("Formato de numero romano invalido: " + romanNumber);
        }

        if (!toRoman(result).equals(upperRoman)) {
            throw new InvalidRomanNumberException("Formato de numero romano invalido: " + romanNumber);
        }

        return result;
    }

    private Integer getRomanValue(String romanSymbol) {
        for (Map.Entry<Integer, String> entry : ARABIC_TO_ROMAN.entrySet()) {
            if (entry.getValue().equals(romanSymbol)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
