package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.Applicant;
import com.project.demo.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
	

	String FINDWITHOUTPENDING = "select * from recruit.job_post where post_status != 'PENDING' ";

	String FINDWITHKEYWORD = "SELECT p from JobPost p WHERE CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName) LIKE %?1%";
	
	@Query("SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName)"
			+ "LIKE %?1% and p.postStatus = ?2")
	public Page<JobPost> findAll(String keyword,String status, Pageable pageable);
	
	@Query("SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName)"
			+ "LIKE %?1% and p.postStatus = ?2")
	public List<JobPost> findWithKeywordAndStatus(String keyword,String status);
	
	@Query("SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName)"
			+ "LIKE %?1% and post_status='POSTED' and post_date >= ?2")
	public List<JobPost> findWithKeywordAndStartDate(String keyword,String startDate);
	
	@Query("SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName)"
			+ "LIKE %?1% and post_status='POSTED' and post_date <= ?2")
	public List<JobPost> findWithKeywordAndEndDate(String keyword,String endDate);
	
	@Query("SELECT p from JobPost p WHERE post_status='POSTED' and post_date >= ?1")
	public List<JobPost> findWithStartDate(String startDate);
	
	@Query("SELECT p from JobPost p WHERE post_status='POSTED' and post_date <= ?1")
	public List<JobPost> findWithEndDate(String endDate);
	
	@Query("SELECT p from JobPost p WHERE post_status='POSTED' and post_date >= ?1 and post_date <= ?2")
	public List<JobPost> findWithStartDateAndEndDate(String startDate,String ndDate);
	
	@Query("SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName)"
			+ "LIKE %?1% and post_status='POSTED' and post_date >= ?2 and post_date <= ?3")
	public List<JobPost> findWithKeywordAndStartDateAndEndDate(String keyword,String startDate,String endDate);
	
	@Query("SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName)"
			+ "LIKE %?1%")
	public Page<JobPost> findAllWithKeyword(String keyword, Pageable pageable);

	@Query(value = "select * from recruit.job_post where post_status = 'POSTED' and  due_date >= :dueDate ", nativeQuery = true)
	List<JobPost> findBeforeDueDate(@Param("dueDate") String dueDate);

	@Query(value = FINDWITHOUTPENDING ,nativeQuery=true)
	Page<JobPost> findJobPostsWithoutPending(Pageable pageable);
	
	@Query(value = "SELECT p from JobPost p WHERE "
			+ "CONCAT(p.postName, ' ', p.postCode, ' ',p.team.teamName,' ',p.team.department.departmentName,' ',p.resource.resourceName,' ',p.jobPosition.positionName, ' ',p.postStatus)"
			+ "LIKE %?1% and p.postStatus != 'PENDING'" )
	Page<JobPost> findJobPostsWithKeywordAndNotPending(String keyword,Pageable pageable);

	@Query(value = "select * from recruit.job_post where due_date < :dueDate ", nativeQuery = true)
	List<JobPost> findAfterDueDate(@Param("dueDate") String dueDate);

	List<JobPost> findByPostStatus(String poststatus);
	
	@Query(value= "select count(*) from recruit.job_post where (jobposition_id = :positionId) ", nativeQuery=true)
	Integer getCountByPosition(@Param("positionId")long positionId);
	
	@Query("select count(a) from JobPost j join j.applicants a where j.postId = ?1")
	long getTotalApplicants(long postId);
	
	@Query("select count(a) from JobPost j join j.applicants a where j.postId = ?1 and a.applicantStatus = 'Hired'")
	long getByHiredApplicants(long postId);
	
	@Query("select count(a) from JobPost j join j.applicants a where j.postId = ?1 and a.currentState = 'Fail'")
	long getByRejectedApplicants(long postId);
	
	@Query("select a from JobPost j join j.applicants a where j.postId = ?1")
	List<Applicant> getApplicantsByPostId(long postId);

	// @Query(value = "select * from recruit.job_post where post_date >= :startDate and post_date <= :endDate",nativeQuery = true)
	// List<JobPost> findWithStartDateAndEndDate(@Param("startDate") String startDate,@Param("endDate") String endDate);
	@Query(value= "select count(*) from recruit.job_post where (post_status = :type) ", nativeQuery=true)
	Integer getCountByType(@Param("type") String type);
	
}
