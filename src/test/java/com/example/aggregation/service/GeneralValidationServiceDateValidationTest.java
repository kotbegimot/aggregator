package com.example.aggregation.service;

import com.example.aggregation.util.SourceProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GeneralValidationServiceDateValidationTest {
    @Autowired
    GeneralValidationService validationService;
    @Autowired
    SourceProperties properties;
    @Test
    public void xmlVer1ValidInput() {
        assertTrue(validationService.validateDate("1999-02-28",
                properties.getSourceConfigs().get(properties.getSource1()).getDateFormat()));
    }
    @Test
    public void xmlVer1InvalidInput() {
        assertFalse(validationService.validateDate("1999-02-29",
                properties.getSourceConfigs().get(properties.getSource1()).getDateFormat()));
    }
    @Test
    public void xmlVer1InvalidFormatInput() {
        assertFalse(validationService.validateDate("1999.02.29",
                properties.getSourceConfigs().get(properties.getSource1()).getDateFormat()));
    }
    @Test
    public void xmlVer2ValidInput() {
        assertTrue(validationService.validateDate("28-02-1999",
                properties.getSourceConfigs().get(properties.getSource2()).getDateFormat()));
    }

    @Test
    public void xmlVer2InvalidInput() {
        assertFalse(validationService.validateDate("29-02-1999",
                properties.getSourceConfigs().get(properties.getSource2()).getDateFormat()));
    }

    @Test
    public void xmlVer2InvalidFormatInput() {
        assertFalse(validationService.validateDate("29.02.1999",
                properties.getSourceConfigs().get(properties.getSource2()).getDateFormat()));
    }
    @Test
    public void xmlVer3ValidInput() {
        assertTrue(validationService.validateDate("28.02.1999",
                properties.getSourceConfigs().get(properties.getSource3()).getDateFormat()));
    }

    @Test
    public void xmlVer3InvalidInput() {
        assertFalse(validationService.validateDate("29.02.1999",
                properties.getSourceConfigs().get(properties.getSource3()).getDateFormat()));
    }
    @Test
    public void xmlVer3InvalidFormatInput() {
        assertFalse(validationService.validateDate("29-02-1999",
                properties.getSourceConfigs().get(properties.getSource3()).getDateFormat()));
    }
    @Test
    public void JsonVer1ValidInput() {
        assertTrue(validationService.validateDate("1999-02-28",
                properties.getSourceConfigs().get(properties.getSource4()).getDateFormat()));
    }
    @Test
    public void JsonVer1InvalidInput() {
        assertFalse(validationService.validateDate("1999-02-29",
                properties.getSourceConfigs().get(properties.getSource4()).getDateFormat()));
    }
    @Test
    public void JsonVer1InvalidFormatInput() {
        assertFalse(validationService.validateDate("1999.02.29",
                properties.getSourceConfigs().get(properties.getSource4()).getDateFormat()));
    }
}