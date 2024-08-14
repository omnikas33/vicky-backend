package com.lsit.entity;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_department")
public class Department {

	@Id
	@GeneratedValue(generator = "custom-uuid")
	@GenericGenerator(name = "custom-uuid", strategy = "com.lsit.utils.CustomUUIDGenerator")
	@Column(name = "dept_id", updatable = false, nullable = false)
	private String deptId;

	@Column(name = "dept_name", nullable = false)
	private String deptName;

	@Column(name = "address")
	private String address;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<User> users;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Payment> payments;
}
