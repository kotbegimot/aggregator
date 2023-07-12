package com.example.aggregation.service;

import com.example.aggregation.model.Product;
import com.example.aggregation.util.SourceProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {
    private final static Logger logger = LoggerFactory.getLogger(ProductAggregatorService.class);
    private final CurrencyService currencyService;
    private final ProductService productService;
    private final SourceProperties sourceProperties;

    /**
     * Gets a list of products from product Service and performs this list deduplication
     * In case of duplicated in the responses only the items with the minimum
     * `bestBefore` filed value are considered.
     * @return final list of products for the output
     */
    public List<Product> getProducts() {
        List<Product> products = null;
        logger.info("Aggregation started");

        // set currency conversion values
        currencyService.fillCurrencyContainer();

        // get lists of products from different sources
        products = productService.getProducts(sourceProperties);

        // aggregate lists of products: remove duplicates
        products.sort(Comparator.comparing(Product::getName));
        if (products.size() > 1) {
            for (int i = products.size() - 2; i >= 0; i--) {
                if (products.get(i).getName().isEmpty())
                    continue;
                if (products.get(i).getName().equals(products.get(i + 1).getName())) {
                    // to remain the one with minimal bestBefore date
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(sourceProperties.getOutputFormat()).withLocale(Locale.ENGLISH);
                        LocalDate date1 = LocalDate.parse(products.get(i).getBestBefore(), dtf);
                        LocalDate date2 = LocalDate.parse(products.get(i + 1).getBestBefore(), dtf);
                        if (date1.isBefore(date2)) {
                            logger.debug("[REMAINED] Name: {}; bestBefore: {}; Price: {}; Currency: {}",
                                    products.get(i).getName(), products.get(i).getBestBefore(),
                                    products.get(i).getPrice(), products.get(i).getCurrency());
                            logger.debug("[DELETED] Name: {}; bestBefore: {}; Price: {}; Currency: {}",
                                    products.get(i + 1).getName(), products.get(i + 1).getBestBefore(),
                                    products.get(i + 1).getPrice(), products.get(i + 1).getCurrency());
                            products.remove(i + 1);
                        } else {
                            logger.debug("[REMAINED] Name: {}; bestBefore: {}; Price: {}; Currency: {}",
                                    products.get(i + 1).getName(), products.get(i + 1).getBestBefore(),
                                    products.get(i + 1).getPrice(), products.get(i + 1).getCurrency());
                            logger.debug("[DELETED] Name: {}; bestBefore: {}; Price: {}; Currency: {}",
                                    products.get(i).getName(), products.get(i).getBestBefore(),
                                    products.get(i).getPrice(), products.get(i).getCurrency());
                            products.remove(i);
                        }
                    } catch (Exception e) {
                        logger.error("Unable to deduplicate data. Data format error: {}", e.getMessage());
                    }
                }
            }
        }
        logger.info("Aggregation completed");
        return products;
    }
}
