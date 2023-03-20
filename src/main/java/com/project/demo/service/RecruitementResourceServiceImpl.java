package com.project.demo.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Department;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.repository.RecruitmentResourceRepository;
import com.project.demo.utils.codeGenerator;

@Service
public class RecruitementResourceServiceImpl implements RecruitementResourceService{
	
	@Autowired
	RecruitmentResourceRepository repo;
	
	public JobPost getJobPost() {

		Department dept = Department.builder()
				.departmentId(1)
				.departmentCode("D001")
				.departmentName("Testing")
				.build();
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.department(dept)
				.build();
		
		User user = User.builder()
				.userId((long)1)
				.userCode("U001")
				.userName("Lily")
				.userEmail("lily@gmail.com")
				.password("lily")
				.userCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.userMobile("097878787878")
				.lastAction("update job post")
				.role("DH")
				.userStatus("ACTIVE")
				.build();
		
		RecruitementResource resource = RecruitementResource.builder()
				.resourceId(1)
				.resourceCode("R001")
				.resourceName("Job.com.mm")
				.resourceMobile("097878787878")
				.link("www.google.com")
				.address("Yangon")
				.contactPerson("Rose")
				.recruitementType("University")
				.resourceCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
		
		JobPosition jobPosition = JobPosition.builder()
				.positionId(1)
				.positionCode("J001")
				.positionName("Senior Developer")
				.build();
		
		JobPost jp = JobPost.builder()
				.postId((long)1)
				.postName("JobPost")
				.count(2)
				.comment("Comment for Job Post-")
				.foc(false)
				.postDate(LocalDate.now())
				.dueDate(LocalDate.of(2023, 04, 25))
				.team(team)
				.user(user)
				.resource(resource)
				.jobPosition(jobPosition)
				.sheetId("Sheet ID")
				.postCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
		
		return jp;
	}
	

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
		//reResource.setResourceId(resource.getResourceId());
		//reResource.setResourceCode(resource.getResourceCode());
		reResource.setAddress(resource.getAddress());
		reResource.setContactPerson(resource.getContactPerson());
		reResource.setLink(resource.getLink());
		reResource.setRecruitementType(resource.getRecruitementType());
		//reResource.setResourceCreatedTime(resource.getResourceCreatedTime());
		reResource.setResourceMobile(resource.getResourceMobile());
		reResource.setResourceName(resource.getResourceName());
		
		return repo.save(reResource);
	}

	@Override
	public void deleteRecruitementResource(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
		
	}

	@Override
	public List<RecruitementResource> getAllRecruitementResource() {
		// TODO Auto-generated method stub
//		Pageable sortById = PageRequest.of(pageNum,3,Sort.by("resourceId").descending());
//		List<RecruitementResource> resourceById=repo.findAll(sortById).getContent();
//		return resourceById;
		return repo.findAll();
	}


	@Override
	public List<RecruitementResource> getByCodeNameMobileAndType(String code, String name, String mobile,
			String recruitement_type) {
		// TODO Auto-generated method stub
		return repo.findByCodeNameMobileAndType("%"+code+"%","%"+name+"%","%"+mobile+"%","%"+recruitement_type+"%");
	}


	@Override
	public RecruitementResource getResourceById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}


	@Override
	public Long findTotalPages() {
		// TODO Auto-generated method stub
		Pageable sortById=PageRequest.of(0,10,Sort.by("resourceId"));
		Long totalPages=(long) repo.findAll(sortById).getTotalPages();
		return totalPages;
	}


	@Override
	public List<RecruitementResource> getResourceByName(String name) {
		// TODO Auto-generated method stub
		return repo.findByName(name);
	}


	@Override
	public Page<RecruitementResource> listAllResources(int pageNumber, String sortField, String sortDir,
			String keyword) {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);
		
		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}


	@Override
	public int getCountByType(String type) {
		return repo.getCountByType(type);
	}

}
