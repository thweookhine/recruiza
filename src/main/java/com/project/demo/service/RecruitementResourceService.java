package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.demo.entity.JobPosition;
import com.project.demo.entity.RecruitementResource;

public interface RecruitementResourceService {
	
	RecruitementResource createRecruitementResource(RecruitementResource resource);
	RecruitementResource updateRecruitementResource(RecruitementResource resource);
	void deleteRecruitementResource(Long id);
	List<RecruitementResource> getAllRecruitementResource();
	List<RecruitementResource> getByCodeNameMobileAndType(String code,String name,String mobile,String recruitement_type );
	RecruitementResource getResourceById(Long id);
	Long findTotalPages();
	List<RecruitementResource> getResourceByName(String name);
	Page<RecruitementResource> listAllResources(int pageNumber, String sortField, String sortDir, String keyword);
}
