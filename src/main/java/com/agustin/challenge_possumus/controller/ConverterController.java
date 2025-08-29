package com.agustin.challenge_possumus.controller;

import com.agustin.challenge_possumus.service.RomanNumberConverterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/converter")
public class ConverterController {

    private final RomanNumberConverterService romanNumberConverterService;

    public ConverterController(RomanNumberConverterService romanNumberConverterService) {
        this.romanNumberConverterService = romanNumberConverterService;
    }

    @GetMapping("/to-roman")
    public ResponseEntity<Map<String, Object>> toRoman(@RequestParam("number") Integer number) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.OK.value());
        body.put("result", this.romanNumberConverterService.toRoman(number));
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/to-arabic")
    public ResponseEntity<Map<String, Object>> toArabic(@RequestParam("roman") String roman) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.OK.value());
        body.put("result", this.romanNumberConverterService.toArabic(roman));
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
