package com.project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Team;
import com.project.demo.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {
	
	@Autowired
	TeamRepository teamRepository;
	
	public Team createTeam() {
		return null;
	}
}
