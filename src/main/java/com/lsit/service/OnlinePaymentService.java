package com.lsit.service;

import org.springframework.stereotype.Service;

import com.lsit.utils.AESCipher;

@Service
public class OnlinePaymentService {

	 private static final String INIT_VECTOR = "Xa9PG6yr2cX3JH5O"; // Ensure this is 16 bytes long

	    public String encryptPaymentData(String jsonData, String key) {
	        try {
	            return AESCipher.encrypt(key, INIT_VECTOR, jsonData);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    
	    public String decryptPaymentData(String key, String encryptedData) {
	        try {
	            return AESCipher.decrypt(key, INIT_VECTOR, encryptedData);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
