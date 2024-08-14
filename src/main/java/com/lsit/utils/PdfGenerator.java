package com.lsit.utils;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lsit.entity.Payment;

@Component
public class PdfGenerator {

	/*
	 * public byte[] generatePdf(Payment payment) { ByteArrayOutputStream
	 * byteArrayOutputStream = new ByteArrayOutputStream();
	 * 
	 * Document document = new Document(); try { PdfWriter.getInstance(document,
	 * byteArrayOutputStream); document.open();
	 * 
	 * Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16); Font
	 * bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	 * 
	 * Paragraph header = new Paragraph("Payment Challan", headerFont);
	 * header.setAlignment(Element.ALIGN_CENTER); document.add(header);
	 * 
	 * document.add(new Paragraph(" ")); // Add an empty line
	 * 
	 * PdfPTable table = new PdfPTable(2); table.setWidthPercentage(100);
	 * table.setSpacingBefore(10f); table.setSpacingAfter(10f);
	 * 
	 * addTableCell(table, "Payment ID:", bodyFont); addTableCell(table,
	 * payment.getPaymentId(), bodyFont); addTableCell(table, "Transaction ID:",
	 * bodyFont); addTableCell(table, payment.getTransactionId(), bodyFont);
	 * addTableCell(table, "Reference ID:", bodyFont); addTableCell(table,
	 * payment.getReferenceId(), bodyFont); addTableCell(table, "Payee Name:",
	 * bodyFont); addTableCell(table, payment.getPayeeName(), bodyFont);
	 * addTableCell(table, "Payee Email:", bodyFont); addTableCell(table,
	 * payment.getPayeeEmail(), bodyFont); addTableCell(table, "Payment Date:",
	 * bodyFont); addTableCell(table,
	 * payment.getPaymentDate().format(DateTimeFormatter.
	 * ofPattern("yyyy-MM-dd HH:mm:ss")), bodyFont); addTableCell(table,
	 * "Total Amount:", bodyFont); addTableCell(table,
	 * payment.getTotalAmount().toString(), bodyFont);
	 * 
	 * document.add(table);
	 * 
	 * document.close(); } catch (DocumentException e) { e.printStackTrace(); }
	 * 
	 * return byteArrayOutputStream.toByteArray(); }
	 * 
	 * private void addTableCell(PdfPTable table, String text, Font font) { PdfPCell
	 * cell = new PdfPCell(new Phrase(text, font));
	 * cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell); }
	 */
}
