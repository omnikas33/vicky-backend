package com.lsit.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_payment")
public class Payment {

	@Id
	@GeneratedValue(generator = "custom-uuid")
	@GenericGenerator(name = "custom-uuid", strategy = "com.lsit.utils.CustomUUIDGenerator")
	@Column(name = "payment_id", nullable = false, updatable = false)
	private String paymentId;

	@Column(name = "transaction_id", nullable = false, updatable = false)
	private String transactionId;

	@Column(name = "reference_id", nullable = false, updatable = false)
	private String referenceId;

	@Column(name = "dept_voucher_number")
	private String voucherNumber;
	
	@Column(name = "dept_contact_number", nullable = false)
	private String deptContactNumber;

	@Column(name = "payee_name", nullable = false)
	private String payeeName;

	@Column(name = "payee_email", nullable = false)
	private String payeeEmail;

	@Column(name = "transaction_type", nullable = false)
	private String transactionType;

	@Column(name = "online_transaction_type", nullable=true)
	private String onlineTransactionType;
	
	@CreationTimestamp
	@Column(name = "payment_date", nullable = false)
	private LocalDate paymentDate;

	@Column(name = "deduction", nullable = false)
	private Boolean deduction;

	@Column(name = "gross_total_amount", nullable = false)
	private Double grossTotalAmount;

	@Column(name = "cess_amount", nullable = false)
	private Double cessAmount;

	@Column(name = "dbocw_cess_transfer_after_deduction_if_any", nullable = false)
	private Double cessTransferAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Department department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	@JsonManagedReference
	private Bank bank;

	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<CessEntry> cessEntries;

	@OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private PaymentUploads paymentUploads;

	@PrePersist
	public void prePersist() {
	    if (this.transactionId == null) {
	        this.transactionId = "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
	    }
	    if (this.referenceId == null) {
	        this.referenceId = "REF" + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
	    }
	}

}
