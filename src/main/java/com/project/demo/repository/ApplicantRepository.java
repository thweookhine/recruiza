package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant,Long>{

	@Query("SELECT u from Applicant u WHERE "
			+ "CONCAT(u.applicantId, ' ', u.applicantCode, ' ', u.applicantName)"
			+ "LIKE %?1%")
	public Page<Applicant> findSearchAll(String keyword, Pageable pageable);
	
	@Query("select a from Applicant a where a.applicantStatus = ?1 and "
			+ "concat(a.applicantId, ' ', a.applicantCode, ' ', a.applicantName, ' ', a.applicantEmail, ' ', a.applicantMobile, ' ', a.address, ' ', a.currentState, ' ', a.jobPosition.positionName, ' ')"
			+ "like %?2%")
	public Page<Applicant> findApplicantProcess(String status, String searchKey, Pageable pageable);
	
	@Query("select a from Applicant a where a.jobPost.postId = ?1")
	public List<Applicant> getApplicantsByJobPostId(Long postId);

	@Query("select a from Applicant a where "
			+ "concat(a.applicantId, ' ', a.applicantCode, ' ', a.applicantName, ' ', a.applicantEmail, ' ', a.applicantMobile, ' ', a.address, ' ', a.currentState, ' ', a.jobPosition.positionName, ' ')"
			+ "like %?1%")
	public Page<Applicant> findWithSearchKey(String searchKey, Pageable pageable);

}
