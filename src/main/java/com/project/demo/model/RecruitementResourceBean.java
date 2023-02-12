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
public class RecruitementResourceBean {

	private long resourceId;
	
	private String resourceCode;
	
	@NotEmpty(message = "Resource Name must not be empty.")
	private String resourceName;
	
	@NotEmpty(message = "Resource Link must not be empty.")
	private String link;
	
	@NotEmpty(message = "Resource Address must not be empty")
	private String address;
	
	@NotEmpty(message = "Resource Mobile must not be empty")
	private String resourceMobile;
	
	@NotEmpty(message = "Resource Contact Person must not be empty")
	private String contactPerson;
	
	@NotEmpty(message = "Resource Recruitement Type must not be empty")
	private String recruitementType;
	
	private Timestamp resourceCreatedTime;
	
}
