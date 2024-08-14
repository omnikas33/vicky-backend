package com.lsit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_requests")
public class OnlinePaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "txn_id", nullable = false, unique = true)
    private String txnId;

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "date_time", nullable = false)
    private String dateTime;

    @Column(name = "cust_mail")
    private String custMail;

    @Column(name = "cust_mobile")
    private String custMobile;

    @Column(name = "return_url")
    private String returnURL;

    @Column(name = "product_id", nullable = false)
    private String productId = "DEFAULT";

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "txn_type")
    private String txnType;

    @Column(name = "is_multi_settlement")
    private String isMultiSettlement;

    @Column(name = "udf1")
    private String udf1;

    @Column(name = "udf2")
    private String udf2;

    @Column(name = "udf3")
    private String udf3;

    @Column(name = "udf4")
    private String udf4;

    @Column(name = "udf5")
    private String udf5;

    @Column(name = "udf6")
    private String udf6;

    @Column(name = "instrument_id")
    private String instrumentId;

    @Column(name = "card_details")
    private String cardDetails;

    @Column(name = "card_type")
    private String cardType;

    // Constructors, getters, and setters can be omitted due to @Data annotation
}
