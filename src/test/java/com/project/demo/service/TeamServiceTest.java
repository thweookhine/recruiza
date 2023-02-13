package com.project.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.Department;
import com.project.demo.entity.Team;
import com.project.demo.repository.TeamRepository;

@SpringBootTest
class TeamServiceTest {
	
	@Mock
	TeamRepository teamRepository;
	
	@InjectMocks
	TeamServiceImpl teamService;
	
	@Autowired
	TeamRepository repo;
	
	@Autowired
	TeamServiceImpl service;
	
	Team getTeam() {
		Department dept = Department.builder()
				.departmentCode("D001")
				.departmentName("Dept1")
				.build();
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Team1")
				.department(dept)
				.build();
		return team;
	}

	@Test
	void testCreateTeam() {
		Team team = getTeam();
		
		when(teamRepository.findById((long) 1)).thenReturn(Optional.of(team));
		when(teamRepository.save(team)).thenReturn(team);
		
		Team result = teamService.createTeam(team);
		
		assertEquals("T001", result.getTeamCode());
		assertEquals("Team1", result.getTeamName());
	}

	@Test
	void testUpdateTeam() {
		Team team = getTeam();
		team.setTeamName("Team updated");
		
		when(teamRepository.findById((long) 1)).thenReturn(Optional.of(team));
		when(teamRepository.save(team)).thenReturn(team);
		
		Team result = teamService.updateTeam(team);
		
		assertEquals("T001", result.getTeamCode());
		assertEquals("Team updated", result.getTeamName());
	}

	@Test
	void testDeleteTeam() {
		Team team = getTeam();
		teamService.deleteTeam(team);
	}

	@Test
	void testGetAllTeams() {
		Department dept = new Department();
		dept.setDepartmentId(1);
		dept.setDepartmentCode("D001");
		dept.setDepartmentName("Test");
		
		Department dept2 = new Department();
		dept2.setDepartmentId(2);
		dept2.setDepartmentCode("D002");
		dept2.setDepartmentName("Test2");
		
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Team1")
				.department(dept)
				.build();
		
		Team team2 = Team.builder()
				.teamId(2)
				.teamCode("T002")
				.teamName("Team2")
				.department(dept)
				.build();
		Team team3 = Team.builder()
				.teamId(3)
				.teamCode("T003")
				.teamName("Team3")
				.department(dept2)
				.build();
		
		Team team4 = Team.builder()
				.teamId(4)
				.teamCode("T004")
				.teamName("Team4")
				.department(dept)
				.build();
		
		List<Team> list = new ArrayList<>();
		list.add(team);
		list.add(team2);
		list.add(team3);
		list.add(team4);
		
		when(teamRepository.findAll()).thenReturn(list);
		
		List<Team> result = teamService.getAllTeams();
		
		assertEquals(4, result.size());
		
	}

	@Test
	void testSearchTeam() {
		List<Team> list = service.searchTeam("T002","T002");
		assertEquals(1, list.size());
	}

	@Test
	void testGetLastTeam() {
		Team team = service.getLastTeam();
		assertNotNull(team);
		//2023-02-13 14:08:30
	}

}
