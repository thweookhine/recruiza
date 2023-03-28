package com.project.demo.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.Department;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.repository.RecruitmentResourceRepository;
import com.project.demo.service.RecruitementResourceService;
import com.project.demo.service.RecruitementResourceServiceImpl;

@SpringBootTest
class RecruitementResourceServiceTest {
	
	@Mock
	RecruitmentResourceRepository repo;
	
	@InjectMocks
	RecruitementResourceServiceImpl resourceService;
	
	@Autowired
	RecruitementResourceService service;
	
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

	@Test
	void testCreateRecruitementResource() {
		//fail("Not yet implemented");
		RecruitementResource reResource=RecruitementResource.builder()
										.resourceId((long)1)
										.resourceCode("R001")
										.address("Yangon,Thamine")
										.contactPerson("May Yamin Oo")
										.link("job.com")
										.recruitementType("Agency")
										.resourceMobile("0987654321")
									    .resourceName("Kim Taehyung")
									    .resourceCreatedTime(Timestamp.valueOf("2023-02-13 15:32:00"))
									    //.jobPost(getJobPost())
										.build();
		
		when(repo.findById((long)1)).thenReturn(Optional.of(reResource));
		when(repo.save(reResource)).thenReturn(reResource);
		
		resourceService.createRecruitementResource(reResource);
		verify(repo,times(2)).save(reResource);
	}

	@Test
	void testUpdateRecruitementResource() {
		//("Not yet implemented");
		RecruitementResource reResource=RecruitementResource.builder()
				.resourceId((long)1)
				.resourceCode("R001")
				.address("Yangon,Thamine")
				.contactPerson("May Yamin Oo")
				.link("job.com")
				.recruitementType("Agency")
				.resourceMobile("0987654321")
			    .resourceName("Kim Taehyung")
			    .resourceCreatedTime(Timestamp.valueOf("2023-02-13 15:32:00"))
			    //.jobPost(getJobPost())
				.build();
		
		when(repo.findById((long) 1)).thenReturn(Optional.of(reResource));
		when(repo.save(reResource)).thenReturn(reResource);
		
		resourceService.updateRecruitementResource(reResource);
		verify(repo,times(1)).save(reResource);
	}

	@Test
	void testDeleteRecruitementResource() {
		//fail("Not yet implemented");
		RecruitementResource reResource=RecruitementResource.builder()
				.resourceId((long)1)
				.resourceCode("R001")
				.address("Yangon,Thamine")
				.contactPerson("May Yamin Oo")
				.link("job.com")
				.recruitementType("Agency")
				.resourceMobile("0987654321")
			    .resourceName("Kim Taehyung")
			    .resourceCreatedTime(Timestamp.valueOf("2023-02-13 15:32:00"))
			    //.jobPost(getJobPost())
				.build();
		
		resourceService.deleteRecruitementResource((long)1);
		verify(repo,times(1)).deleteById(reResource.getResourceId());
	}

	@Test
	void testGetAllRecruitementResource() {
		//fail("Not yet implemented");
		RecruitementResource reResource=RecruitementResource.builder()
				.resourceId((long)1)
				.resourceCode("R001")
				.address("Yangon,Thamine")
				.contactPerson("May Yamin Oo")
				.link("job.com")
				.recruitementType("Agency")
				.resourceMobile("0987654321")
			    .resourceName("Kim Taehyung")
			    .resourceCreatedTime(Timestamp.valueOf("2023-02-13 15:32:00"))
			    //.jobPost(getJobPost())
				.build();
		
		RecruitementResource reResource1=RecruitementResource.builder()
				.resourceId((long)2)
				.resourceCode("R002")
				.address("Yangon,Hledan")
				.contactPerson("May Yamin Oo")
				.link("job.com")
				.recruitementType("Agency")
				.resourceMobile("0912345678")
			    .resourceName("Thwe Oo Khine")
			    .resourceCreatedTime(Timestamp.valueOf("2023-02-13 15:32:00"))
			    //.jobPost(getJobPost())
				.build();
		
		List<RecruitementResource> list= new ArrayList<RecruitementResource>();
		
		list.add(reResource);
		list.add(reResource1);
		
		when(repo.findAll()).thenReturn(list);
		List<RecruitementResource> reResourceList=resourceService.getAllRecruitementResource();
		assertEquals(2,reResourceList.size());
		verify(repo, times(1)).findAll();
	}
	@Test
	void testGetByCodeNameMobileAndType() {
		
		List<RecruitementResource> list= service.getByCodeNameMobileAndType("R007", "R007", "R007", "R007");
		assertEquals(1,list.size());
	}

	@Test
	void testGetResourceById() {
		RecruitementResource resource = RecruitementResource.builder()
				.resourceId((long)1)
				.resourceCode("R002")
				.address("Yangon,Hledan")
				.contactPerson("May Yamin Oo")
				.link("job.com")
				.recruitementType("Agency")
				.resourceMobile("0912345678")
			    .resourceName("Thwe Oo Khine")
			    .resourceCreatedTime(Timestamp.valueOf("2023-02-13 15:32:00"))
			   // .jobPost(getJobPost())
				.build();
		
		when(repo.findById((long)1)).thenReturn(java.util.Optional.of(resource));
		RecruitementResource resource1=resourceService.getResourceById((long)1);
		assertEquals("Yangon,Hledan",resource1.getAddress());
		assertEquals("May Yamin Oo",resource1.getContactPerson());
	}
}
