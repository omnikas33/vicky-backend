package com.lsit.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lsit.entity.CessEntry;
import com.lsit.entity.Payment;
import com.lsit.repo.CessEntryRepository;
import com.lsit.repo.PaymentRepository;
import com.lsit.service.CessEntryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CessEntryServiceImpl implements CessEntryService {

    @Autowired
    private CessEntryRepository cessEntryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Transactional
    public List<CessEntry> processExcelFile(MultipartFile file, String paymentId) throws IOException {
        List<CessEntry> entries = new ArrayList<>();

        // Fetch the Payment entity using the paymentId
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for ID: " + paymentId));

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the first sheet

            for (Row currentRow : sheet) {
                if (currentRow.getRowNum() == 0 || isRowEmpty(currentRow)) {
                    // Skip header or empty row
                    continue;
                }

                CessEntry entry = new CessEntry();
                try {
                    // Adjusting cell indices based on new entity structure
                    String srNo = getStringCellValue(currentRow.getCell(0)); // Sr No column (index 0)
                    String deptVoucherNumber = getStringCellValue(currentRow.getCell(1)); // Dept Voucher Number column (index 1)
                    LocalDate date = getDateFromCell(currentRow.getCell(2)); // Date column (index 2)
                    String agencyName = getStringCellValue(currentRow.getCell(3)); // Agency Name column (index 3)
                    String agencyAddress = getStringCellValue(currentRow.getCell(4)); // Agency Address column (index 4)
                    String siteAddress = getStringCellValue(currentRow.getCell(5)); // Site Address column (index 5)
                    String totalProject = getStringCellValue(currentRow.getCell(6)); // Total Project column (index 6)
                    Double billValue = getNumericCellValue(currentRow.getCell(7)); // Bill Value column (index 7)
                    Double cessCollection = getNumericCellValue(currentRow.getCell(8)); // Cess Collection column (index 8)
                    Double cessTransfer = getNumericCellValue(currentRow.getCell(9)); // cess transfer column (index 9)

                    if (agencyName == null || agencyName.isEmpty() || siteAddress == null
                            || siteAddress.isEmpty()) {
                        log.error("Invalid data in row {}: Agency Name or Site Address cannot be null or empty",
                                currentRow.getRowNum());
                        throw new IllegalArgumentException("Agency Name or Site Address cannot be null or empty");
                    }

                    entry.setSrNo(srNo);
                    entry.setDeptVoucherNumber(deptVoucherNumber);
                    entry.setDate(date);
                    entry.setAgencyName(agencyName);
                    entry.setAgencyAddress(agencyAddress);
                    entry.setSiteAddress(siteAddress);
                    entry.setTotalProject(totalProject);
                    entry.setBillValue(billValue);
                    entry.setCessCollection(cessCollection);
                    entry.setCessTransfer(cessTransfer);
                    entry.setPayment(payment); // Set payment

                    log.info("Processing row {}: SrNo={}, DeptVoucherNumber={}, Date={}, AgencyName={}, AgencyAddress={}, SiteAddress={}, TotalProject={}, BillValue={}, CessCollection={}",
                            currentRow.getRowNum(), entry.getSrNo(), entry.getDeptVoucherNumber(), entry.getDate(),
                            entry.getAgencyName(), entry.getAgencyAddress(), entry.getSiteAddress(), entry.getTotalProject(),
                            entry.getBillValue(), entry.getCessCollection(), entry.getCessTransfer());

                    entries.add(entry);
                } catch (Exception e) {
                    log.error("Error processing row {}: {}", currentRow.getRowNum(), e.getMessage());
                    throw new IllegalArgumentException(
                            "Error processing row " + currentRow.getRowNum() + ": " + e.getMessage());
                }
            }
        } catch (IOException | IllegalStateException e) {
            throw new IOException("Failed to process Excel file: " + e.getMessage());
        }

        return cessEntryRepository.saveAll(entries);
    }

    @Override
    public List<CessEntry> getAllEntries() {
        return cessEntryRepository.findAll();
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue()).trim();
        }
        return null;
    }

    private Double getNumericCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        return null;
    }

    private boolean isRowEmpty(Row row) {
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
    
    
    public static LocalDate getDateFromCell(Cell cell) {
        if (cell == null) {
            return null; // Handle null cell
        }

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } else if (cell.getCellType() == CellType.STRING) {
            String dateString = cell.getStringCellValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateString, formatter);
        } else {
            throw new DateTimeParseException("Invalid cell type for date", null, 0);
        }
}
}
