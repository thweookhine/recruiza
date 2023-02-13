package com.project.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.RecruitementResource;
import com.project.demo.repository.RecruitmentResourceRepository;

@SpringBootTest
class RecruitementResourceServiceTest {
	
	@Mock
	RecruitmentResourceRepository repo;
	
	@InjectMocks
	RecruitementResourceServiceImpl resourceService;

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
				.build();
		resourceService.deleteRecruitementResource(reResource);
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
				.build();
		
		List<RecruitementResource> list= new ArrayList<RecruitementResource>();
		
		list.add(reResource);
		list.add(reResource1);
		
		when(repo.findAll()).thenReturn(list);
		List<RecruitementResource> reResourceList=resourceService.getAllRecruitementResource();
		assertEquals(2,reResourceList.size());
		verify(repo, times(1)).findAll();
	}

}
