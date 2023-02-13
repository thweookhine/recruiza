package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.Team;

public interface TeamService {

	Team createTeam(Team team);

	Team updateTeam(Team team);
	
	void deleteTeam(Team team);
	
	List<Team> getAllTeams();
	
	List<Team> searchTeam(String code,String name);
	
	Team getLastTeam();

}
