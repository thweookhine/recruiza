package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.JobPosition;

public interface JobPositionRepository extends JpaRepository<JobPosition,Long> {

	
	@Query(value = "select * from recruit.job_position where (code like :code) or (name like :name)",
			nativeQuery=true)
	List<JobPosition> findByCodeAndName(@Param("code") String code,@Param("name") String name );
	
	@Query(value = "select * from recruit.job_position order by id desc limit 1",
			nativeQuery=true)
	JobPosition findLastJobPosition();
	
	@Query(value="select * from recruit.job_position where name=:name",nativeQuery=true)
	List<JobPosition> findByName(@Param("name")String name);
	
	@Query("SELECT u from JobPosition u WHERE "
			+ "CONCAT(u.positionId, ' ', u.positionCode, ' ', u.positionName)"
			+ "LIKE %?1%")
	public Page<JobPosition> findAll(String keyword, Pageable pageable);
}

	
