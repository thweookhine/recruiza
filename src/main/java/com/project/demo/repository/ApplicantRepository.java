package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entity.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant,Long>{

}
