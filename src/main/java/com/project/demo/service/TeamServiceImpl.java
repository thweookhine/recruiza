package com.project.demo.service;

import static com.project.demo.utils.codeGenerator.generateTeamCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Department;
import com.project.demo.entity.Team;
import com.project.demo.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepo;

	@Override
	public Team createTeam(Team team) {
		Team t = teamRepo.save(team);
		t = teamRepo.findById(t.getTeamId()).get();
		t.setTeamCode(generateTeamCode(t.getTeamId()));
		return teamRepo.save(t);
	}

	@Override
	public Team updateTeam(Team team) {
		Team t = teamRepo.findById(team.getTeamId()).get();
		t.setTeamName(team.getTeamName());
		t.setDepartment(team.getDepartment());
		return teamRepo.save(t);
	}

	@Override
	public void deleteTeam(long id) {
		teamRepo.deleteById(id);
	}

	@Override
	public Long findTotalPages() {
		Pageable sortById = PageRequest.of(0, 2,Sort.by("teamId"));
		Long totalPages = (long) teamRepo.findAll(sortById).getTotalPages();
		return totalPages;
	}
	@Override
	public List<Team> getAllTeams(int pageNum) {
		Pageable sortById = PageRequest.of(pageNum, 2,Sort.by("teamId").descending());
		List<Team> teamById = teamRepo.findAll(sortById).getContent();
		return teamById;
	}

	@Override
	public Team getLastTeam() {
		return teamRepo.findLastTeam();
	}

	@Override
	public List<Team> searchTeam(String code, String name) {
		return teamRepo.searchTeam("%" + code + "%", "%" + name + "%");
//		Pageable pageable = PageRequest.of(0, 2);
//		List<Team> teams = teamRepo.searchTeam("%" + code + "%", "%" + name + "%", pageable).getContent();
//		return teams;
	}

	@Override
	public Team getById(long id) {
		return teamRepo.findById(id).get();
	}

	@Override
	public List<Team> searchWithName(String name) {
		return teamRepo.findByTeamName(name);
	}

	@Override
	public Team searchWithNameAndDept(String name, long deptId) {
		return teamRepo.searchWithNameAndDept(name, deptId);
	}

	@Override
	public Page<Team> listAllTeams(int pageNumber, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
		
		if (keyword != null) {
			return teamRepo.findAllWithKeyword(keyword, pageable);
		}
		return teamRepo.findAll(pageable);
	}
}
