package com.lsit.serviceImpl;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lsit.entity.User;
import com.lsit.repo.UserRepository;
import com.lsit.service.UserService;
import com.lsit.utils.EmailService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final int OTP_VALID_DURATION = 5; // OTP validity duration in minutes
	private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Long> otpTimestamp = new ConcurrentHashMap<>();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Override
	public Optional<User> findByUsernameAndPasswordAndDeptName(String userName, String password, String deptName) {
		Optional<User> userOptional = userRepository.findByUsernameAndDeptName(userName, deptName);

		if (userOptional.isPresent()) {
			User user = userOptional.get();

			// Retrieve the stored encoded password
			String storedPassword = user.getPassword();

			// Use passwordEncoder to match the plain text password with the encoded
			// password
			if (passwordEncoder.matches(password, storedPassword)) {
				// Passwords match, return the authenticated user
				return Optional.of(user);
			} else {
				// Passwords do not match
				logger.warn("Invalid login attempt for username: {}", userName);
				return Optional.empty();
			}
		} else {
			// User not found for given username and department
			logger.warn("User not found for username: {} and department: {}", userName, deptName);
			return Optional.empty();
		}
	}

	@Override
	public void sendOtp(String email, String token) {
		String otp = generateOtp();
		otpStorage.put(token, otp);
		otpTimestamp.put(token, System.currentTimeMillis());

		// Send OTP to the user's email
		emailService.sendOtpEmail(email, otp);

		logger.info("OTP sent to email: {}", email);
	}

	@Override
	public boolean verifyOtp(String token, String otp) {
		logger.debug("Verifying OTP for token: {}", token);

		String storedOtp = otpStorage.get(token);
		Long timestamp = otpTimestamp.get(token);

		if (storedOtp == null || timestamp == null) {
			logger.warn("OTP verification failed for token: {}. No OTP found.", token);
			return false;
		}

		// Check if the OTP is valid and not expired
		if (storedOtp.equals(otp)
				&& (System.currentTimeMillis() - timestamp) <= TimeUnit.MINUTES.toMillis(OTP_VALID_DURATION)) {
			otpStorage.remove(token);
			otpTimestamp.remove(token);
			logger.info("OTP verified successfully for token: {}", token);
			return true;
		} else {
			logger.warn("OTP verification failed for token: {}. Invalid or expired OTP.", token);
			return false;
		}
	}

	private String generateOtp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}
}
