package com.lsit.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_user")
public class User {

	@Id
	@GeneratedValue(generator = "custom-uuid")
	@GenericGenerator(name = "custom-uuid", strategy = "com.lsit.utils.CustomUUIDGenerator")
	@Column(name = "user_id", updatable = false, nullable = false)
	private String userId;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", nullable = false)
	@JsonBackReference
	private Department department;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Payment> payments;
}
