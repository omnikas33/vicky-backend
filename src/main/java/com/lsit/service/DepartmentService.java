package com.lsit.service;

import java.util.List;
import java.util.Optional;

import com.lsit.entity.Department;

public interface DepartmentService {
	Optional<Department> findByDeptName(String deptName);

	List<Department> findAllDepartments();

}
