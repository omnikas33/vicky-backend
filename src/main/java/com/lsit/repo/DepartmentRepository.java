package com.lsit.repo;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsit.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Serializable> {

	Optional<Department> findByDeptName(String deptName);
}
