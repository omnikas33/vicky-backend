package com.lsit.utils;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.lsit.entity.Bank;
import com.lsit.entity.CessEntry;
import com.lsit.entity.Department;
import com.lsit.entity.Payment;
import com.lsit.entity.PaymentUploads;
import com.lsit.entity.User;

public class CustomUUIDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		if (object instanceof User) {
			User user = (User) object;
			Department department = user.getDepartment();
			if (department != null && department.getDeptName() != null) {
				String userId = UUID.randomUUID().toString();
				return "USER-" + userId.substring(0, 8); // Example: USER-12345678
			} else {
				throw new IllegalArgumentException("Department or Department Name cannot be null for User");
			}
		} else if (object instanceof Department) {
			Department department = (Department) object;
			if (department.getDeptName() != null) {
				String deptId = UUID.randomUUID().toString();
				return "DEPT-" + deptId.substring(0, 8); // Example: DEPT-abcdefgh
			} else {
				throw new IllegalArgumentException("Department Name cannot be null for Department");
			}
		} else if (object instanceof Payment) {
			Payment payment = (Payment) object;
			// Customize payment IDs based on your requirements
			String paymentId = UUID.randomUUID().toString();
			return "PAY-" + paymentId.substring(0, 6); // Example: PAY-987654
		} else if (object instanceof CessEntry) {
			CessEntry cessEntry = (CessEntry) object;
			String cessId = UUID.randomUUID().toString();
			return "CESS-" + cessId.substring(0, 6); // Example: CESS-abcd12
		} else if (object instanceof Bank) {
			Bank bank = (Bank) object;
			// Customize bank IDs based on your requirements
			String bankId = UUID.randomUUID().toString();
			return "BANK-" + bankId.substring(0, 6); // Example: BANK-abcdef
		} else if (object instanceof PaymentUploads) {
			PaymentUploads paymentUploads = (PaymentUploads) object;
			// Customize payment uploads IDs based on your requirements
			String uploadId = UUID.randomUUID().toString();
			return "UPLD-" + uploadId.substring(0, 6); // Example: UPLD-abcdefgh
		} else {
			throw new IllegalArgumentException("Unsupported object type for UUID generation");
		}
	}
}
