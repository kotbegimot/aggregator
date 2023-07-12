package com.example.aggregation.model;

import com.example.aggregation.util.CurrencyProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains pairs <currency, coefficient>
 * Where coefficient is a conversion rate between input and output currency
 */
@Component
public class CurrencyContainer {
    private final CurrencyProperties properties;
    private Map<String, Float> currencyMap;
    CurrencyContainer(CurrencyProperties properties) {
        this.properties = properties;
        currencyMap = new HashMap<>();
        for (String curr : properties.getSourceCurrencies()) {
            currencyMap.put(curr, 0.0f);
        }
    }
    public void print() {
        for (Map.Entry<String, Float> entry : currencyMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
    }
    public Map<String, Float> getData()
    {
        return currencyMap;
    }
}
