package com.project.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.Department;
import com.project.demo.entity.Team;
import com.project.demo.model.DepartmentBean;
import com.project.demo.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;

	@GetMapping("/department")
	public ModelAndView toDepartment(ModelMap model, RedirectAttributes ra,DepartmentBean deptBean) {
		String keyword = null;
		
		return searchDept(model,deptBean, ra, 1, "departmentId", "asc", keyword);
	}

	@GetMapping(value = "/searchDepartment/{pageNumber}")
	public ModelAndView searchDept(ModelMap model,DepartmentBean deptBean , RedirectAttributes ra, @PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<Department> page = deptService.listAllDepts(currentPage, sortField, sortDir, keyword);

		long totalDepts = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<Department> depts = page.getContent();
		
		int index = Integer.parseInt((currentPage-1)+"1");
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalDepts", totalDepts);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("depts",depts );
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("index",index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

//		DepartmentBean deptBean = DepartmentBean.builder()
//				.build();
//		

		return new ModelAndView("departmentControl", "department", deptBean);

	}

	@GetMapping("/nextDepartmentPage")
	public ModelAndView nextDepartmentPage(@RequestParam("currentPage") String cp, ModelMap model) {
		List<Department> depts = deptService.getAllDepts(Integer.parseInt(cp));
		model.addAttribute("currentPage", Integer.parseInt(cp) + 1);
		model.addAttribute("totalPages", deptService.findTotalPages());
		model.addAttribute("depts", depts);
		return new ModelAndView("departmentControl", "department", new DepartmentBean());
	}

	@GetMapping("/prevDepartmentPage")
	public ModelAndView prevDepartmentPage(@RequestParam("currentPage") String cp, ModelMap model) {

		List<Department> depts = deptService.getAllDepts(Integer.parseInt(cp) - 2);
		model.addAttribute("currentPage", Integer.parseInt(cp) - 1);
		model.addAttribute("totalPages", deptService.findTotalPages());
		model.addAttribute("depts", depts);
		return new ModelAndView("departmentControl", "department", new DepartmentBean());
	}

	@PostMapping("/saveDepartment")
	public ModelAndView saveDepartment(@ModelAttribute("department") @Validated DepartmentBean departmentBean,
			BindingResult bindingResult, ModelMap model, RedirectAttributes ra) {

		if (bindingResult.hasErrors()) {
			return toDepartment(model, ra, departmentBean);
		}

		Department dept = deptService.searchOneWithName(departmentBean.getDepartmentName());

		if (dept == null) {

			// Created

			Department dept2 = Department.builder().departmentName(departmentBean.getDepartmentName()).build();
			System.out.println("dept name" + dept2.getDepartmentName());
			Department result = deptService.createDept(dept2);
			
			if (result != null) {
				ra.addFlashAttribute("message", "Successfully Added.");
			}
		} else {
			ra.addFlashAttribute("message", "Department already exists!");
		}

		return new ModelAndView("redirect:/department");
	}

	@GetMapping("/updateDepartment")
	public ModelAndView toUpdatePage(@RequestParam("id") long id, ModelMap model) {

		Department dept = deptService.getById(id);
		DepartmentBean deptBean = DepartmentBean.builder().departmentId(dept.getDepartmentId())
				.departmentCode(dept.getDepartmentCode()).departmentName(dept.getDepartmentName()).build();
		return new ModelAndView("departmentAction", "department", deptBean);
	}

	@PostMapping("/updateDepartment")
	public ModelAndView updateDepartment(@ModelAttribute("department") @Validated DepartmentBean deptBean,
			BindingResult bindingResult, @RequestParam("oldName") String oldName, ModelMap model,
			RedirectAttributes ra) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("departmentAction");
		}
		Department searchedDept = deptService.searchOneWithName(deptBean.getDepartmentName());
		if (searchedDept == null || oldName.equals(deptBean.getDepartmentName())) {

			Department dept = Department.builder().departmentId(deptBean.getDepartmentId())
					.departmentName(deptBean.getDepartmentName()).build();

			Department result = deptService.updateDept(dept);
			if (result != null) {
				if (oldName.equals(deptBean.getDepartmentName())) {
					ra.addFlashAttribute("message", "No Data Changed!");
				} else {
					ra.addFlashAttribute("message", "Successfully Updated.");
				}
			}

		} else {
			model.addAttribute("message", "Department already exists!");
			return new ModelAndView("departmentAction");
		}

		return new ModelAndView("redirect:/department");
	}

	@GetMapping("/deleteDepartment")
	public ModelAndView deleteDepartment(@RequestParam("id") long id, ModelMap model, RedirectAttributes ra) {

		Department dept = deptService.searhWithId(id);

		if (dept != null && dept.getTeams().size() > 0) {

			List<Team> teams = new ArrayList<>();

			for (Team t : dept.getTeams()) {
				teams.add(t);
			}

			model.addAttribute("message", "Can't Delete!");
			model.addAttribute("teams", teams);

			DepartmentBean deptBean = DepartmentBean.builder().departmentId(dept.getDepartmentId())
					.departmentName(dept.getDepartmentName()).build();
			return new ModelAndView("departmentAction", "department", deptBean);
		}

		deptService.deleteDept(id);
		return new ModelAndView("redirect:/department");
	}

//	@PostMapping("/searchDepartment")
//	public ModelAndView searchDepartment(@RequestParam("keyword") String keyword, ModelMap model) {
//		List<Department> depts = deptService.searchDept(keyword, keyword);
////		model.addAttribute("currentPage", 1);
////		model.addAttribute("totalPages", deptService.findTotalPages());
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("depts", depts);
//		return new ModelAndView("departmentControl", "department", new DepartmentBean());
//	}

}
