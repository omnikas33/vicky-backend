package com.lsit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.lsit.binder.PaymentRequestBinder;
import com.lsit.entity.Payment;
import com.lsit.model.PaymentReportDTO;

public interface PaymentService {

//	List<PaymentReportDTO> getDailyPayments(LocalDate date);

	List<PaymentReportDTO> getMonthlyPayments(int month, int year, String departmentName);

	List<PaymentReportDTO> getDepartmentWisePayments(String departmentName);

//	Optional<PaymentReportDTO> createPayment(PaymentRequestBinder paymentRequest);

//	byte[] generateChallanPdf(String paymentId);

//	void sendChallanPdf(String paymentId, String email);

//	Optional<PaymentReportDTO> createPayment(PaymentRequestBinder paymentRequest, MultipartFile cessFile);

	Optional<Payment> createPayment(PaymentRequestBinder paymentRequest, MultipartFile cessFile, MultipartFile ddUpload,
			MultipartFile counterSlip);

	List<PaymentReportDTO> getDailyPayments(LocalDate date, String departmentName);
}
