package com.project.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.demo.entity.Department;

@Service
public interface DepartmentService {

	Department createDept(Department dept);

	Department updateDept(Department dept);

	void deleteDept(long id);

	List<Department> getAllDepts(int pageNum);
	
	List<Department> readAllDepts();
	
	Long findTotalPages();

	List<Department> searchDept(String code, String name);
	
	Department getById(long id);
	
	Department getLastDept();
}
