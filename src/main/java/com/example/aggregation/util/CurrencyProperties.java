package com.example.aggregation.util;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "currency")
@Getter
@Setter
public class CurrencyProperties {
    @NotEmpty(message = "Currency property \"sourceCurrencies\" cannot be null:  check application.yml")
    private List<String> sourceCurrencies;
    @NotEmpty(message = "Currency property \"outputCurrency\" cannot be null:  check application.yml")
    private String outputCurrency;
    @NotEmpty(message = "Currency property \"source\" cannot be null:  check application.yml")
    private String source;
}
