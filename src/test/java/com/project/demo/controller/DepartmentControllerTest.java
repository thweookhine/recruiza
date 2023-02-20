package com.project.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.demo.entity.Department;
import com.project.demo.model.DepartmentBean;
import com.project.demo.repository.DepartmentRepository;
import com.project.demo.service.DepartmentService;

//@SpringBootTest
@WebMvcTest(value = DepartmentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
//@AutoConfigureMockMvc
class DepartmentControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	DepartmentService deptService;
	
	@MockBean
	DepartmentRepository deptRepo;
	
	DepartmentBean getDeptBean(int num) {
		return DepartmentBean.builder()
				.departmentId(num)
				.departmentCode("D00"+num)
				.departmentName("Dept"+1)
				.build();
	}

	@Test
	void testToDepartment() throws Exception {
		mockMvc.perform(get("/department"))
		.andExpect(view().name("departmentControl"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("department"));
	}

	@Test
	void testNextDepartmentPage() throws Exception {
		
		List<Department> depts = new ArrayList<>();
		Department d1 = Department.builder()
				.departmentCode("D001")
				.departmentId(1)
				.departmentName("Testing")
				.build();
		
		Department d2 = Department.builder()
				.departmentCode("D002")
				.departmentId(2)
				.departmentName("Web")
				.build();
		depts.add(d1);
		depts.add(d2);
		
		when(deptService.getAllDepts(0)).thenReturn(depts);
		
		mockMvc.perform(get("/nextDepartmentPage").param("currentPage", "1"))
		.andExpect(view().name("departmentControl"));
	}

	@Test
	void testPrevDepartmentPage() throws Exception {
		List<Department> depts = new ArrayList<>();
		Department d1 = Department.builder()
				.departmentCode("D001")
				.departmentId(1)
				.departmentName("Testing")
				.build();
		
		Department d2 = Department.builder()
				.departmentCode("D002")
				.departmentId(2)
				.departmentName("Web")
				.build();
		depts.add(d1);
		depts.add(d2);
		
		when(deptService.getAllDepts(0)).thenReturn(depts);
		
		mockMvc.perform(get("/prevDepartmentPage").param("currentPage", "1"))
		.andExpect(view().name("departmentControl"));
	}

	@Test
	void testSaveDepartmentSuccess() throws Exception {
		DepartmentBean deptBean = getDeptBean(1);
		mockMvc.perform(post("/saveDepartment").flashAttr("department", deptBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/department"));
	}
	
	@Test
	void testSaveDepartmentFail() throws Exception {
		DepartmentBean deptBean = DepartmentBean.builder().build();
		mockMvc.perform(post("/saveDepartment").flashAttr("department", deptBean))
		.andExpect(view().name("departmentControl"));
	}

	@Test
	void testToUpdatePage() throws Exception {
		DepartmentBean dBean = getDeptBean(1);
		
		Department dept = Department.builder()
				.departmentId(1)
				.departmentCode("D001")
				.departmentName("Marketing")
				.build();
		
		when(deptService.getById((long) 1)).thenReturn(dept);
		
		dBean.setDepartmentName("Updated Dept Name");
		
		mockMvc.perform(get("/updateDepartment").param("id", "1"))
		.andExpect(view().name("departmentAction"));
	}

	@Test
	void testUpdateDepartmentSuccess() throws Exception {
		DepartmentBean dBean = getDeptBean(1);
		Department dept = Department.builder()
				.departmentId(dBean.getDepartmentId())
				.departmentCode(dBean.getDepartmentCode())
				.departmentName(dBean.getDepartmentName())
				.build();
		
		when(deptService.updateDept(dept)).thenReturn(dept);
		
		mockMvc.perform(post("/updateDepartment").flashAttr("department", dBean))
		.andExpect(redirectedUrl("/department"));
		
	}
	
	@Test
	void testUpdateDepartmentFail() throws Exception {
		DepartmentBean dBean = DepartmentBean.builder().build();
		Department dept = Department.builder()
				.departmentId(dBean.getDepartmentId())
				.departmentCode(dBean.getDepartmentCode())
				.departmentName(dBean.getDepartmentName())
				.build();
		
		when(deptService.updateDept(dept)).thenReturn(dept);
		
		mockMvc.perform(post("/updateDepartment").flashAttr("department", dBean))
		.andExpect(view().name("departmentControl"));
		
	}

	@Test
	void testDeleteDepartment() throws Exception {
		mockMvc.perform(get("/deleteDepartment").param("id", "1"))
		.andExpect(redirectedUrl("/department"));
	}

	@Test
	void testSearchDepartment() throws Exception {
		
		String keyword = "Test";
		
		List<Department> depts = new ArrayList<>();
		Department d1 = Department.builder()
				.departmentCode("D001")
				.departmentId(1)
				.departmentName("Testing")
				.build();
		
		Department d2 = Department.builder()
				.departmentCode("D002")
				.departmentId(2)
				.departmentName("Web")
				.build();
		depts.add(d1);
		depts.add(d2);
		
		when(deptService.searchDept(keyword, keyword)).thenReturn(depts);
		
		mockMvc.perform(post("/searchDepartment").param("keyword", keyword))
		.andExpect(view().name("departmentControl"));
	}

}
