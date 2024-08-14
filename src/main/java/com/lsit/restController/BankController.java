package com.lsit.restController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lsit.entity.Bank;
import com.lsit.service.BankService;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("/branches")
    public List<Bank> getBranches() {
        return bankService.getAllBanks();
    }

    @GetMapping("/branch/address")
    public String getBranchAddress(@RequestParam("branchName") String branchName) {
        return bankService.getAllBanks().stream()
                .filter(bank -> bank.getBranchName().equals(branchName))
                .map(Bank::getBranchAddress)
                .findFirst()
                .orElse("Branch address not found");
    }

    @GetMapping("/branch/ifsc")
    public String getIFSC(@RequestParam("branchName") String branchName) {
        return bankService.getAllBanks().stream()
                .filter(bank -> bank.getBranchName().equals(branchName))
                .map(Bank::getIfsc)
                .findFirst()
                .orElse("IFSC code not found");
    }
}
