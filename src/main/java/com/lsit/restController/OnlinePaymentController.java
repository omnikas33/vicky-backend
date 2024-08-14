package com.lsit.restController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsit.binder.OnlinePaymentRequestBinder;
import com.lsit.model.OnlinePaymentResponseModel;
import com.lsit.service.OnlinePaymentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/online")
public class OnlinePaymentController {

    private static final Logger logger = LoggerFactory.getLogger(OnlinePaymentController.class);

    @Autowired
    private OnlinePaymentService onlinePaymentService;

   

    @PostMapping("/paymentRequest")
    public Map<String, Object> processPayment(@RequestBody OnlinePaymentRequestBinder paymentRequest) throws Exception {
        Map<String, Object> response = new HashMap<>();

        // Set additional parameters if needed
        paymentRequest.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Convert PaymentRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestData = objectMapper.writeValueAsString(paymentRequest);

        logger.info("Payment Request Data: Json  " + jsonRequestData);

        // Encrypt JSON data using AES with initialization vector
        String apiKey = paymentRequest.getApiKey();// Ensure this key is 32 bytes long for AES-256
        String encryptedData = onlinePaymentService.encryptPaymentData(jsonRequestData, apiKey);

        logger.info("Payment Request Data: Encrypted " + encryptedData);

        // Add response data
        response.put("encryptedData", encryptedData);
        response.put("paymentRequest", paymentRequest);

        return response;
    }

    @PostMapping("/paymentResponse")
    public Map<String, Object> paymentResponse(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Extract fields from request
            Map<String, String> fields = extractFieldsFromRequest(request);

            logger.info("Payment Response Data: Raw Request Parameters: " + fields);

            String message = null;
            String statusIcon = "fa-circle-xmark";
            String statusColor = "#fd4b6b";

            // Check if there's an error
            if (fields.containsKey("txnErrorMsg")) {
                // Handle error scenario
                String errorString = fields.get("txnErrorMsg");
                String errorCode = GetTextBetweenTags(errorString, "<error_code>", "</error_code>");
                String errorMsg = GetTextBetweenTags(errorString, "<error_msg>", "</error_msg>");

                message = "Error " + errorCode + " : " + errorMsg;
            } else {
                // Handle successful transaction response
                String encResp = fields.get("respData");
                if (encResp != null) {
                    String apiKey = "IUGFFHJGGGHJKKJKMJMLML"; // Replace with your actual API key

                    // Decrypt the data
                    String decryptedData = onlinePaymentService.decryptPaymentData(apiKey, encResp);
                    logger.info("Payment Response Data: Decrypted : " + decryptedData);

                    // Parse the decrypted JSON to a PaymentResponse object
                    ObjectMapper objectMapper = new ObjectMapper();
                    OnlinePaymentResponseModel paymentResponse = objectMapper.readValue(decryptedData, OnlinePaymentResponseModel.class);

                    // Determine the message and status based on the response
                    if ("Ok".equalsIgnoreCase(paymentResponse.getTransStatus())) {
                        message = "Transaction Successful";
                        statusIcon = "fa-badge-check";
                        statusColor = "#0989ff";
                    } else if ("To".equalsIgnoreCase(paymentResponse.getTransStatus())) {
                        message = "Sorry!! Your Transaction is Timed Out";
                    } else {
                        message = "Transaction Failed";
                    }

                    // Add the PaymentResponse object to the response
                    response.put("paymentResponse", paymentResponse);
                }
            }

            // Add status and message to the response
            response.put("statusIcon", statusIcon);
            response.put("statusColor", statusColor);
            response.put("message", message);

        } catch (Exception e) {
            logger.error("Error processing payment response.", e);
            response.put("error", "Error processing payment response.");
        }

        return response;
    }

    // Helper method to extract fields from request
    private Map<String, String> extractFieldsFromRequest(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
            }
        }
        return fields;
    }

    // Helper method to get text between tags
    private String GetTextBetweenTags(String inputText, String tag1, String tag2) {
        String result = "NA";
        int index1 = inputText.indexOf(tag1);
        int index2 = inputText.indexOf(tag2);
        if (index1 != -1 && index2 != -1) {
            index1 = index1 + tag1.length();
            result = inputText.substring(index1, index2);
        }
        return result;
    }
}
