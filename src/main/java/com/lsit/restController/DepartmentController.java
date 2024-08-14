package com.lsit.restController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lsit.entity.Department;
import com.lsit.model.DepartmentDTO;
import com.lsit.service.DepartmentService;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<Department> departments = departmentService.findAllDepartments();
            
            // Check if the list is empty and return appropriate response
            if (departments.isEmpty()) {
                return new ResponseEntity<>("No departments found", HttpStatus.NO_CONTENT);
            }

            // Map entities to DTOs
            List<DepartmentDTO> departmentDTOs = departments.stream()
                .map(department -> {
                    DepartmentDTO dto = new DepartmentDTO();
                    dto.setDeptName(department.getDeptName());
                    return dto;
                })
                .collect(Collectors.toList());

            return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching departments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
