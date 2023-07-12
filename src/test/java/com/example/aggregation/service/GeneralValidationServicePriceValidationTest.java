package com.example.aggregation.service;

import com.example.aggregation.util.ValidationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GeneralValidationServicePriceValidationTest {
    @Autowired
    GeneralValidationService validationService;
    @Autowired
    ValidationProperties properties;

    /**
     * Check strict validation
     */
    @Test
    public void checkCorrectFloatStrict() {
        assertTrue(validationService.validatePrice("7,65", properties.getPriceRegexpStrict()));
    }
    @Test
    public void checkFloatWithDotStrict() {
        assertTrue(validationService.validatePrice("0.69", properties.getPriceRegexpStrict()));
    }
    @Test
    public void checkLettersBeforeAndAfterFloatStrict() {
        assertFalse(validationService.validatePrice("abc7.65def", properties.getPriceRegexpStrict()));
    }

    @Test
    public void checkLetterAfterFloatStrict() {
        assertFalse(validationService.validatePrice("7.65d", properties.getPriceRegexpStrict()));
    }

    @Test
    public void checkLetterBeforeFloatStrict() {
        assertFalse(validationService.validatePrice("l7,65", properties.getPriceRegexpStrict()));
    }

    @Test
    public void checkLetterInsideFloatStrict() {
        assertFalse(validationService.validatePrice("7,a65", properties.getPriceRegexpStrict()));
    }

    @Test
    public void checkColonInsideFloatStrict() {
        assertFalse(validationService.validatePrice("7:65", properties.getPriceRegexpStrict()));
    }

    @Test
    public void checkNegativeFloatStrict() {
        assertFalse(validationService.validatePrice("-7.65", properties.getPriceRegexpStrict()));
    }
    /**
     * Check lenient validation
     */
    @Test
    public void checkCorrectFloat() {
        assertTrue(validationService.validatePrice("7,65", properties.getPriceRegexpLenient()));
    }

    @Test
    public void checkLettersBeforeAndAfterFloat() {
        assertTrue(validationService.validatePrice("abc7.65def", properties.getPriceRegexpLenient()));
    }

    @Test
    public void checkLetterAfterFloat() {
        assertTrue(validationService.validatePrice("7.65d", properties.getPriceRegexpLenient()));
    }

    @Test
    public void checkLetterBeforeFloat() {
        assertTrue(validationService.validatePrice("l7,65", properties.getPriceRegexpLenient()));
    }

    @Test
    public void checkLetterInsideFloat() {
        assertFalse(validationService.validatePrice("7,a65", properties.getPriceRegexpLenient()));
    }

    @Test
    public void checkColonInsideFloat() {
        assertFalse(validationService.validatePrice("7:65", properties.getPriceRegexpLenient()));
    }

    @Test
    public void checkNegativeFloat() {
        assertTrue(validationService.validatePrice("-7.65", properties.getPriceRegexpLenient()));
    }
}