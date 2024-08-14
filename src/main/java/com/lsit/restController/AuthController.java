package com.lsit.restController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lsit.binder.LoginBinder;
import com.lsit.binder.OtpBinder;
import com.lsit.entity.User;
import com.lsit.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request; // Inject HttpServletRequest for session handling

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginBinder loginBinder) {
		String userName = loginBinder.getUserName();
		String password = loginBinder.getPassword();
		String departmentName = loginBinder.getDepartmentName();

		Optional<User> user = userService.findByUsernameAndPasswordAndDeptName(userName, password, departmentName);

		if (user.isPresent()) {
			// Generate a token and store it in the session
			String token = UUID.randomUUID().toString();
			HttpSession session = request.getSession();
			session.setAttribute("token", token);

			// Send OTP after successful authentication
			userService.sendOtp(user.get().getEmail(), token);

			// Return the token in the response body
			Map<String, String> response = new HashMap<>();
			response.put("message", "OTP sent to your email.");
			response.put("token", token);
			return ResponseEntity.ok(response);
		} else {
			logger.warn("Invalid login attempt for username: {}, department: {}", userName, departmentName);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Collections.singletonMap("message", "Invalid credentials."));
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpBinder otpBinder) {
		String otp = otpBinder.getOtp();
		String token = otpBinder.getToken();

		logger.debug("Verifying OTP for token: {}", token);

		// Verify the OTP
		if (userService.verifyOtp(token, otp)) {
			HttpSession session = request.getSession();
			session.setAttribute("authenticated", true);
			return ResponseEntity.ok("OTP verification successful. Login successful.");
		}

		logger.warn("OTP verification failed for token: {}", token);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
	}
}
