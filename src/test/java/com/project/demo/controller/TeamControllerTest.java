package com.project.demo.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.demo.entity.Department;
import com.project.demo.entity.Team;
import com.project.demo.model.TeamBean;
import com.project.demo.repository.DepartmentRepository;
import com.project.demo.repository.TeamRepository;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.TeamService;

@WebMvcTest(value = TeamController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class TeamControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	TeamService teamService;
	
	@MockBean
	TeamRepository teamRepo;

	@MockBean
	DepartmentService deptService;
	
	@MockBean
	DepartmentRepository deptRepo;
	
	@Test
	void testToTeam() throws Exception {
		
		mockMvc.perform(get("/team"))
		.andExpect(status().isOk())
		.andExpect(view().name("teamControl"))
		.andExpect(model().attributeExists("team"))
		.andExpect(model().attributeExists("teams"));
	}

	@Test
	void testNextTeamPage() throws Exception {
		mockMvc.perform(get("/nextTeamPage").param("currentPage", "1"))
		.andExpect(model().attributeExists("team"))
		.andExpect(model().attributeExists("teams"))
		.andExpect(view().name("teamControl"))
		.andExpect(status().isOk())
		;
	}

	@Test
	void testPrevTeamPage() throws Exception {
		mockMvc.perform(get("/prevTeamPage").param("currentPage", "1"))
		.andExpect(model().attributeExists("team"))
		.andExpect(model().attributeExists("teams"))
		.andExpect(view().name("teamControl"))
		.andExpect(status().isOk())
		;
	}

	@Test
	void testSaveTeamSuccess() throws Exception {
		TeamBean teamBean = TeamBean.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.departmentName("Testing")
				.build();
		
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
		
		when(teamService.createTeam(team)).thenReturn(team);
		when(deptService.searchDept("Testing", "Testing")).thenReturn(List.of(dept));
		
		mockMvc.perform(post("/saveTeam").flashAttr("team", teamBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/team"));
	}
	
	@Test
	void testSaveTeamFail() throws Exception {
		TeamBean teamBean = TeamBean.builder()
				.build();
		
		mockMvc.perform(post("/saveTeam").flashAttr("team", teamBean))
		.andExpect(view().name("teamControl"));
	}

	@Test
	void testToUpdateTeamPage() throws Exception {
		
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
		
		when(teamService.getById(1)).thenReturn(team);
		
		mockMvc.perform(get("/updateTeam").param("id", "1"))
		.andExpect(model().attributeExists("team"))
		.andExpect(status().isOk())
		.andExpect(view().name("teamAction"));
	}

	@Test
	void testUpdateTeamSuccess() throws Exception {
		
		TeamBean teamBean = TeamBean.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.departmentName("Testing")
				.build();
		
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
		
		when(teamService.createTeam(team)).thenReturn(team);
		when(deptService.searchDept("Testing", "Testing")).thenReturn(List.of(dept));
		
		mockMvc.perform(post("/updateTeam").flashAttr("team", teamBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/team"));
		
	}
	
	@Test
	void testUpdateTeamFail() throws Exception {
		
		TeamBean teamBean = TeamBean.builder().build();
		mockMvc.perform(post("/updateTeam").flashAttr("team", teamBean))
		.andExpect(status().is(200))
		.andExpect(view().name("teamControl"));
		
	}

	@Test
	void testDeleteTeam() throws Exception {
		mockMvc.perform(get("/deleteTeam").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/team"));
	}

	@Test
	void testSearchTeam() throws Exception {
		mockMvc.perform(post("/searchTeam").param("keyword", "Testing").param("departmentName", "Testing"))
		.andExpect(view().name("teamControl"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("teams"))
		;
	}

}
