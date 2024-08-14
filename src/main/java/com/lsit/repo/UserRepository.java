package com.lsit.repo;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lsit.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	@Query("SELECT u FROM User u JOIN u.department d WHERE u.userName = :userName AND d.deptName = :deptName")
	Optional<User> findByUsernameAndDeptName(@Param("userName") String userName, @Param("deptName") String deptName);

	Optional<User> findByUserName(String userName);
}
