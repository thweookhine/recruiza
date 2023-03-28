package com.project.demo.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.demo.entity.JobPosition;
import com.project.demo.repository.JobPositionRepository;
import com.project.demo.service.JobPositionService;
import com.project.demo.service.JobPositionServiceImpl;

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
	}

	@Test
	void testDeleteJobPosition() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetAllJobPosition() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetPositionByCodeAndName() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetLastJobPosition() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetPositionById() {
		//fail("Not yet implemented");
	}

	@Test
	void testFindTotalPages() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetPositionByName() {
		//fail("Not yet implemented");
	}

	@Test
	void testListAllPositions() {
		//fail("Not yet implemented");
	}

}
