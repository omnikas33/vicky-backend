package com.lsit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsit.entity.PaymentUploads;

public interface PaymentUploadsRepository extends JpaRepository<PaymentUploads, String> {

}
