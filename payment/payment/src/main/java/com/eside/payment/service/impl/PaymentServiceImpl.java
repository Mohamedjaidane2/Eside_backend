package com.eside.payment.service.impl;

import com.eside.payment.dto.Flouci.ResponsePayment;
import com.eside.payment.service.PaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${flouci.app_token}")
    private String appToken;

    @Value("${flouci.app_secret}")
    private String appSecret;

    @Value("${flouci.developer_tracking_id}")
    private String developerTrackingId;

    @Override
    public ResponsePayment generatePayment(Integer amount) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Defining the media type
        ContentType contentType = ContentType.APPLICATION_JSON;

        // Creating a HashMap to store request data
        HashMap<String, Object> requestHashMap = new HashMap<>();
        requestHashMap.put("app_token", appToken);
        requestHashMap.put("app_secret", appSecret);
        requestHashMap.put("accept_card", "true");
        requestHashMap.put("amount", String.valueOf(amount));
        requestHashMap.put("success_link", "http://localhost:8080/payment/success");
        requestHashMap.put("fail_link", "http://localhost:8080/payment/error");
        requestHashMap.put("session_timeout_secs", 1200);
        requestHashMap.put("developer_tracking_id", developerTrackingId);

        // Convert the HashMap to JSON using ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestHashMap);

        // Creating the request body with the JSON
        StringEntity entity = new StringEntity(json, contentType);

        // Creating the POST request with the Flouci API URL, request body, and appropriate headers
        HttpPost request = new HttpPost("https://developers.flouci.com/api/generate_payment");
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");

        // Executing the request
        CloseableHttpResponse response = client.execute(request);

        // Handling the response
        try {
            // Checking for successful response
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Unexpected code " + response.getStatusLine());
            }

            // Processing the JSON response
            HttpEntity responseEntity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getContent());

            // Constructing the ResponsePayment object from JSON response data
            ResponsePayment responsePayment = ResponsePayment.builder()
                    .payment_id(jsonNode.path("result").path("payment_id").asText()) // Payment ID
                    .link(jsonNode.path("result").path("link").asText()) // Payment link
                    .build();

            // Returning the ResponsePayment object
            return responsePayment;
        } finally {
            response.close();
            client.close();
        }
    }

    @Override
    public boolean verifyPayment(String paymentId) throws IOException {
        String apiUrl = "https://developers.flouci.com/api/verify_payment/" + paymentId;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);
        request.setHeader("apppublic", appToken);
        request.setHeader("appsecret", appSecret);

        try (CloseableHttpResponse response = client.execute(request)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
                String statusPayment = jsonNode.path("result").path("status").asText();
                return statusPayment.equals("SUCCESS");
            } else {
                System.err.println("Erreur lors de la vérification du paiement. Code de statut : " + response.getStatusLine().getStatusCode());
                return false;
            }
        }
    }
}
