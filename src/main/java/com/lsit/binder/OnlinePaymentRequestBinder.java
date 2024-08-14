package com.lsit.binder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OnlinePaymentRequestBinder {
	private String merchantId;
	private String apiKey;
	private String txnId;
	private String amount;
	private String dateTime;
	private String custMail;
	private String custMobile;
	private String returnURL;
	private String productId = "DEFAULT";
//    private String type = "1.1"; // AES256 by default
	private String channelId;
	private String txnType;
	private String isMultiSettlement;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String instrumentId;
	private String cardDetails;
	private String cardType;

}
