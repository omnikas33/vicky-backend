package com.lsit.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.lsit.entity.Bank;
import com.lsit.entity.Department;
import com.lsit.entity.User;
import com.lsit.repo.BankRepository;
import com.lsit.repo.DepartmentRepository;
import com.lsit.repo.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private BankRepository bankRepository;

	@Override
	public void run(String... args) throws Exception {
//		userRepository.deleteAll();
//		departmentRepository.deleteAll();
//		bankRepository.deleteAll();
//
//		// Create and save sample departments
//		Department department1 = new Department();
//		department1.setDeptName("The Commissioner, MCD");
//		department1.setAddress("Civic Centre, Minto Road, New Delhi, 110002");
//
//		Department department2 = new Department();
//		department2.setDeptName("The Chairman, New Delhi Municipal Council");
//		department2.setAddress("NDMC Head Office, Palika Kendra, Parliament Street, New Delhi, 110001");
//
//		Department department3 = new Department();
//		department3.setDeptName("The Engineer In Chief, Public Works Department");
//		department3.setAddress("12th Floor M.S.O. Building, I.P Estate, New Delhi, 110002");
//
//		Department department4 = new Department();
//		department4.setDeptName(
//				"The Chief Accounts Officer(HQ), Delhi State Industrial & Infrastructure Development Corp. ltd");
//		department4.setAddress("New Delhi, 110002");
//
//		Department department5 = new Department();
//		department5.setDeptName("The Chief Engineer(HQ), Delhi Development Authority");
//		department5.setAddress("E.M.S Office, Vikas Sadan, INA, New Delhi, 110023");
//
//		Department department6 = new Department();
//		department6.setDeptName("The Executive Engineer(GS), Military Engineer Service");
//		department6.setAddress("Garrison Engineer(South West), CGO Complex, New Delhi, 110003");
//
//		Department department7 = new Department();
//		department7.setDeptName("The Chief Engineer, Delhi Jal Board");
//		department7.setAddress("Varunalay, PAhse-2, Karol Bagh, New Delhi, 110005");
//
//		Department department8 = new Department();
//		department8.setDeptName("The Secretary, Irrigation and Flood Control Department");
//		department8.setAddress("Govt. of NCT of Delhi, L.M Bund Office Complex, Shastri Nagar, Delhi, 110031");
//
//		departmentRepository.saveAll(List.of(department1, department2, department3, department4, department5,
//				department6, department7, department8));
//
//		// Create and save sample users
//		User user = new User();
//		user.setUserName("loneVicky");
//		user.setEmail("lonevicky@gmail.com");
//		user.setPassword(passwordEncoder.encode("123"));
//		user.setDepartment(department1);
//
//		User user1 = new User();
//		user1.setUserName("pranit");
//		user1.setEmail("pranitlodha@gmail.com");
//		user1.setPassword(passwordEncoder.encode("12345"));
//		user1.setDepartment(department2);
//
//		userRepository.saveAll(List.of(user, user1));
//
//		// Create and save sample banks
//		Bank bank1 = new Bank();
//		bank1.setBranchCode("0140");
//		bank1.setBranchName("KAROLBAGH");
//		bank1.setZone("DELHI ZONE");
//		bank1.setIfsc("MAHB0000140");
//		bank1.setBranchAddress("17A/45, WEA KAROL BAGH NEWDELHI");
//
//		Bank bank2 = new Bank();
//		bank2.setBranchCode("0343");
//		bank2.setBranchName("CONNAUGHT PLACE");
//		bank2.setZone("DELHI ZONE");
//		bank2.setIfsc("MAHB0000343");
//		bank2.setBranchAddress("MAHARASHTRA BANK BLDG B-29 CONNAUGHT PLACE DELHI NEWDELHI");
//
//		Bank bank3 = new Bank();
//		bank3.setBranchCode("0351");
//		bank3.setBranchName("CHANDNICHOWK");
//		bank3.setZone("DELHI ZONE");
//		bank3.setIfsc("MAHB0000351");
//		bank3.setBranchAddress("1899, 1st Floor, Opp. Gurudwara Sisganj Chandni Chowk- Delhi");
//
//		Bank bank4 = new Bank();
//		bank4.setBranchCode("0392");
//		bank4.setBranchName("SOUTH EXTN.");
//		bank4.setZone("DELHI ZONE");
//		bank4.setIfsc("MAHB0000392");
//		bank4.setBranchAddress("A 13 SOUTH EXTN PART 1 RING RD NEW DELHI NEWDELHI ");
//
//		Bank bank5 = new Bank();
//		bank5.setBranchCode("0422");
//		bank5.setBranchName("ASAFALI RD");
//		bank5.setZone("DELHI ZONE");
//		bank5.setIfsc("MAHB0000422");
//		bank5.setBranchAddress("A-4/24, AB HOUSE ASAF ALI ROAD, CENTRAL DELHI, NEWDELHI");
//
//		Bank bank6 = new Bank();
//		bank6.setBranchCode("0561");
//		bank6.setBranchName("EAST PATEL NAGAR");
//		bank6.setZone("DELHI ZONE");
//		bank6.setIfsc("MAHB0000561");
//		bank6.setBranchAddress("28/14 EAST PATEL NAGAR NEWDELHI");
//
//		Bank bank7 = new Bank();
//		bank7.setBranchCode("0587");
//		bank7.setBranchName("VIVEK VIHAR");
//		bank7.setZone("DELHI ZONE");
//		bank7.setIfsc("MAHB0000587");
//		bank7.setBranchAddress("C-2 VIVEK VIHAR PHASE --1 DELHI NEWDELHI");
//
//		Bank bank8 = new Bank();
//		bank8.setBranchCode("0593");
//		bank8.setBranchName("PRESS ENCLAVE");
//		bank8.setZone("DELHI ZONE");
//		bank8.setIfsc("MAHB0000593");
//		bank8.setBranchAddress("E11&12 PRESS ENCLAVE SAKET MALVIA NAGAR EXTN AREA NEW DELHI");
//
//		Bank bank9 = new Bank();
//		bank9.setBranchCode("0794");
//		bank9.setBranchName("DELHI GREATER KAILASH");
//		bank9.setZone("DELHI ZONE");
//		bank9.setIfsc("MAHB0000794");
//		bank9.setBranchAddress("M-16 GREATER KAILASH 2 M-BLOCK MARKET NEW DELHI");
//
//		Bank bank10 = new Bank();
//		bank10.setBranchCode("0901");
//		bank10.setBranchName("DR. MUKHERJEE NGR");
//		bank10.setZone("DELHI ZONE");
//		bank10.setIfsc("MAHB0000901");
//		bank10.setBranchAddress("B/9/10 COMMERCIAL COMPLEX MAIN RD DR. MUKHERJEE NAGAR  NEWDELHI");
//
//		Bank bank11 = new Bank();
//		bank11.setBranchCode("0905");
//		bank11.setBranchName("PREET VIHAR");
//		bank11.setZone("DELHI ZONE");
//		bank11.setIfsc("MAHB0000905");
//		bank11.setBranchAddress("F-12 DASHMESH NIWAS PREET VIHAR NEW DELHI");
//
//		Bank bank12 = new Bank();
//		bank12.setBranchCode("0974");
//		bank12.setBranchName("KALKAJI");
//		bank12.setZone("DELHI ZONE");
//		bank12.setIfsc("MAHB0000974");
//		bank12.setBranchAddress("G --1, RAJ TOWER ALAKNANDA SHOPPING COMPLEX, KALKAJI NEWDELHI");
//
//		Bank bank13 = new Bank();
//		bank13.setBranchCode("1160");
//		bank13.setBranchName("UPSC");
//		bank13.setZone("DELHI ZONE");
//		bank13.setIfsc("MAHB0001160");
//		bank13.setBranchAddress("DHOLPUR HOUSE SHAHAJAHAN RD U.P.S.C. DELHI NEWDELHI ");
//
//		Bank bank14 = new Bank();
//		bank14.setBranchCode("1188");
//		bank14.setBranchName("JANAKPURI");
//		bank14.setZone("DELHI ZONE");
//		bank14.setIfsc("MAHB0001188");
//		bank14.setBranchAddress("22-23 INSTITUTIONAL AREA D BLOCK JANAKPURI  NEWDELHI");
//
//		Bank bank15 = new Bank();
//		bank15.setBranchCode("1238");
//		bank15.setBranchName("ROHINI DELHI");
//		bank15.setZone("DELHI ZONE");
//		bank15.setIfsc("MAHB0001238");
//		bank15.setBranchAddress("GARG TRADE CENTRE COMMUNITY CENTRE 11 ROHINI NEW DELHI ");
//
//		Bank bank16 = new Bank();
//		bank16.setBranchCode("1239");
//		bank16.setBranchName("DELHI PASCHIM VIHAR");
//		bank16.setZone("DELHI ZONE");
//		bank16.setIfsc("MAHB0001239");
//		bank16.setBranchAddress("SUNDER VIHAR 12/401 OUTER RING RD PASCHIM VIHAR NEW DELHI ");
//
//		Bank bank17 = new Bank();
//		bank17.setBranchCode("1244");
//		bank17.setBranchName("DWARKA, DELHI");
//		bank17.setZone("DELHI ZONE");
//		bank17.setIfsc("MAHB0001244");
//		bank17.setBranchAddress("PLOT NO 3, INSTITUTIONAL AREA NAIR SERVICE SOCIETY, NEAR DDA SPORTS COMPLEX DWARKA SEC 11, NEW DELHI");
//
//		Bank bank18 = new Bank();
//		bank18.setBranchCode("1247");
//		bank18.setBranchName("BAPROLA");
//		bank18.setZone("DELHI ZONE");
//		bank18.setIfsc("MAHB0001247");
//		bank18.setBranchAddress("OPP. TALAB VILLAGE BAPROLA NANGLOI-NAZAFGARH RD NEW DELHI BAPROLA");
//
//		Bank bank19 = new Bank();
//		bank19.setBranchCode("1250");
//		bank19.setBranchName("PUSHPANJALI ENCLAVE");
//		bank19.setZone("DELHI ZONE");
//		bank19.setIfsc("MAHB0001250");
//		bank19.setBranchAddress("A-26 PUSHPANJALI ENC PITAMPURA NEW DELHI NEW DELHI");
//
//		Bank bank20 = new Bank();
//		bank20.setBranchCode("1257");
//		bank20.setBranchName("MAYUR VIHAR");
//		bank20.setZone("DELHI ZONE");
//		bank20.setIfsc("MAHB0001257");
//		bank20.setBranchAddress("F1-F2 DDA MARKET MAYUR VIHAR PHASE--1 DELHI NEW DELHI-");
//
//		Bank bank21 = new Bank();
//		bank21.setBranchCode("1258");
//		bank21.setBranchName("DAYANAND VIHAR");
//		bank21.setZone("DELHI ZONE");
//		bank21.setIfsc("MAHB0001258");
//		bank21.setBranchAddress("12 DAYANAND VIHAR VIKAS MARG DELHI NEW DELHI");
//
//		Bank bank22 = new Bank();
//		bank22.setBranchCode("1306");
//		bank22.setBranchName("DWARKA (WEST) ");
//		bank22.setZone("DELHI ZONE");
//		bank22.setIfsc("MAHB0001306");
//		bank22.setBranchAddress("HALL F-1FIRST FLRPLOT NO. 6POCKET-V SECTOR-12 DWARKA NEW DELHI NEW DELHI");
//
//		Bank bank23 = new Bank();
//		bank23.setBranchCode("1313");
//		bank23.setBranchName("VASANT KUNJ ");
//		bank23.setZone("DELHI ZONE");
//		bank23.setIfsc("MAHB0001313");
//		bank23.setBranchAddress("GROUND FLR OES BHAVAN PLOT NO.11LSCSECTOR B-1VASANT KUNJ DELHI");
//
//		Bank bank24 = new Bank();
//		bank24.setBranchCode("1342");
//		bank24.setBranchName("DELHI ROHINI SECTOR 24");
//		bank24.setZone("DELHI ZONE");
//		bank24.setIfsc("MAHB0001342");
//		bank24.setBranchAddress("1st FLOOR BEST MEGA MALL ,CS/OCF NO 7 SECTOR 24,ROHINI NEW DELHI");
//
//		Bank bank25 = new Bank();
//		bank25.setBranchCode("1343");
//		bank25.setBranchName("DELHI RAJOURI GARDEN");
//		bank25.setZone("DELHI ZONE");
//		bank25.setIfsc("MAHB0001343");
//		bank25.setBranchAddress("GROUND FLOOR,J-8/77 G NEHRU MARKET RAJOURI GARDEN NEW DELHI");
//
//		Bank bank26 = new Bank();
//		bank26.setBranchCode("1356");
//		bank26.setBranchName("DELHI PITAMPURA");
//		bank26.setZone("DELHI ZONE");
//		bank26.setIfsc("MAHB0001356");
//		bank26.setBranchAddress("GROUND FLOOR, KP-27, COMMUNITY CENTRE, PITAMPURA NEWDELHI");
//
//		bankRepository.saveAll(List.of(bank1, bank2, bank3, bank4, bank5, bank6, bank7, bank8, bank9, bank10, bank11,
//				bank12, bank13, bank14, bank15, bank16, bank17, bank18, bank19, bank20, bank21, bank22, bank23, bank24,
//				bank25, bank26));
//
//		System.out.println("Sample data saved successfully!");
	}
}
