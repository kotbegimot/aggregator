package com.example.aggregation.service;

import com.example.aggregation.model.CurrencyContainer;
import com.example.aggregation.util.CurrencyProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final static Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final HttpRequestService requestService;
    private final CurrencyProperties properties;
    private final CurrencyContainer currencyContainer;
    public void fillCurrencyContainer() {
        for (String currency : currencyContainer.getData().keySet()) {
            setCurrencyConversion(currency);
        }
    }

    /**
     * Gets a conversion rate for the currency and put it into currency container
     * @param currency
     */
    private void setCurrencyConversion(String currency) {
        if (currency.equals(properties.getOutputCurrency())) {
            // coefficient for output currency should be 1
            currencyContainer.getData().put(currency, 1.0f);
        } else {
            String source = String.format("%s?from=%s&to=EUR", properties.getSource(), currency);
            String response = requestService.getRequest(source);
            if (response != null && !response.isEmpty()) {
                try {
                    Float conversion = Float.parseFloat(response);
                    currencyContainer.getData().put(currency, conversion);
                } catch (Exception e) {
                    logger.error("Unable to add a conversion rate of the currency: {}, response: {}, error: {}", currency, response, e.getMessage());
                }
            } else {
                logger.error("Unable to add a conversion rate of the currency: {}, response is empty", currency);
            }
        }
    }
}
