package com.lsit.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendOtpEmail(String toEmail, String otp) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            // Set the "From" address (use the same address you used in JavaMailSender configuration)
            helper.setFrom("lodhasit2024@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Your OTP Code");
            helper.setText("Your OTP code is: " + otp);

            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

	public boolean sendPaymentChallanEmail(String toEmail, byte[] pdfData) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(toEmail);
			helper.setSubject("Payment Challan");
			helper.setText("Please find attached your payment challan.");

			ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfData);
			helper.addAttachment("PaymentChallan.pdf", new InputStreamSource() {
				@Override
				public InputStream getInputStream() throws IOException {
					return inputStream;
				}
			});

			mailSender.send(mimeMessage);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}
