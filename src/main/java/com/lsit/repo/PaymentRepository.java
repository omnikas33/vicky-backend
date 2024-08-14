package com.lsit.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lsit.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

	// Method to find payments between two LocalDateTime values
	List<Payment> findByPaymentDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

	// Method to find payments by department name
	List<Payment> findByDepartment_DeptName(String departmentName);

	// Method to find payments between two LocalDateTime values and by department
	// name
	List<Payment> findByPaymentDateBetweenAndDepartment_DeptName(LocalDateTime startDateTime, LocalDateTime endDateTime,
			String departmentName);

	@Query("SELECT p FROM Payment p WHERE p.department.deptId = :deptId")
	List<Payment> findPaymentsByDepartmentId(@Param("deptId") String deptId);

	// Method to find payments by month, year, and department name
	@Query("SELECT p FROM Payment p WHERE FUNCTION('MONTH', p.paymentDate) = :month "
			+ "AND FUNCTION('YEAR', p.paymentDate) = :year " + "AND p.department.deptName = :departmentName")
	List<Payment> findPaymentsByMonthYearAndDepartment(@Param("month") int month, @Param("year") int year,
			@Param("departmentName") String departmentName);

	// Method to find payments by department ID (String type)
	List<Payment> findByDepartment_DeptId(String deptId);

	// Repository method to find payments by date and department ID
	List<Payment> findByPaymentDateAndDepartment_DeptId(LocalDate paymentDate, String deptId);

	// Method to find payments by date range and department ID (String type)
	@Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startOfMonthDateTime AND :endOfMonthDateTime "
			+ "AND p.department.deptId = :deptId")
	List<Payment> findPaymentsByDateRangeAndDepartment(@Param("startOfMonthDateTime") LocalDate startOfMonthDateTime,
			@Param("endOfMonthDateTime") LocalDate endOfMonthDateTime, @Param("deptId") String deptId);

//	@Query("SELECT p FROM Payment p WHERE p.departmentName = :departmentName")
//	List<Payment> findPaymentsByDepartmentName(@Param("departmentName") String departmentName);
}
