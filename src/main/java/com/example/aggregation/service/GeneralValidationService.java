package com.example.aggregation.service;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.IsoEra;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class GeneralValidationService {
    private final static Logger logger = LoggerFactory.getLogger(GeneralValidationService.class);
    public boolean validateName(@NotNull String name) {
        return !name.isEmpty();
    }

    /**
     * Checks that input date matches with input date format
     * @param date - input date (string)
     * @param dateFormat - input date format (dateTime)
     * @return bool
     */
    public boolean  validateDate(@NotNull String date, @NotNull String dateFormat)
    {
        boolean retVal = false;
        if (!date.isEmpty()) {
            try {
                DateTimeFormatter f = new DateTimeFormatterBuilder()
                        .appendPattern(dateFormat)
                        .parseDefaulting(ChronoField.ERA, IsoEra.CE.getValue())
                        .toFormatter()
                        .withResolverStyle(ResolverStyle.STRICT);
                LocalDate.parse(date, f);
                retVal = true;
            } catch (DateTimeParseException e) {
                logger.error("Date validation error: {}, date: {}, date format: {}", e.getMessage(), date, dateFormat);
            } catch (Exception e) {
                logger.error("DateTimeFormatterBuilder error: {}, date format: {}", e.getMessage(), dateFormat);
            }
        }
        return retVal;
    }

    /**
     * Validates input price with a reqular expression
     * @param price - input raw price (string)
     * @param regexp - validation rule, it depends on validation type from properties
     * @return bool
     */
    public boolean validatePrice(@NotNull String price, @NotNull String regexp) {
        boolean retVal = false;
        try {
            Pattern.compile(regexp);
            retVal = price.matches(regexp);
        }
        catch (PatternSyntaxException  e) {
            logger.error("Regular expression syntax is invalid: {}, regexp: {}, unable to validate price: {}",
                    e.getMessage(), regexp, price);
            throw e;
        }
        return retVal;
    }

    /**
     * Check the currency is supported by the application
     * @param currency - input currency
     * @param currencyList - supported currencies, /see currency properties
     * @return bool
     */
    public boolean validateCurrency(@NotNull String currency, @NotNull Set<String> currencyList) {
        return currencyList.contains(currency);
    }
}
