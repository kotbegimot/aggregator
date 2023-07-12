package com.example.aggregation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeneralValidationServiceTest {
    @Autowired
    GeneralValidationService validationService;
    @Test
    void validateInvalidName() {
        assertFalse(validationService.validateName(""));
    }
    @Test
    void validateValidName() {
        assertTrue(validationService.validateName("Chocolate Frog"));
    }
    @Test
    void validateValidCurrency() {
        Set<String> currencySet = new HashSet<>(Arrays.asList("AMD", "TJS ", "KZT", "KGS", "UZS"));
        assertTrue(validationService.validateCurrency("KZT", currencySet));
    }
}