package com.lsit.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("lodhasit2024@gmail.com");
		mailSender.setPassword("wmmg kbyy sxye nrwe");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.connectiontimeout", "10000");
		props.put("mail.smtp.timeout", "10000");
		props.put("mail.smtp.writetimeout", "10000");

		return mailSender;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowCredentials(true);
	    configuration.addAllowedOrigin("http://localhost:8443"); // Your client URL
	    configuration.addAllowedHeader("*"); // Allow all headers
	    configuration.addAllowedMethod("*"); // Allow all methods

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
	            .requestMatchers("/api/departments/all").permitAll()
	            .requestMatchers("/api/auth/verify-otp").permitAll()
	            .requestMatchers("/api/auth/login").permitAll()
	            .requestMatchers("/api/cess/upload").permitAll()
	            .requestMatchers("/api/cess/entries").permitAll()
	            .requestMatchers("/api/payments/create").permitAll()
	            .requestMatchers("/api/payments/daily").permitAll()
	            .requestMatchers("/api/payments/monthly").permitAll()
	            .requestMatchers("/api/payments/department/{departmentName}").permitAll()
	            .requestMatchers("/api/banks/branches").permitAll()
	            .requestMatchers("/api/banks/names").permitAll()
	            .requestMatchers("/api/online/paymentRequest").permitAll()
	            .requestMatchers("/api/online/paymentResponse").permitAll()
	            .requestMatchers("https://pa-preprod.1pay.in/payment/payprocessorV2").permitAll()
	            .anyRequest().authenticated())
	        .formLogin()
	            .loginPage("/login")
	            .defaultSuccessUrl("https://localhost:8443", true)
	            .permitAll()
	        .and()
	        .logout()
	            .logoutSuccessUrl("https://localhost:8443")
	            .permitAll();
	    return http.build();  
	}
}
