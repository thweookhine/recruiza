package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	@Query(value = "select * from recruit.team where (code like :code) or (name like :name)",nativeQuery = true)
	List<Team> searchTeam(String code, String name);
	
	@Query(value = "select * from recruit.team order by id desc limit 1", nativeQuery = true)
	Team findLastTeam();
}
