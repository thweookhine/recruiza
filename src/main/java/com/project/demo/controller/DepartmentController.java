package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable.BindRestriction;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.demo.entity.Department;
import com.project.demo.model.DepartmentBean;
import com.project.demo.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;

	@GetMapping("/department")
	public ModelAndView toDepartment(ModelMap model) {
		List<Department> depts = deptService.getAllDepts(0);
		model.addAttribute("currentPage", 1);
		model.addAttribute("totalPages", deptService.findTotalPages());
		model.addAttribute("depts", depts);
		return new ModelAndView("departmentControl", "department", new DepartmentBean());
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
			BindingResult bindingResult, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("departmentControl");
		}

		Department dept = Department.builder().departmentName(departmentBean.getDepartmentName()).build();

		Department result = deptService.createDept(dept);
		if (result != null) {
			model.addAttribute("message", "Successfully Added.");
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
			BindingResult bindingResult, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("departmentControl");
		}

		Department dept = Department.builder().departmentId(deptBean.getDepartmentId())
				.departmentName(deptBean.getDepartmentName()).build();

		Department result = deptService.updateDept(dept);
		if (result != null) {
			model.addAttribute("message", "Successfully Added.");
		}

		return new ModelAndView("redirect:/department");
	}

	@GetMapping("/deleteDepartment")
	public ModelAndView deleteDepartment(@RequestParam("id") long id) {
		deptService.deleteDept(id);
		return new ModelAndView("redirect:/department");
	}

	@PostMapping("/searchDepartment")
	public ModelAndView searchDepartment(@RequestParam("keyword") String keyword, ModelMap model) {
		List<Department> depts = deptService.searchDept(keyword, keyword);
//		model.addAttribute("currentPage", 1);
//		model.addAttribute("totalPages", deptService.findTotalPages());
		model.addAttribute("keyword",keyword);
		model.addAttribute("depts", depts);
		return new ModelAndView("departmentControl", "department", new DepartmentBean());
	}

}
