package com.example.aggregation.service;

import com.example.aggregation.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
public class ParsePerformer {
    private final Map<String, Parser> parserMap;

    /**
     * Collect all the found parsers to the map
     * @param parsers - Parser interface implementation beans
     */
    @Autowired
    public ParsePerformer(@NotNull List<Parser> parsers) {
        parserMap = parsers.stream()
                .collect(toMap(Parser::getSource, Function.identity()));
    }

    /**
     * Parse the source's response using the appropriate type of parser
     * @param responseString - response from the source
     * @param source - id (url) to find the suitable parser
     * @return list of parsed products
     */
    public List<Product> parse(String responseString, String source) {
        Parser parser = parserMap.get(source);
        if (parser == null) {
            throw new UnsupportedOperationException("Source " + source + " is not supported yet!");
        }
        return parser.parse(responseString, source);
    }
}
