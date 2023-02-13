package com.project.demo.service;

import java.util.List;

import com.project.demo.entity.Department;


public interface DepartmentService {

	Department createDept(Department dept);
	Department updateDept(Department dept);
	void deleteDept(Department dept);
	List<Department> getAllDepts();
}
