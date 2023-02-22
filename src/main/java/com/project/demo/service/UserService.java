package com.project.demo.service;

import org.springframework.data.domain.Page;

import com.project.demo.entity.User;
import com.project.demo.model.UserBean;

public interface UserService {
	
	User userSelectOne(String email);
	
	User insertUser(User user);
	
	Page<User> listAllUsers(int pageNumber, String sortField, String sortDir, String keyword);
	
	User selectUserOne(Long id);
	
	User updateUser(UserBean bean);
	
	User deleteUser(Long id);
	
	User activeUser(Long id);
	
}
