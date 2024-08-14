package com.lsit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsit.entity.Bank;
import com.lsit.repo.BankRepository;

@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;

	public List<Bank> getAllBanks() {
		return bankRepository.findAll();
	}
}
