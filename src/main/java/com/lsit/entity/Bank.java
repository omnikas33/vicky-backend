package com.lsit.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_bank")
public class Bank {

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.lsit.utils.CustomUUIDGenerator")
    @Column(name = "bank_id", nullable = false, updatable = false)
    private String bankId;

    @Column(name = "branch_code", nullable = false)
    private String branchCode;

    @Column(name = "branch_name", nullable = false)
    private String branchName;
    
    @Column(name = "zone")
    private String zone;

    @Column(name = "ifsc", nullable = false)
    private String ifsc;
    
    @Column(name = "branch_address")
    private String branchAddress;

    @OneToMany(mappedBy = "bank")
    @JsonBackReference
    private List<Payment> payments;
}
