package com.project.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.JobPosition;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.repository.JobPositionRepository;

@SpringBootTest
class JobPositionServiceTest {
	
	@Mock
	JobPositionRepository repo;
	
	@InjectMocks
	JobPositionServiceImpl jpService;
	
	@Autowired
	JobPositionService service;

	@Test
	void testCreateJobPosition() {
		//fail("Not yet implemented");
		JobPosition position = JobPosition.builder()
								.positionId((long)1)
								.positionCode("JP001")
								.positionName("Project Manager")
								.build();
		
		when(repo.findById((long)1)).thenReturn(Optional.of(position));
		when(repo.save(position)).thenReturn(position);
		
		jpService.createJobPosition(position);
		verify(repo,times(2)).save(position);
	}

	@Test
	void testUpdateJobPosition() {
		//fail("Not yet implemented");
		JobPosition position = JobPosition.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();

		when(repo.findById((long) 1)).thenReturn(Optional.of(position));
		when(repo.save(position)).thenReturn(position);
		
		jpService.updateJobPosition(position);
		verify(repo,times(1)).save(position);
		
	}

	@Test
	void testDeleteJobPosition() {
		//fail("Not yet implemented");
		
		jpService.deleteJobPosition((long)1);
		verify(repo,times(1)).deleteById((long)1);
	}

	@Test
	void testGetAllJobPosition() {
		//fail("Not yet implemented");
		
		List<JobPosition> list= new ArrayList<JobPosition>();
		JobPosition position = JobPosition.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();
		JobPosition position1 = JobPosition.builder()
				.positionId((long)2)
				.positionCode("JP002")
				.positionName("Project Manager")
				.build();
		
		list.add(position);
		list.add(position1);
		
		when(repo.findAll()).thenReturn(list);
		List<JobPosition> userList=jpService.getAllJobPosition();
		assertEquals(2,userList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	void testGetPositionById() {
		
		JobPosition position = JobPosition.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();
		
		when(repo.findById((long)1)).thenReturn(java.util.Optional.of(position));
		JobPosition position1=jpService.getPositionById((long)1);
		assertEquals("Project Manager",position1.getPositionName());

	}

	@Test
	void testGetPositionByCodeAndName() {
//		//fail("Not yet implemented");
//		
//		List<JobPosition> list= new ArrayList<JobPosition>();
//		JobPosition position = JobPosition.builder()
//				.positionId((long)1)
//				.positionCode("JP001")
//				.positionName("Senior Developer")
//				.build();
//		JobPosition position1 = JobPosition.builder()
//				.positionId((long)2)
//				.positionCode("JP002")
//				.positionName("Project Manager")
//				.build();
//		
//		list.add(position);
//		list.add(position1);
//		
//		String result= "JP002";
//		
//		when(repo.findByCodeAndName("%"+result+"%","%+"+result+"%")).thenReturn(list);
//		List<JobPosition> positionList=jpService.getPositionByCodeAndName(result,result);
//		assertEquals(1,positionList.size());
//		verify(repo,times(1)).findByCodeAndName("%"+result+"%","%"+result+"%");
//		
		
		List<JobPosition> list=service.getPositionByCodeAndName("J003", "J003");
		assertEquals(1,list.size());
	}
	
	@Test
	void testGetLastJobPosition() {
		service.getLastJobPosition();
	}

}
