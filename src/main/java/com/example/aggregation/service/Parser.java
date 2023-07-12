package com.example.aggregation.service;

import com.example.aggregation.model.Product;

import java.util.List;

public interface Parser {
    /**
     * Getting all products from the source response
     *
     * @param inputString is a response from the source
     * @param source is URL of the source
     * @return list of parsed products
     */
    List<Product> parse(String inputString, String source);
    /**
     * Returns id for ParsePerformer
     * @return the source URL
     */
    String getSource();
}
