package com.example.aggregation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for objects Product with name, price, currency, best before date, id and source url.
 */

@Data
@AllArgsConstructor
public class Product {
    @JsonProperty("name")
    String name;
    @JsonProperty("price")
    String price;
    @JsonProperty("currency")
    String currency;
    @JsonProperty("bestBefore")
    String bestBefore;
    @JsonProperty("id")
    String id;
    @JsonProperty("source")
    String source;
}
