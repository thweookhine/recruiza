package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

}
