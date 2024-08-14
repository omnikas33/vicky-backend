package com.lsit.service;

import java.util.Optional;

import com.lsit.entity.User;

public interface UserService {

	Optional<User> findByUsernameAndPasswordAndDeptName(String userName, String password, String deptName);

//	void sendOtp(String email);

	boolean verifyOtp(String email, String otp);

	void sendOtp(String email, String token);

}
