package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.RecruitementResource;

public interface RecruitementResourceService {
	
	RecruitementResource createRecruitementResource(RecruitementResource resource);
	RecruitementResource updateRecruitementResource(RecruitementResource resource);
	void deleteRecruitementResource(RecruitementResource resource);
	List<RecruitementResource> getAllRecruitementResource();

}
