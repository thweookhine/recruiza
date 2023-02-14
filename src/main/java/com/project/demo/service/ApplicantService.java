package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.Applicant;

public interface ApplicantService {

	Applicant createApplicant(Applicant applicant);
	Applicant updateApplicant(Applicant applicant);
	void deleteApplicant(Applicant applicant);
	List<Applicant> getAllApplicant();
}