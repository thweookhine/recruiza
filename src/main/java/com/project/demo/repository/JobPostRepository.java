package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long>{
	
	@Query(value ="select * from recruit.job_post where (code like :code) or (name like :name)",
			nativeQuery=true )
	List<JobPost> findWithCodeAndName(@Param("code")String code,@Param("name")String name);
	
	@Query(value = "select * from recruit.job_post where post_date >= :startDate",nativeQuery = true)
	List<JobPost> findWithStartDate(@Param("startDate") String startDate);
	
	@Query(value = "select * from recruit.job_post where post_date <= :endDate",nativeQuery = true)
	List<JobPost> findWithEndDate(@Param("endDate") String endDate);
	
	@Query(value = "select * from recruit.job_post where post_date >= :startDate and post_date <= :endDate",nativeQuery = true)
	List<JobPost> findWithStartDateAndEndDate(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
