package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long>{

//	@Query("SELECT h from History h WHERE "
//			+ "h.user.userName = ?1")
//	public Page<History> findAllWithKeyword(String username,String keyword, Pageable pageable);
	
	@Query("SELECT h from History h WHERE h.user.userId = ?1")
	public Page<History> findAllWithUname(long userId,Pageable pageable);
	
	@Query(value = "SELECT * from history WHERE user_id = :userId  order by id desc limit 20 ", nativeQuery = true)
	public List<History> findLast20(@Param("userId")long userId);
}
