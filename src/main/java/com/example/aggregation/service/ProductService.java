package com.example.aggregation.service;

import com.example.aggregation.model.Product;
import com.example.aggregation.util.SourceProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final HttpRequestService requestService;
    private final ParsePerformer parser;
    private final ProductValidationService validator;
    private final ProductConversionService converter;

    /**
     * Get the list of products from the source after product objects' parsing, validation and conversion
     * @param properties - source properties (contains source settings: id, url, date format ...)
     * @return list of products
     */
    public List<Product> getProducts(@NotNull SourceProperties properties) {
        List<Product> products = new ArrayList<>();
        for (Map.Entry<String, SourceProperties.SourceSettings>entry : properties.getSourceConfigs().entrySet()) {
            List<Product>sourceProducts;
            String source = entry.getValue().getUrl();
            String inDataFormat = entry.getValue().getDateFormat();
            // get request
            String response = requestService.getRequest(source);
            try {
                // parse the request response
                sourceProducts = parser.parse(response, source);
                // validate the list of products
                sourceProducts = validator.validate(sourceProducts, source, inDataFormat);
                // convert  the list of products
                sourceProducts = converter.convert(sourceProducts, source, inDataFormat, properties.getOutputFormat());
                products.addAll(sourceProducts);
            } catch (UnsupportedOperationException e) {
                logger.error("Parsing error: {}", e.getMessage());
            }
        }
        return products;
    }
}
