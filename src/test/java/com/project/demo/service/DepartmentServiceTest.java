package com.project.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.Department;
import com.project.demo.entity.Team;
import com.project.demo.repository.DepartmentRepository;

@SpringBootTest
class DepartmentServiceTest {
	
	@Mock
	DepartmentRepository deptRepository;
	
	@InjectMocks
	DepartmentServiceImpl deptService;
	
	@Autowired
	DepartmentService service;
	
	@Autowired
	DepartmentRepository repo;
	
	Department getDept() {
		Department dept = new Department();
		dept.setDepartmentId(1);
		dept.setDepartmentCode("D001");
		dept.setDepartmentName("Test");
		return dept;
	}

	@Test
	void testCreateDept() {
		Department dept = getDept();
		when(deptRepository.findById((long) 1)).thenReturn(Optional.of(dept));
		when(deptRepository.save(dept)).thenReturn(dept);
		
		Department result = deptService.createDept(dept);
		
		assertEquals("D001", result.getDepartmentCode());
		assertEquals("Test", result.getDepartmentName());
	}

	@Test
	void testUpdateDept() {
		Department dept = getDept();
		dept.setDepartmentName("Test Updated");
		
		when(deptRepository.findById((long) 1)).thenReturn(Optional.of(dept));
		
		when(deptRepository.save(dept)).thenReturn(dept);
		
		Department result = deptService.updateDept(dept);
		assertEquals("D001", result.getDepartmentCode());
		assertEquals("Test Updated", result.getDepartmentName());
	}

	@Test
	void testDeleteDept() {
		Department dept = getDept();
		deptService.deleteDept(dept);
	}

	@Test
	void testGetAllDepts() {
		Department dept = new Department();
		dept.setDepartmentId(1);
		dept.setDepartmentCode("D001");
		dept.setDepartmentName("Test");
		
		Department dept2 = new Department();
		dept2.setDepartmentId(2);
		dept2.setDepartmentCode("D002");
		dept2.setDepartmentName("Test2");
		
		Department dept3 = new Department();
		dept3.setDepartmentId(3);
		dept3.setDepartmentCode("D003");
		dept3.setDepartmentName("Test3");	
		
		List<Department> list = new ArrayList<>();
		list.add(dept);
		list.add(dept2);
		list.add(dept3);
		
		when(deptRepository.findAll()).thenReturn(list);
		List<Department> result = deptService.getAllDepts();
		assertEquals(3, result.size());
		
	}

	@Test
	void testSearchDeptWithCode() {
		List<Department> list = service.searchDept("D002", "D002");
		for(Department dept : list) {
			for(Team t : dept.getTeams()) {
				System.out.println("team name => "+t.getTeamName());
			}
		}
		assertEquals(1, list.size());
	}
	
	@Test
	void testFindLastDept() {
		Department lastDept = service.getLastDept();
		assertNotNull(lastDept);
	}

}
