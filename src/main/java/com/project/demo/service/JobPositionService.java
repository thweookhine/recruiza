package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.demo.entity.JobPosition;

public interface JobPositionService {
	
	JobPosition createJobPosition(JobPosition jobPosition);
	JobPosition updateJobPosition(JobPosition jobPosition);
	void deleteJobPosition(Long id);
	List<JobPosition> getAllJobPosition();
	List<JobPosition> getPositionByCodeAndName(String code,String name);
	JobPosition getLastJobPosition();
	JobPosition getPositionById(Long id);
	Long findTotalPages();
	List<JobPosition> getPositionByName(String name);
	Page<JobPosition> listAllPositions(int pageNumber, String sortField, String sortDir, String keyword);
	

}
