package com.project.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.project.demo.entity.User;

@Service
public interface UserService {

	User userSelectOne(String email);

	User insertUser(User user);

	Page<User> listAllUsers(int pageNumber, String sortField, String sortDir, String keyword);

	User selectUserOne(Long id);

	User updateUser(User user);
	
	User changePassword(User user);

	User deleteUser(Long id);

	User activeUser(Long id);

	User findUserName(String userName);

	User findUserEmail(String email);

	User getById(long id);
	
	User updateToken(String token, String email);
	
	User getUser(String token);
	
	User updatePassword(User user, String newPassword);
	
	User editUserRole(Long userId,String role);
	
	long countUserByStatus(String status);
	
	long countUserByRole(String role);
}
