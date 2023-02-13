package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entity.RecruitementResource;
import com.project.demo.repository.RecruitmentResourceRepository;
import com.project.demo.utils.codeGenerator;

@Service
public class RecruitementResourceServiceImpl implements RecruitementResourceService{
	
	@Autowired
	RecruitmentResourceRepository repo;

	@Override
	public RecruitementResource createRecruitementResource(RecruitementResource resource) {
		// TODO Auto-generated method stub
		RecruitementResource reResource=repo.save(resource);
		reResource=repo.findById(reResource.getResourceId()).get();
		reResource.setResourceCode(codeGenerator.generateRecruitementResource(reResource.getResourceId()));
		return repo.save(resource);
		
		
	}

	@Override
	public RecruitementResource updateRecruitementResource(RecruitementResource resource) {
		// TODO Auto-generated method stub
		RecruitementResource reResource=repo.findById(resource.getResourceId()).get();
		reResource.setResourceId(resource.getResourceId());
		reResource.setResourceCode(resource.getResourceCode());
		reResource.setAddress(resource.getAddress());
		reResource.setContactPerson(resource.getContactPerson());
		reResource.setLink(resource.getLink());
		reResource.setRecruitementType(resource.getRecruitementType());
		reResource.setResourceCreatedTime(resource.getResourceCreatedTime());
		reResource.setResourceMobile(resource.getResourceMobile());
		reResource.setResourceName(resource.getResourceName());
		
		return repo.save(reResource);
	}

	@Override
	public void deleteRecruitementResource(RecruitementResource resource) {
		// TODO Auto-generated method stub
		repo.deleteById(resource.getResourceId());
		
	}

	@Override
	public List<RecruitementResource> getAllRecruitementResource() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

}
