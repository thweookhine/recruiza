package com.project.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant,Long>{

	@Query("SELECT u from Applicant u WHERE "
			+ "CONCAT(u.applicantId, ' ', u.applicantCode, ' ', u.applicantName)"
			+ "LIKE %?1%")
	public Page<Applicant> findAll(String keyword, Pageable pageable);

}
