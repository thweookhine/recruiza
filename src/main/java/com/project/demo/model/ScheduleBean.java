package com.project.demo.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleBean {

	private long scheduleId;
	
	private String scheduleCode;
	
	@NotEmpty(message = "Schedule Name must not be empty.")
	private String scheduleName;
	
	@NotEmpty(message = "Schedule Time must not be empty.")
	private LocalDateTime time;
	
	@NotEmpty(message = "Applicant must not be empty.")
	private ApplicantBean applicantBean;
}
