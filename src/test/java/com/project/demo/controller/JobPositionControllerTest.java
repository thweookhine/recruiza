package com.project.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.demo.entity.JobPosition;
import com.project.demo.model.DepartmentBean;
import com.project.demo.model.JobPositionBean;
import com.project.demo.repository.DepartmentRepository;
import com.project.demo.repository.JobPositionRepository;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.JobPositionService;

@WebMvcTest(value = JobPositionController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class JobPositionControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	JobPositionService positionservice;
	
	@MockBean
	JobPositionRepository repo;

	@Test
	void testDisplayPrevious() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayNext() {
		fail("Not yet implemented");
	}

	@Test
	void testGetJobPosition() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayView() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveJobPositioSuccess() {
		//fail("Not yet implemented");
		JobPositionBean positionBean = JobPositionBean.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();
		try {
			mockMvc.perform(post("/savejobposition").flashAttr("jobposition", positionBean))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/jobposition"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testSaveJobPositioFail() throws Exception {
		
		JobPositionBean positionBean = JobPositionBean.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();
		
		mockMvc.perform(post("/savejobposition").flashAttr("jobposition", positionBean))
		.andExpect(redirectedUrl("/jobposition"));
		
	}

	@Test
	void testDisplayUpdateJobPosition() throws Exception {
		//fail("Not yet implemented");
		
		JobPositionBean positionBean = JobPositionBean.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();
		
			JobPosition position=JobPosition.builder()
				.positionId((long)1)
				.positionCode("P001")
				.positionName("PM")
				.build();
			
			when(positionservice.getPositionById((long)1)).thenReturn(position);
			
			position.setPositionName("Java Dev");
			
			mockMvc.perform(get("/editjobposition").param("id", "1").flashAttr("jobposition", positionBean))
			.andExpect(view().name("editJobPosition"));
	}

	@Test
	void testUpdateJobPositionSuccess() throws Exception {
		//fail("Not yet implemented");
		JobPositionBean positionBean = JobPositionBean.builder()
				.positionId((long)1)
				.positionCode("JP001")
				.positionName("Project Manager")
				.build();
		
		JobPosition position=JobPosition.builder()
				.positionId((long)1)
				.positionCode("P001")
				.positionName("PM")
				.build();
		
when(positionservice.updateJobPosition(position)).thenReturn(position);
		
		mockMvc.perform(post("/updatejobposition").flashAttr("jobposition", positionBean))
				.andExpect(redirectedUrl("/jobposition"))
				.andExpect(model().attributeExists("message"));

		
	}

	@Test
	void testDeleteJobPosition() {
		fail("Not yet implemented");
	}

	@Test
	void testSearchJobPosition() {
		fail("Not yet implemented");
	}

	@Test
	void testObject() {
		fail("Not yet implemented");
	}

	@Test
	void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	void testClone() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	@Test
	void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	void testWait() {
		fail("Not yet implemented");
	}

	@Test
	void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	void testFinalize() {
		fail("Not yet implemented");
	}

}
