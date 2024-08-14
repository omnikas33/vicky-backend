package com.lsit.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lsit.entity.CessEntry;
import com.lsit.service.CessEntryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cess")
public class CessEntryController {

	@Autowired
	private CessEntryService cessEntryService;

//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            cessEntryService.processExcelFile(file);
//            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully!");
//        } catch (Exception e) {
//            log.error("Failed to upload file: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to upload file: " + e.getMessage());
//        }
//    }

	@GetMapping("/entries")
	public ResponseEntity<List<CessEntry>> getAllEntries() {
		List<CessEntry> entries = cessEntryService.getAllEntries();
		return new ResponseEntity<>(entries, HttpStatus.OK);
	}
}
