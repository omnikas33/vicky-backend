package com.lsit.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lsit.entity.CessEntry;

public interface CessEntryService {
	List<CessEntry> getAllEntries();

	List<CessEntry> processExcelFile(MultipartFile file, String paymentId) throws IOException;
}
