package com.project.demo.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamBean {

	private long teamId;
	
	private String teamCode;
	
	@NotEmpty(message = "Team Name must not be empty.")
	private String teamName;
	
	@NotEmpty(message = "Team Department must not be empty.")
	private String departmentName;
}
