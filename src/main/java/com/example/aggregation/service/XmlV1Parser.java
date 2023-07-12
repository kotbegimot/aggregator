package com.example.aggregation.service;

import com.example.aggregation.model.Product;
import com.example.aggregation.util.SourceProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XmlV1Parser implements Parser {
    private final static Logger logger = LoggerFactory.getLogger(XmlV1Parser.class);
    private final SourceProperties properties;

    /**
     * Parse xml ver.1 document
     * @param inputString is a response from the source
     * @param source is URL of the source
     * @return list of parsed product objects
     */
    @Override
    public List<Product> parse(String inputString, String source) {
        List<Product> productsList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(inputString));
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            String bestBefore, name, price, currency, id;
            bestBefore = name = price = currency = id = "";

            NodeList list = doc.getElementsByTagName("Product");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    bestBefore = element.getAttribute("BestBefore");
                    name = element.getElementsByTagName("Name").item(0).getTextContent();
                    price = element.getElementsByTagName("Price").item(0).getTextContent();
                    currency = element.getElementsByTagName("Currency").item(0).getTextContent();
                    Product product = new Product(name, price, currency, bestBefore, id, source);
                    productsList.add(product);
                }
            }
        } catch (Exception e) {
            logger.error("Parsing error: {}; Source: {}", e.getMessage(), source);
        }

        return productsList;
    }
    /**
     * Returns id for ParsePerformer
     * @return parser source id (url)
     */
    @Override
    public String getSource() {
        return properties.getSourceConfigs().get(properties.getSource1()).getUrl();
    }
}
