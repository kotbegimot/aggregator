package com.example.aggregation.service;

import com.example.aggregation.model.CurrencyContainer;
import com.example.aggregation.model.Product;
import com.example.aggregation.util.Validation;
import com.example.aggregation.util.ValidationProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductValidationService {
    private final static Logger logger = LoggerFactory.getLogger(GeneralValidationService.class);
    private final ValidationProperties properties;
    private final CurrencyContainer currencyContainer;
    private final GeneralValidationService validationService;

    /**
     * Validates the list of products. Excludes invalid products from the list.
     * @param products - list of unvalidated products
     * @param source - the products' source
     * @param dateFormatStr - the date format for validation
     * @return validated list of products
     */
    public List<Product> validate(List<Product> products, String source, String dateFormatStr) {
        for (int i = products.size() - 1; i >= 0; i--) {
            Product product = products.get(i);
            if (!validateName(product.getName())) {
                logger.error("Element name is invalid! BestBefore: {};  Price: {};  Currency: {}; Id: {}; Source: {}",
                        product.getBestBefore(), product.getPrice(), product.getCurrency(), product.getId(), source);
                products.remove(i);
                continue;
            }
            if (!validateDate(product.getBestBefore(), dateFormatStr)) {
                logger.error(String.format("Date is invalid! bestBefore: %s; Name: %s; Source: %s",
                        product.getBestBefore(), product.getName(), source));
                products.remove(i);
                continue;
            }
            if (!validatePrice(product.getPrice())) {
                logger.error("Price is invalid! Price: {}; Name: {}; Source: {}",
                        product.getPrice(), product.getName(), source);
                products.remove(i);
                continue;
            }
            if (!validateCurrency(product.getCurrency())) {
                logger.error("Currency is invalid! Currency: {}; Name: {}; Source: {}",
                        product.getCurrency(), product.getName(), source);
                products.remove(i);
                continue;
            }
        }
        return products;
    }

    /**
     * Validates a product name paying attention to validation type
     * @param name - a product name
     * @return bool
     */
    public boolean validateName(@NotNull String name) {
        return properties.getValidationType() != Validation.STRICT || validationService.validateName(name);
    }

    /**
     * Validates a product date
     * @param date - a product date
     * @param dateFormat - product's source date format
     * @return bool
     */
    public boolean validateDate(@NotNull String date, @NotNull String dateFormat) {
        return validationService.validateDate(date, dateFormat);
    }

    /**
     * Validates a product price paying attention to validation type
     * @param price - a product price
     * @return bool
     */
    public boolean validatePrice(@NotNull String price) {
        String regExp = properties.getValidationType() == Validation.STRICT ?
                properties.getPriceRegexpStrict() : properties.getPriceRegexpLenient();
        return validationService.validatePrice(price, regExp);
    }

    /**
     * Validates a product currency
     * @param currency - a product currency
     * @return bool
     */
    public boolean validateCurrency(@NotNull String currency) {
        return validationService.validateCurrency(currency, currencyContainer.getData().keySet());
    }
}
