package com.project.demo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.User;
import com.project.demo.model.UserBean;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	public User getUser() {
		User user = User.builder()
				.userId((long)1)
				.userCode("uso11")
				.userName("Hla Hla")
				.userEmail("hla@gmail.com")
				.userMobile("091236457")
				.password("hpass")
				.role("MARKETER")
				.lastAction("")
				.userStatus("")
				.userCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.enabled(true)
				.build();
		return user;
	}
	
	public UserBean getUserBean() {
		UserBean bean = UserBean.builder()
				.userId((long)1)
				.userCode("uso11")
				.userName("Thu Thu")
				.userEmail("thu@gmail.com")
				.userMobile("091236457")
				.password("hpass")
				.role("MARKETER")
				.lastAction("")
				.userStatus("")
				.userCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.enabled(true)
				.build();
		return bean;
	}
	
	@Test
	public void inserUserTest() {
		User user = getUser();
		when(userRepository.findById((long)1)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		User user2 = userServiceImpl.insertUser(user);
		assertEquals("Hla Hla", user2.getUserName());
	}	
	
	@Test
	public void emailSelectUserTest() {
		User user = getUser();
		when(userRepository.selectOne(user.getUserEmail())).thenReturn(user);
		assertEquals(user, userServiceImpl.userSelectOne(user.getUserEmail()));
	}
	
	@Test
	public void findByIdUserTest() {
		User user = getUser();
		when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		assertEquals(user, userServiceImpl.selectUserOne(user.getUserId()));
	}
}
