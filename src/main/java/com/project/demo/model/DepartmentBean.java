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
public class DepartmentBean {
	
	private long departmentId;
	
	private String departmentCode;
	
	@NotEmpty(message = "Department Name must not be empty")
	private String departmentName;

}
