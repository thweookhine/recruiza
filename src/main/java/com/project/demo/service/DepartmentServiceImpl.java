package com.project.demo.service;

import static com.project.demo.utils.codeGenerator.*;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Department;
import com.project.demo.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository deptRepo;

	@Override
	@Transactional
	public Department createDept(Department dept) {
		Department department = deptRepo.save(dept);
		department = deptRepo.findById(department.getDepartmentId()).get();
		department.setDepartmentCode(generateDepartmentCode(department.getDepartmentId()));
		return deptRepo.save(department);
	}

	@Override
	@Transactional
	public Department updateDept(Department dept) {
		Department department = deptRepo.findById(dept.getDepartmentId()).get();
		department.setDepartmentName(dept.getDepartmentName());
		department.setTeams(dept.getTeams());
		return deptRepo.save(department);
	}

	@Override
	public void deleteDept(Department dept) {
		deptRepo.deleteById(dept.getDepartmentId());
	}

	@Override
	public List<Department> getAllDepts() {
		return deptRepo.findAll();
	}

	@Override
	@Transactional
	public List<Department> searchDept(String code, String name) {
		return deptRepo.searchDept("%" + code + "%", "%" + name + "%");
	}

	@Override
	public Department getLastDept() {
		return deptRepo.findLastDept();
	}

}
