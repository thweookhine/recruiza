package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.JobPosition;

public interface JobPositionService {
	
	JobPosition createJobPosition(JobPosition jobPosition);
	JobPosition updateJobPosition(JobPosition jobPosition);
	void deleteJobPosition(JobPosition jobPosition);
	List<JobPosition> getAllJobPosition();
	List<JobPosition> getPositionByCodeAndName(String code,String name);
	JobPosition getLastJobPosition();
	

}
