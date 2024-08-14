package com.lsit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsit.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, String> {

	List<Bank> findAll();

//	Optional<Bank> findByBankNameAndBranchNameAndIfsc(String bankName, String branchName, String ifsc);

//	List<Bank> findByBankNameAndBranchNameAndIfsc(String bankName, String branchName, String ifsc);
    List<Bank> findByBranchNameAndIfsc(String branchName, String ifsc);

}
