package com.lsit.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReportDTO {
    private String transactionId;
    private String referenceId;
    private String payeeName;
    private String payeeEmail;
    private String paymentDate; // Assuming this is a formatted string, change to LocalDate if necessary
    private Double cessAmount;
	private String voucherNumber;
    private Boolean deduction;
    private Double cessTransferAmount;
    private Double grossTotalAmount;
    private String userName;
    private String departmentName;
    private String uploadId; // Add this field
    private String branchCode; // Updated field
    private Date uploadDate;
    private String transactionType;
	private String onlineTransactionType;

}
