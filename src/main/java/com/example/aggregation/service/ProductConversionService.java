package com.example.aggregation.service;

import com.example.aggregation.model.CurrencyContainer;
import com.example.aggregation.model.Product;
import com.example.aggregation.util.CurrencyProperties;
import com.example.aggregation.util.ValidationProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductConversionService {
    private final static Logger logger = LoggerFactory.getLogger(GeneralValidationService.class);
    private final CurrencyProperties currencyProperties;
    private final ValidationProperties validationProperties;
    private final CurrencyContainer currencyContainer;
    private final GeneralConversionService conversionService;

    /**
     * Check the products matches output format and converts them to this format if needed
     * @param products - list of validated products
     * @param source - source
     * @param inDateFormatStr - input date format (dateTime string)
     * @param outDateFormatStr - output date format (dateTime string)
     * @return list of products converted to output format
     */
    public List<Product> convert(List<Product> products, String source,
                                 String inDateFormatStr, String outDateFormatStr) {
        for (int i = products.size() - 1; i >= 0; i--) {
            boolean modified = false;
            Product product = products.get(i);
            modified |= checkPrice(product, source);
            modified |= checkDate(product, source, inDateFormatStr, outDateFormatStr);
            if (modified)
                products.set(i, product);
        }
        return products;
    }

    /**
     * Checks the product's price matches output format and converts it to this format if needed
     * @param product - checked product object
     * @param source - the product's source
     * @return bool - a status of product modification
     */
    boolean checkPrice(Product product, String source) {
        boolean modified = false;
        if (!product.getCurrency().equals(currencyProperties.getOutputCurrency())) {
            try {
                String price = conversionService.parsePrice(product.getPrice(), validationProperties.getPriceRegexpLenient());
                product.setPrice(
                        conversionService.convertPrice(price, currencyContainer.getData().get(product.getCurrency())));
                product.setCurrency(currencyProperties.getOutputCurrency());
                modified = true;
            } catch (Exception e) {
                logger.error("Price conversion failed: {}, price: {}, currency: {}, source: {}",
                        e.getMessage(), product.getPrice(), product.getCurrency(), source);
            }
        }
        return modified;
    }

    /**
     * Checks the product's date matches output format and converts it to this format if needed
     * @param product - checked product object
     * @param source - the product's source
     * @param inDateFormat - input date format (dateTime string)
     * @param outDateFormat - output date format (dateTime string)
     * @return bool - a status of product modification
     */
    boolean checkDate(Product product, String source, String inDateFormat, String outDateFormat) {
        boolean modified = false;
        if (!inDateFormat.equals(outDateFormat)) {
            try {
                product.setBestBefore(conversionService.convertDate(product.getBestBefore(), inDateFormat, outDateFormat));
                modified = true;
            } catch (Exception e) {
                logger.error("Date conversion failed: {}, date: {}, input date format: {}, output date format: {}, source: {}",
                        e.getMessage(), product.getBestBefore(), inDateFormat, outDateFormat, source);
            }
        }
        return modified;
    }
}
