package com.project.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long>{

//	@Query("SELECT h from History h WHERE "
//			+ "h.user.userName = ?1")
//	public Page<History> findAllWithKeyword(String username,String keyword, Pageable pageable);
	
	@Query("SELECT h from History h WHERE h.user.userName = ?1")
	public Page<History> findAllWithUname(String username,Pageable pageable);
}
