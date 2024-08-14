package com.lsit.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsit.entity.Department;
import com.lsit.repo.DepartmentRepository;
import com.lsit.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public Optional<Department> findByDeptName(String deptName) {
		return departmentRepository.findByDeptName(deptName);
	}

	@Override
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}
}
