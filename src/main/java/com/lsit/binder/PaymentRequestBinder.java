package com.lsit.binder;

import lombok.Data;

@Data
public class PaymentRequestBinder {

	private String voucherNumber;
	private String deptContactNumber;
	private String payeeName;
	private String payeeEmail;
	private String transactionType;
	private String onlineTransactionType;
	private Boolean deduction;
	private Double grossTotalAmount;
	private Double cessAmount;
	private Double cessTransferAmount;
	private String deptName;
	private String userName;

	// Bank details
	private String branchName;
	private String branchAddress;
	private String ifsc;

}
