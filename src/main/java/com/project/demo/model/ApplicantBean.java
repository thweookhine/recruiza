package com.project.demo.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantBean {

	private long applicantId;
	
	private String applicantCode;
	
	@NotEmpty(message = "Applicant Name must not be empty.")
	private String applicantName;
	
	@NotEmpty(message = "Applicant Email must not be empty.")
	private String applicantEmail;
	
	@NotEmpty(message = "Applicant Mobile must not be empty.")
	private String applicantMobile;
	
	@NotEmpty(message = "Applicant Address must not be empty.")
	private String address;
	
	@NotEmpty(message = "Applicant Link must not be empty.")
	private String link;
	
//	@NotEmpty(message = "Applicant Current State must not be empty.")
	private String currentState;
	
	private String applicantStatus;
	
	private String applyTime;
	
	private String comment;
	
	//@NotEmpty(message = "Job Post must not be empty.")
	private String jobPostBean;
	
	private String jobPositionBean;
	
}
