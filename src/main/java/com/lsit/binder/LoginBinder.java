package com.lsit.binder;

import lombok.Data;

@Data
public class LoginBinder {

	private String userName;
	private String password;
	private String departmentName;

	// You can optionally add other fields that might be relevant to the login
	// process
}
