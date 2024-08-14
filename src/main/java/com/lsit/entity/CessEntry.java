package com.lsit.entity;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_cess")
public class CessEntry {

	@Id
	@GeneratedValue(generator = "custom-uuid")
	@GenericGenerator(name = "custom-uuid", strategy = "com.lsit.utils.CustomUUIDGenerator")
	@Column(name = "cess_id", updatable = false, nullable = false)
	private String cessId;

	@Column(name = "Sr_no", nullable = false)
	private String SrNo;

	@Column(name = "dept_voucher_number", nullable = false)
	private String deptVoucherNumber;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "name_of_agency", nullable = false)
	private String agencyName;

	@Column(name = "address_of_agency", nullable = false)
	private String agencyAddress;

	@Column(name = "site_address", nullable = false)
	private String siteAddress;

	@Column(name = "total_project", nullable = false)
	private String totalProject;

	@Column(name = "bill_value", nullable=false)
	private Double billValue;

	@Column(name = "cess_collection", nullable= false)
	private Double cessCollection;
	
	@Column(name = "dbocw_cess_transfer_after_deduction_if_any", nullable=false)
	private Double cessTransfer;
	
	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = false)
	@JsonBackReference
	private Payment payment;

	// Add getter for paymentId
	public String getPaymentId() {
		return payment != null ? payment.getPaymentId() : null;
	}
}
