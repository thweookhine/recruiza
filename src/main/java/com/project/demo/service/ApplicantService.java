package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.demo.entity.Applicant;
import com.project.demo.model.ApplicantBean;

public interface ApplicantService {

	Applicant createApplicant(Applicant applicant);
	
	Applicant updateApplicant(Applicant applicant);
	
	void deleteApplicant(Long id);
	
	List<Applicant> getAllApplicant();
	
	Page<Applicant> listAllApplicants(int pageNumber, String sortField, String sortDir, String keyword);
	
	Applicant getApplicantById(Long id);
	
	Page<Applicant> listApplicantProcess(int pageNumber, String sortField, String sortDir, String keyword, String searchKey);
	
	Applicant changeApplicantStatus(ApplicantBean bean, String checkStatus, String getStatus,String comment);
	
	List<Applicant> getApplicantList(Long postId);
	
	long countOfApplicantStatus(String status);
	
	Integer getCountByJobPost(long postId);
	
}