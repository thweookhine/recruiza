package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.project.demo.entity.History;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.model.DepartmentBean;
import com.project.demo.model.UserBean;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.HistoryService;
import com.project.demo.service.UserService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	@GetMapping("/department")
	public ModelAndView toDepartment(ModelMap model, RedirectAttributes ra, DepartmentBean deptBean) {
		String keyword = null;

		return searchDept(model, deptBean, ra, 1, "departmentId", "asc", keyword);
	}

	@GetMapping(value = "/searchDepartment/{pageNumber}")
	public ModelAndView searchDept(ModelMap model, DepartmentBean deptBean, RedirectAttributes ra,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<Department> page = deptService.listAllDepts(currentPage, sortField, sortDir, keyword);

		long totalDepts = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<Department> depts = page.getContent();

		int index = Integer.parseInt((currentPage - 1) + "1");

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalDepts", totalDepts);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("depts", depts);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return new ModelAndView("departmentControl", "department", deptBean);

	}

	@PostMapping("/saveDepartment")
	public ModelAndView saveDepartment(@ModelAttribute("department") @Validated DepartmentBean departmentBean,
			BindingResult bindingResult, HttpSession session, ModelMap model, RedirectAttributes ra) {

		if (bindingResult.hasErrors()) {
			return toDepartment(model, ra, departmentBean);
		}

		Department dept = deptService.searchOneWithName(departmentBean.getDepartmentName());

		if (dept == null) {

			// Created

			Department dept2 = Department.builder().departmentName(departmentBean.getDepartmentName()).build();
			Department result = deptService.createDept(dept2);

			if (result != null) {
				generateHistoryForDept(result, session, "added");
				ra.addFlashAttribute("message", "Successfully Added.");

			}
		} else {
			generateHistoryForDept(dept, session, "tried to insert existing data");
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
			RedirectAttributes ra, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("departmentAction");
		}

		Department searchedDept = deptService.searchOneWithName(deptBean.getDepartmentName());
		if (searchedDept == null || oldName.equals(deptBean.getDepartmentName())) {

			Department dept = Department.builder().departmentId(deptBean.getDepartmentId())
					.departmentName(deptBean.getDepartmentName()).build();

			if (oldName.equals(deptBean.getDepartmentName())) {
				ra.addFlashAttribute("message", "No Data Changed!");

			} else {
				Department result = deptService.updateDept(dept);
				if (result != null) {
					generateHistoryForDept(result, session, "updated");
					ra.addFlashAttribute("message", "Successfully Updated.");
				}
			}

		} else {
			generateHistoryForDept(searchedDept, session, "tried to update existing data");
			model.addAttribute("message", "Department already exists!");
			return new ModelAndView("departmentAction");
		}

		return new ModelAndView("redirect:/department");
	}

	@GetMapping("/deleteDepartment")
	public ModelAndView deleteDepartment(@RequestParam("id") long id, ModelMap model, RedirectAttributes ra,
			HttpSession session) {

		Department dept = deptService.searhWithId(id);

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		if (dept != null && dept.getTeams().size() > 0) {

			List<Team> teams = new ArrayList<>();

			for (Team t : dept.getTeams()) {
				teams.add(t);
			}

			generateHistoryForDept(dept, session, "tried to delete department having teams.");
			model.addAttribute("message", "Can't Delete!");
			model.addAttribute("teams", teams);

			DepartmentBean deptBean = DepartmentBean.builder().departmentId(dept.getDepartmentId())
					.departmentName(dept.getDepartmentName()).build();
			return new ModelAndView("departmentAction", "department", deptBean);
		}

		deptService.deleteDept(id);
		generateHistoryForDept(dept, session, "deleted");
		ra.addFlashAttribute("message", "Successfully Deleted!.");
		return new ModelAndView("redirect:/department");
	}

	public void generateHistoryForDept(Department dept, HttpSession session, String action) {
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		String data = dept.getDepartmentId() + "," + dept.getDepartmentCode() + "," + dept.getDepartmentName();

		History history = History.builder()
				.user(user)
				.action(action)
				.dataName(dept.getDepartmentName())
				.tableName("Department")
				.data(data)
				.historyCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.build();
		historyService.createHistory(history);
	}

}
