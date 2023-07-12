package com.example.aggregation.service;

import com.example.aggregation.model.Product;
import com.example.aggregation.util.SourceProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonV1Parser implements Parser {
    private final static Logger logger = LoggerFactory.getLogger(JsonV1Parser.class);
    private final SourceProperties properties;

    /**
     * Parse JSON ver.1 document
     * @param inputString is a response from the source
     * @param source is URL of the source
     * @return list of parsed product objects
     */
    @Override
    public List<Product> parse(@NotNull String inputString, String source) {
        List<Product> products = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(inputString);
        JSONArray productArrayJson = jsonObject.getJSONArray("products");
        for (int i = 0; i < productArrayJson.length(); i++) {
            JSONObject productJson = productArrayJson.getJSONObject(i);
            String name = productJson.getString("name");
            String price = productJson.getString("price");
            String currency = productJson.getString("currency");
            String bestBefore = productJson.getString("bestBefore");
            String id = productJson.getString("id");
            Product product = new Product(name, price, currency, bestBefore, id, source);
            products.add(product);
        }
        return products;
    }
    /**
     * Returns id for ParsePerformer
     * @return parser source id (url)
     */
    @Override
    public String getSource() {
        return properties.getSourceConfigs().get(properties.getSource4()).getUrl();
    }
}
