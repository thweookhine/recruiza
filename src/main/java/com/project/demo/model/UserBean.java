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
	
	@NotEmpty(message = "User Password must not be empty")
	private String password;
	
	@NotEmpty(message = "User Confirm Password must not be empty")
	private String confPassword;
	
	@NotEmpty(message = "User Email must not be empty")
	private String userEmail;
	
	@NotEmpty(message = "User Mobile must not be empty")
	private String userMobile;
	
	private String lastAction;
	@NotEmpty(message = "User Role must not be empty")
	private String role;
	
	private String userStatus;
	
	private Timestamp userCreatedTime;

	private boolean enabled;
}
