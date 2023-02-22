package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	@Query(value = "select * from recruit.team where (code like :code) or (name like :name)",nativeQuery = true)
	List<Team> searchTeam(String code, String name);
	
	@Query(value = "select * from recruit.team order by id desc limit 1", nativeQuery = true)
	Team findLastTeam();
	
	@Query(value = "select * from recruit.team where name = :name and department_id = :deptId", nativeQuery = true)
	Team searchWithNameAndDept(@Param("name")String name,@Param("deptId")long deptId);
	
	List<Team> findByTeamName(String teamname);
	
	@Query("SELECT t from Team t WHERE "
			+ "CONCAT(t.teamId, ' ', t.teamCode, ' ', t.teamName, ' ',t.department.departmentName)"
			+ "LIKE %?1%")
	public Page<Team> findAllWithKeyword(String keyword, Pageable pageable);
	
	
}
