package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.Team;

public interface TeamService {

	Team createTeam(Team team);

	Team updateTeam(Team team);
	
	void deleteTeam(long id);
	
	List<Team> getAllTeams(int pageNum);
	
	Team getById(long id);
	
	Long findTotalPages();
	
	List<Team> searchTeam(String code,String name);
	
	Team getLastTeam();

}
