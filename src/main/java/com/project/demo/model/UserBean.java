package com.project.demo.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
	
	private long userId;
	
	private String userCode;
	
	@NotEmpty(message = "User Name must not be empty")
	private String userName;
	
	private String password;
	
	private String confPassword;
	
	@NotEmpty(message = "User Email must not be empty")
	private String userEmail;
	
	@NotEmpty(message = "User Mobile must not be empty")
	private String userMobile;
	
	private String lastAction;
	
	private String role;
	
	private String userStatus;
	
	private Timestamp userCreatedTime;

	private boolean enabled;
}
