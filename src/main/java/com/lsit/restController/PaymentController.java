package com.lsit.restController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lsit.binder.PaymentRequestBinder;
import com.lsit.entity.Payment;
import com.lsit.model.PaymentReportDTO;
import com.lsit.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/daily")
	public ResponseEntity<?> getDailyPayments(@RequestParam("date") String date,
			@RequestParam("department") String department) {
		try {
			LocalDate localDate = LocalDate.parse(date);
			List<PaymentReportDTO> payments = paymentService.getDailyPayments(localDate, department);
			return ResponseEntity.ok(payments);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch daily payments");
		}
	}

	 @GetMapping("/monthly")
	    public ResponseEntity<List<PaymentReportDTO>> getMonthlyPayments(
	            @RequestParam int month, 
	            @RequestParam int year,
	            @RequestParam String departmentName) {
	        List<PaymentReportDTO> payments = paymentService.getMonthlyPayments(month, year, departmentName);
	        return ResponseEntity.ok(payments);
	    }

	@GetMapping("/department/{departmentName}")
	public ResponseEntity<?> getDepartmentWisePayments(@PathVariable String departmentName) {
		try {
			List<PaymentReportDTO> payments = paymentService.getDepartmentWisePayments(departmentName);
			return ResponseEntity.ok(payments);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to fetch department wise payments");
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Payment> createPayment(@RequestPart("paymentRequest") PaymentRequestBinder paymentRequest,
			@RequestPart("cessFile") MultipartFile cessFile,
			@RequestPart(value = "ddUpload", required = false) MultipartFile ddUpload,
			@RequestPart(value = "counterSlip", required = false) MultipartFile counterSlip) {
		try {
			Optional<Payment> result = paymentService.createPayment(paymentRequest, cessFile, ddUpload, counterSlip);
			if (result.isPresent()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

//	@GetMapping("/{paymentId}/challan-pdf")
//	public ResponseEntity<byte[]> generateChallanPdf(@PathVariable String paymentId) {
//		try {
//			byte[] pdfData = paymentService.generateChallanPdf(paymentId);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_PDF);
//			headers.setContentDispositionFormData("attachment", "PaymentChallan.pdf");
//			return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage().getBytes());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Failed to generate challan PDF".getBytes());
//		}
//	}

//	@GetMapping("/{paymentId}/send-challan")
//	public ResponseEntity<?> sendChallanPdfByEmail(@PathVariable String paymentId,
//			@RequestParam("email") String email) {
//		try {
//			paymentService.sendChallanPdf(paymentId, email);
//			return ResponseEntity.ok("Challan PDF sent successfully to " + email);
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send challan PDF");
//		}
//	}
}
