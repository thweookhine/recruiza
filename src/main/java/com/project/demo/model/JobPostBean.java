package com.project.demo.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostBean {
	
	
	private long postId;
	
	private String postCode;
	
	@NotEmpty(message = "Post Name must not be empty.")
	private String postName;
	
	@NotEmpty(message = "Post Count must not be empty.")
	private int count;
	
	@NotEmpty(message = "Post Comment must not be empty.")
	private String comment;
	
	@NotEmpty(message = "Post FOC must not be empty.")
	private boolean foc;
	
	@NotEmpty(message = "Post Date must not be empty.")
	private LocalDate postDate;
	
	@NotEmpty(message = "Post Due Date must not be empty.")
	private LocalDate dueDate;
	
	@NotEmpty(message = "Post Team must not be empty.")
	private TeamBean teamBean;
	
	@NotEmpty(message = "Post User must not be empty.")
	private UserBean userBean;
	
	@NotEmpty(message = "RecruitementResource must not be empty.")
	private RecruitementResourceBean resourceBean;
	
	@NotEmpty(message = "Job Position must not be empty.")
	private JobPositionBean jobPositionBean;
	
	@NotEmpty(message = "SheetId must not be empty.")
	private String sheetId;
	
	private Timestamp postCreatedTime;
	

}