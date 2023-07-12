package com.example.aggregation.util;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "source")
@Getter
@Setter
public class SourceProperties {
    /**
     * type: dateTime
     */
    @NotEmpty(message = "Source property \"outputFormat\" cannot be null: check application.yml")
    private String outputFormat;

    @NotEmpty(message = "Source property \"source1\" cannot be null: check application.yml")
    private String source1;
    @NotEmpty(message = "Source property \"source2\" cannot be null: check application.yml")
    private String source2;
    @NotEmpty(message = "Source property \"source3\" cannot be null: check application.yml")
    private String source3;
    @NotEmpty(message = "Source property \"source4\" cannot be null: check application.yml")
    private String source4;

    /**
     * key:  id (string);
     * value: SourceSettings:
     * url (string), data format (string)
     */
    @NotEmpty(message = "Source property \"sourceConfigs\" cannot be null: check application.yml")
    private Map<String, SourceSettings> sourceConfigs = new HashMap<>();

    public Map<String, SourceSettings> getSourceConfigs() {
        return sourceConfigs;
    }
    @Data
    @AllArgsConstructor
    public static class SourceSettings {
        private String url;
        private String dateFormat;
    }
}
