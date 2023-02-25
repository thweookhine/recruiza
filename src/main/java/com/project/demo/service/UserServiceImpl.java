package com.project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.demo.entity.User;
import com.project.demo.model.UserBean;
import com.project.demo.repository.UserRepository;
import com.project.demo.utils.codeGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User userSelectOne(String email) {
		return userRepository.selectOne(email);
	}

	@Override
	public User insertUser(User u) {
		User user = userRepository.save(u);
		user = userRepository.findById(user.getUserId()).get();
		user.setUserCode(codeGenerator.generateUserCode(user.getUserId()));
		return userRepository.save(user);
	}

	@Override
	public User selectUserOne(Long id) {

		return userRepository.findById(id).get();
	}

	@Override
	public User updateUser(UserBean bean) {

		User user = userRepository.findById(bean.getUserId()).get();

		user.setUserName(bean.getUserName());
		user.setUserEmail(bean.getUserEmail());
		user.setUserMobile(bean.getUserMobile());
		user.setPassword(bean.getPassword());
		user.setRole(bean.getRole());

		return userRepository.save(user);
	}

	@Override
	public Page<User> listAllUsers(int pageNumber, String sortField, String sortDir, String keyword) {

		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

		if (keyword != null) {
			return userRepository.findAll(keyword, pageable);
		}
		return userRepository.findAll(pageable);
	}

	@Override
	public User deleteUser(Long id) {

		User user = userRepository.findById(id).get();
		user.setUserStatus("DELETED");
		user.setEnabled(false);

		return userRepository.save(user);
	}

	@Override
	public User activeUser(Long id) {

		User user = userRepository.findById(id).get();
		user.setUserStatus("ACTIVE");
		user.setEnabled(true);

		return userRepository.save(user);
	}

	@Override
	public User findUserName(String userName) {

		return userRepository.findByUserName(userName);
	}

	@Override
	public User findUserEmail(String email) {

		return userRepository.findByUserEmail(email);
	}

	public User getById(long id) {
		return userRepository.findById(id).get();
	}

}
