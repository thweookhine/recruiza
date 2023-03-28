package com.project.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.userEmail = ?1")
	public User selectOne(String email);
	
	@Query("SELECT u from User u WHERE "
			+ "CONCAT(u.userId, ' ', u.userCode, ' ', u.userName, ' ', u.userEmail, ' ', u.userMobile, ' ', u.role, ' ', u.userStatus, ' ')"
			+ "LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	User findByUserName(String userName);
	
	User findByUserEmail(String email);
	
	public User findByToken(String token);
	
	long countByUserStatus(String status);
	
	long countByRole(String role);
	
}
