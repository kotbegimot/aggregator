package com.example.aggregation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpRequestService {
    private final static Logger logger = LoggerFactory.getLogger(HttpRequestService.class);
    private String response = null;

    public String makeRequestPrivate(String urlStr, HttpURLConnection connection) {
        try {
            StringBuffer responseBuffer = new StringBuffer();
            BufferedReader in;
            String inputLine;
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // the request is successful, the response reading
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                in.close();
                response = responseBuffer.toString();
            } else {
                // to print an error if response code isn't equal 200, in this case we return empty response string
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                in.close();
                logger.error("Request {} failed, response code: {}, error: {}", urlStr, responseCode, responseBuffer);
            }
        } catch (Exception e) {
            logger.error("Request {} failed, exception: {}", urlStr, e.getMessage());
        }
        return response;
    }

    /**
     * Performs http GET request and returns it's response
     *
     * @param urlStr - URL
     * @return response string
     */
    public String getRequest(String urlStr) {
        String response = "";
        try {
            URL obj = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            response = makeRequestPrivate(urlStr, connection);
        } catch (Exception e) {
            logger.error("Request {} failed, exception: {}", urlStr, e.getMessage());
        }
        return response;
    }
}
