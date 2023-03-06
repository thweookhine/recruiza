package com.project.demo.service;

import static com.project.demo.utils.codeGenerator.*;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Department;
import com.project.demo.entity.Team;
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
//		department.setTeams(dept.getTeams());
		return deptRepo.save(department);
	}

	@Override
	public void deleteDept(long id) {
		deptRepo.deleteById(id);
	}

	@Override
	public List<Department> getAllDepts(int pageNum) {
		Pageable sortById = PageRequest.of(pageNum, 2,Sort.by("departmentId").descending());
		List<Department> deptById = deptRepo.findAll(sortById).getContent();
		return deptById;
	}
	
	@Override
	public List<Department> readAllDepts(){
		return deptRepo.findAll();
	}
	
	@Override
	public Long findTotalPages() {
		Pageable sortById = PageRequest.of(0, 2,Sort.by("departmentId"));
		Long totalPages = (long) deptRepo.findAll(sortById).getTotalPages();
		return totalPages;
	}

	@Override
	@Transactional
	public List<Department> searchDept(String code, String name) {
		if(code.isEmpty() && name.isEmpty()) {
			return getAllDepts(0);
		}
		return deptRepo.searchDept("%" + code + "%", "%" + name + "%");
	}

	@Override
	public Department getLastDept() {
		return deptRepo.findLastDept();
	}

	@Override
	public Department getById(long id) {
		return deptRepo.findById(id).get();
	}

	@Override
	public List<Department> searchWithName(String name) {
		return deptRepo.findByDepartmentName(name);
	}

	@Override
	public Department searhWithId(long id) {
		return deptRepo.findById(id).get();
	}

	@Override
	public Department searchOneWithName(String name) {
		return deptRepo.searchWithName(name);
	}

	@Override
	public Page<Department> listAllDepts(int pageNumber, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);
		
		if (keyword != null) {
			return deptRepo.findAll(keyword, pageable);
		}
		return deptRepo.findAll(pageable);
	}

	@Override
	public List<Team> getTeamList(long departmentId) {
		return deptRepo.getTeamListBydId(departmentId);
	}

}
