package com.project.demo.model;

import javax.validation.constraints.NotEmpty;

import com.project.demo.entity.JobPosition;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;

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
	
	private String postName;
	
	private int count;
	
	private String comment;
	
	private boolean foc;
	
	private String postDate;
	
	private String dueDate;
	
	private long teamBean;
	
	private String userBean;
	
	private long resourceId;
	
	private long jobPositionId;

	private String sheetId;
	
	private String postStatus;

	private long departmentId;


}
