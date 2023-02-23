package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.demo.entity.Applicant;

public interface ApplicantService {

	Applicant createApplicant(Applicant applicant);
	Applicant updateApplicant(Applicant applicant);
	void deleteApplicant(Applicant applicant);
	List<Applicant> getAllApplicant();
	Page<Applicant> listAllApplicants(int pageNumber, String sortField, String sortDir, String keyword);
}