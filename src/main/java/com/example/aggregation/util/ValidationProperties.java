package com.example.aggregation.util;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "validation")
@Getter
@Setter
@Validated
public class ValidationProperties {
    @NotEmpty(message = "Validation property \"priceRegexpStrict\" cannot be null: check application.yml")
    private String priceRegexpStrict;
    @NotEmpty(message = "Validation property \"priceRegexpLenient\" cannot be null: check application.yml")
    private String priceRegexpLenient;
    @EnumNamePattern(regexp = "STRICT|LENIENT")
    private Validation validationType;
}
