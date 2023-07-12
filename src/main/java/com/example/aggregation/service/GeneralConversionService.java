package com.example.aggregation.service;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class GeneralConversionService {
    private final static Logger logger = LoggerFactory.getLogger(GeneralConversionService.class);

    /**
     * Get a price of certain format (float) from input string
     * @param rawPrice - string for parsing
     * @param regExp - rule for parsing, depends on validation type
     * @return
     */
    public String parsePrice(@NotNull String rawPrice, @NotNull String regExp) {
        String price = rawPrice;
        try {
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(rawPrice);
            if (matcher.find()) {
                price = matcher.group(2) + matcher.group(3);
            }
        }
        catch (PatternSyntaxException e) {
            logger.error("Regular expression syntax is invalid: {}, regexp: {}, unable to validate price: {}",
                    e.getMessage(), regExp, price);
            throw e;
        }
        return price;
    }

    /**
     * Converts price value using currency rate
     * @param inputPrice - price value with input currency
     * @param coefficient - conversion rate between input and output currency
     * @return converted price value to output currency
     */
    public String convertPrice(@NotNull String inputPrice, float coefficient) {
        String outputPrice = "";
        try {
            float priceFloat = Float.parseFloat(inputPrice.replace(",", "."));
            var curr_eur = priceFloat * coefficient;
            outputPrice = String.valueOf(curr_eur);
        }
        catch (Exception e) {
            logger.error("Price is invalid! Exception: {}; Price: {}", e.getMessage(), inputPrice);
            throw e;
        }
        return outputPrice;
    }

    /**
     * Converts input date format to output date format
     * @param inDate - input date (string)
     * @param inputDateTimeFormat - input date format (dateTime)
     * @param outputDateTimeFormat - output date format (dateTime)
     * @return date converted to output date format
     */
    public String convertDate(@NotNull String inDate,  @NotNull String inputDateTimeFormat, @NotNull String outputDateTimeFormat) {
        String convertedDate = inDate;
        try {
            DateTimeFormatter fIn = DateTimeFormatter.ofPattern(inputDateTimeFormat, Locale.ENGLISH);
            LocalDate ld = LocalDate.parse(inDate, fIn);
            DateTimeFormatter fOut = DateTimeFormatter.ofPattern(outputDateTimeFormat, Locale.ENGLISH);
            convertedDate = ld.format(fOut);
        } catch (Exception e) {
            logger.error("Price is invalid! Exception: {}; Date: {}; In DataFormat: {}; Out DataFormat: {};",
                    e.getMessage(), inDate, inputDateTimeFormat, outputDateTimeFormat);
            throw e;
        }
        return convertedDate;
    }
}
