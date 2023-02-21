package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.RecruitementResource;

public interface RecruitementResourceService {
	
	RecruitementResource createRecruitementResource(RecruitementResource resource);
	RecruitementResource updateRecruitementResource(RecruitementResource resource);
	void deleteRecruitementResource(Long id);
	List<RecruitementResource> getAllRecruitementResource(int pageNum);
	List<RecruitementResource> getByCodeNameMobileAndType(String code,String name,String mobile,String recruitement_type );
	RecruitementResource getResourceById(Long id);
	Long findTotalPages();
	List<RecruitementResource> getResourceByName(String name);

}
