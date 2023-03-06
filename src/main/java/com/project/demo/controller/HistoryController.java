package com.project.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.Department;
import com.project.demo.entity.History;
import com.project.demo.entity.User;
import com.project.demo.model.DepartmentBean;
import com.project.demo.model.UserBean;
import com.project.demo.service.HistoryService;
import com.project.demo.service.UserService;

@RestController
public class HistoryController {

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/history")
	public ModelAndView toHistory(ModelMap model, RedirectAttributes ra,HttpSession session) {
		String keyword = null;
		return searchHistory(model, ra,session, 1, "historyId", "asc", keyword);
	}
	
	
	@GetMapping(value = "/searchHistory/{pageNumber}")
	public ModelAndView searchHistory(ModelMap model, RedirectAttributes ra,HttpSession session,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());
		Page<History> page = historyService.listAllHistory(currentPage, sortField, sortDir, keyword,user.getUserName());

		long totalHistories = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<History> histories = page.getContent();

		int index = Integer.parseInt((currentPage - 1) + "1");

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalHistories", totalHistories);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("histories", histories);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		// DepartmentBean deptBean = DepartmentBean.builder()
		// .build();
		//
		
		return new ModelAndView("historyControl");

	}
}
