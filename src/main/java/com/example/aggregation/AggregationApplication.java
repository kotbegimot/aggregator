package com.example.aggregation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.demo.util")
public class AggregationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregationApplication.class, args);
	}

}
