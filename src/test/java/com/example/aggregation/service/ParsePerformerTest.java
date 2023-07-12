package com.example.aggregation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParsePerformerTest {

    @Test
    void parseThrowException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            ParsePerformer performer = new ParsePerformer(new ArrayList<>());
            String response = "";
            String source = "invalid";
            performer.parse(response, source);
        });
    }
}