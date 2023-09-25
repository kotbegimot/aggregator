package com.example.aggregation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ParsePerformerTest {

    @Test
    @DisplayName("Should Throw Exception For Unsupported Source Type")
    void parseThrowException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            ParsePerformer performer = new ParsePerformer(new ArrayList<>());
            String response = "";
            String source = "invalid";
            performer.parse(response, source);
        });
    }
}