package com.lsit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OnlinePaymentResponseModel {
	
	    @JsonProperty("payment_mode")
	    private String paymentMode;

	    @JsonProperty("resp_message")
	    private String respMessage;

	    @JsonProperty("udf5")
	    private String udf5;

	    @JsonProperty("cust_email_id")
	    private String custEmailId;

	    @JsonProperty("udf6")
	    private String udf6;

	    @JsonProperty("udf3")
	    private String udf3;

	    @JsonProperty("recon_status")
	    private String reconStatus;

	    @JsonProperty("merchant_id")
	    private String merchantId;

	    @JsonProperty("txn_amount")
	    private String txnAmount;

	    @JsonProperty("udf4")
	    private String udf4;

	    @JsonProperty("udf1")
	    private String udf1;

	    @JsonProperty("udf2")
	    private String udf2;

	    @JsonProperty("pg_ref_id")
	    private String pgRefId;

	    @JsonProperty("txn_id")
	    private String txnId;

	    @JsonProperty("sur_charge")
	    private String surCharge;

	    @JsonProperty("resp_date_time")
	    private String respDateTime;

	    @JsonProperty("bank_ref_id")
	    private String bankRefId;

	    @JsonProperty("resp_code")
	    private String respCode;

	    @JsonProperty("txn_date_time")
	    private String txnDateTime;

	    @JsonProperty("trans_status")
	    private String transStatus;

	    @JsonProperty("cust_mobile_no")
	    private String custMobileNo;
	}


