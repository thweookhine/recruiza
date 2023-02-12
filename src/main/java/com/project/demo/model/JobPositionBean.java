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
public class JobPositionBean {
	
	private long positionId;
	
	private String positionCode;
	
	@NotEmpty(message = "Position Name must not be empty.")
	private String positionName;
}
