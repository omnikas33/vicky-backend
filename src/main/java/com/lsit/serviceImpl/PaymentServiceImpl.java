package com.lsit.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lsit.binder.PaymentRequestBinder;
import com.lsit.entity.Bank;
import com.lsit.entity.Department;
import com.lsit.entity.Payment;
import com.lsit.entity.PaymentUploads;
import com.lsit.entity.User;
import com.lsit.model.PaymentReportDTO;
import com.lsit.repo.BankRepository;
import com.lsit.repo.DepartmentRepository;
import com.lsit.repo.PaymentRepository;
import com.lsit.repo.PaymentUploadsRepository;
import com.lsit.repo.UserRepository;
import com.lsit.service.CessEntryService;
import com.lsit.service.PaymentService;
import com.lsit.utils.EmailService;
import com.lsit.utils.PdfGenerator;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CessEntryService cessEntryService;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private PaymentUploadsRepository uploadsRepository;

	@Override
	@Transactional
	public Optional<Payment> createPayment(PaymentRequestBinder paymentRequest, MultipartFile cessFile,
			MultipartFile ddUpload, MultipartFile counterSlip) {
		logger.info("Creating payment for user: {} in department: {}", paymentRequest.getUserName(),
				paymentRequest.getDeptName());

		// Retrieve user and department
		Optional<User> userOpt = userRepository.findByUserName(paymentRequest.getUserName());
		Optional<Department> deptOpt = departmentRepository.findByDeptName(paymentRequest.getDeptName());

		if (userOpt.isPresent() && deptOpt.isPresent()) {
			Payment payment = new Payment();
			payment.setVoucherNumber(paymentRequest.getVoucherNumber());
			payment.setDeptContactNumber(paymentRequest.getDeptContactNumber());
			payment.setPayeeName(paymentRequest.getPayeeName());
			payment.setPayeeEmail(paymentRequest.getPayeeEmail());
			payment.setTransactionType(paymentRequest.getTransactionType());
			payment.setDeduction(paymentRequest.getDeduction());
			payment.setGrossTotalAmount(paymentRequest.getGrossTotalAmount());
			payment.setCessAmount(paymentRequest.getCessAmount());
			payment.setCessTransferAmount(paymentRequest.getCessTransferAmount());
			payment.setUser(userOpt.get());
			payment.setDepartment(deptOpt.get());
			payment.setOnlineTransactionType(paymentRequest.getOnlineTransactionType());

			// Check if bank data already exists
			Bank bank = null;
			try {
				List<Bank> banks = bankRepository.findByBranchNameAndIfsc(paymentRequest.getBranchName(),
						paymentRequest.getIfsc());

				if (!banks.isEmpty()) {
					// Get the first result or handle multiple results as needed
					bank = banks.get(0);
				} else {
					// Optionally create a new bank record if needed
					logger.warn("Bank data not found. Consider creating a new record if needed.");
				}
			} catch (Exception e) {
				// Handle the exception and log the error
				logger.error("Error checking bank data: {}", e.getMessage());
				throw new RuntimeException("Failed to check bank data: " + e.getMessage());
			}

			// Associate the Bank entity with the Payment
			payment.setBank(bank);

			// Save the payment
			Payment savedPayment;
			try {
				savedPayment = paymentRepository.save(payment);
				logger.info("Payment created successfully with ID: {}", savedPayment.getPaymentId());
			} catch (Exception e) {
				logger.error("Error saving payment: {}", e.getMessage());
				throw new RuntimeException("Failed to save payment: " + e.getMessage());
			}

			// Save the PaymentUploads data only if necessary
			if ((ddUpload != null && !ddUpload.isEmpty()) || (counterSlip != null && !counterSlip.isEmpty())) {
				PaymentUploads paymentUploads = new PaymentUploads();
				paymentUploads.setPayment(savedPayment);

				try {
					if (ddUpload != null && !ddUpload.isEmpty()) {
						paymentUploads.setDdUpload(ddUpload.getBytes());
					}
					if (counterSlip != null && !counterSlip.isEmpty()) {
						paymentUploads.setCounterSlip(counterSlip.getBytes());
					}
				} catch (IOException e) {
					logger.error("Error processing payment uploads: {}", e.getMessage());
					throw new RuntimeException("Failed to process payment uploads: " + e.getMessage());
				}

				try {
					uploadsRepository.save(paymentUploads); // Save the PaymentUploads entity
					logger.info("Payment uploads saved successfully for payment ID: {}", savedPayment.getPaymentId());
				} catch (Exception e) {
					logger.error("Error saving payment uploads: {}", e.getMessage());
					throw new RuntimeException("Failed to save payment uploads: " + e.getMessage());
				}
			} else {
				logger.info("No valid uploads found. Skipping creation of PaymentUploads record.");
			}

			// Process the Cess Entry file
			try {
				cessEntryService.processExcelFile(cessFile, savedPayment.getPaymentId());
			} catch (IOException e) {
				logger.error("Error processing Cess Entry file: {}", e.getMessage());
				throw new RuntimeException("Failed to process Cess Entry file: " + e.getMessage());
			}

			return Optional.of(savedPayment);
		} else {
			logger.error("Failed to create payment. User or department not found.");
			return Optional.empty();
		}
	}

	/*
	 * @Override public byte[] generateChallanPdf(String paymentId) {
	 * logger.info("Generating challan PDF for payment ID: {}", paymentId);
	 * Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
	 * 
	 * if (paymentOpt.isPresent()) { Payment payment = paymentOpt.get(); return
	 * pdfGenerator.generatePdf(payment); } else {
	 * logger.error("Failed to generate challan PDF. Payment ID {} not found.",
	 * paymentId); throw new IllegalArgumentException("Invalid payment ID: " +
	 * paymentId); } }
	 */

	/*
	 * @Override public void sendChallanPdf(String paymentId, String email) {
	 * logger.info("Sending challan PDF to payee email for payment ID: {}",
	 * paymentId); Optional<Payment> paymentOpt =
	 * paymentRepository.findById(paymentId);
	 * 
	 * if (paymentOpt.isPresent()) { Payment payment = paymentOpt.get(); byte[]
	 * pdfData = generateChallanPdf(paymentId);
	 * emailService.sendPaymentChallanEmail(payment.getPayeeEmail(), pdfData);
	 * logger.info("Challan PDF sent successfully to email: {}",
	 * payment.getPayeeEmail()); } else {
	 * logger.error("Failed to send challan PDF. Payment ID {} not found.",
	 * paymentId); throw new IllegalArgumentException("Invalid payment ID: " +
	 * paymentId); } }
	 */

	// Updated method to fetch department-wise payments
	public List<PaymentReportDTO> getDepartmentWisePayments(String departmentName) {
		logger.debug("Fetching department-wise payments for department: {}", departmentName);

		// Fetch department by name
		Optional<Department> departmentOptional = departmentRepository.findByDeptName(departmentName);

		// Use standard exception if department is not found
		Department department = departmentOptional
				.orElseThrow(() -> new IllegalArgumentException("Department not found with name: " + departmentName));

		String deptId = department.getDeptId();
		logger.debug("Fetched department ID: {}", deptId);

		// Fetch payments based on department ID
		List<Payment> payments = paymentRepository.findByDepartment_DeptId(deptId);

		if (payments.isEmpty()) {
			logger.info("No payments found for department ID: {}", deptId);
		} else {
			logger.info("Found {} payments for department ID: {}", payments.size(), deptId);
		}

		return payments.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// Updated method to fetch daily payments
	public List<PaymentReportDTO> getDailyPayments(LocalDate date, String departmentName) {
		logger.debug("Fetching daily payments for date: {} and department: {}", date, departmentName);

		// Convert LocalDate to LocalDateTime for the start and end of the day
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

		// Fetch department by name
		Department department = departmentRepository.findByDeptName(departmentName)
				.orElseThrow(() -> new IllegalArgumentException("Department not found with name: " + departmentName));

		String deptId = department.getDeptId();
		logger.debug("Fetched department ID: {}", deptId);

		// Fetch payments based on department ID and date range
		List<Payment> payments = paymentRepository.findByPaymentDateAndDepartment_DeptId(date, deptId);

		if (payments.isEmpty()) {
			logger.info("No payments found for date: {} and department ID: {}", date, deptId);
		} else {
			logger.info("Found {} payments for date: {} and department ID: {}", payments.size(), date, deptId);
		}

		return payments.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<PaymentReportDTO> getMonthlyPayments(int month, int year, String departmentName) {
		logger.debug("Fetching monthly payments for month: {}, year: {}, and department: {}", month, year,
				departmentName);

		// Fetch department by name
		Department department = departmentRepository.findByDeptName(departmentName)
				.orElseThrow(() -> new IllegalArgumentException("Department not found with name: " + departmentName));

		String deptId = department.getDeptId();
		logger.debug("Fetched department ID: {}", deptId);

		// Fetch payments based on department ID
		List<Payment> payments = paymentRepository.findPaymentsByDepartmentId(deptId);

		// Filter payments by the requested month and year
		List<Payment> filteredPayments = payments.stream().filter(payment -> {
			LocalDate paymentDate = payment.getPaymentDate();
			return paymentDate.getMonthValue() == month && paymentDate.getYear() == year;
		}).collect(Collectors.toList());

		if (filteredPayments.isEmpty()) {
			logger.info("No payments found for month: {}, year: {}, and department ID: {}", month, year, deptId);
		} else {
			logger.info("Found {} payments for month: {}, year: {}, and department ID: {}", filteredPayments.size(),
					month, year, deptId);
		}

		// Convert entities to DTOs
		return filteredPayments.stream().map(this::convertToDTO).collect(Collectors.toList());
	}




	private PaymentReportDTO convertToDTO(Payment payment) {
		logger.debug("Converting Payment entity to DTO: {}", payment);

		PaymentReportDTO dto = new PaymentReportDTO();
		dto.setTransactionId(payment.getTransactionId());
		dto.setReferenceId(payment.getReferenceId());
		dto.setPayeeName(payment.getPayeeName());
		dto.setPayeeEmail(payment.getPayeeEmail());
		dto.setCessAmount(payment.getCessAmount());
		dto.setDeduction(payment.getDeduction());
		dto.setCessTransferAmount(payment.getCessTransferAmount());
		dto.setGrossTotalAmount(payment.getGrossTotalAmount()); // Assuming this is the correct field
		dto.setVoucherNumber(payment.getVoucherNumber());
		dto.setOnlineTransactionType(payment.getOnlineTransactionType());

		// Set formatted date
		LocalDate paymentDate = payment.getPaymentDate();
		if (paymentDate != null) {
			String formattedDate = paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			dto.setPaymentDate(formattedDate);
		} else {
			logger.warn("Payment date is null for Payment ID: {}", payment.getTransactionId());
		}

		dto.setTransactionType(payment.getTransactionType());

		if (payment.getUser() != null) {
			dto.setUserName(payment.getUser().getUserName());
		} else {
			logger.warn("No associated User entity for Payment ID: {}", payment.getTransactionId());
		}

		if (payment.getDepartment() != null) {
			dto.setDepartmentName(payment.getDepartment().getDeptName());
		} else {
			logger.warn("No associated Department entity for Payment ID: {}", payment.getTransactionId());
		}

		if (payment.getBank() != null) {
			dto.setBranchCode(payment.getBank().getBranchCode()); // Updated field
			logger.debug("Branch code set: {}", payment.getBank().getBranchCode());
		} else {
			logger.debug("No associated Bank entity for Payment ID: {}", payment.getTransactionId());
		}

		if (payment.getPaymentUploads() != null) {
			dto.setUploadId(payment.getPaymentUploads().getUploadId());
			dto.setUploadDate(payment.getPaymentUploads().getUploadDate());
			logger.debug("Upload ID set: {}", payment.getPaymentUploads().getUploadId());
		} else {
			logger.debug("No associated PaymentUploads entity for Payment ID: {}", payment.getTransactionId());
		}

		logger.debug("Converted DTO: {}", dto);
		return dto;
	}
}


