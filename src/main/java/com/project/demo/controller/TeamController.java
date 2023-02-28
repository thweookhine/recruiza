package com.project.demo.controller;

import java.sql.SQLIntegrityConstraintViolationException;
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
import com.project.demo.model.TeamBean;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.TeamService;

@RestController
public class TeamController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private DepartmentService deptService;

	@GetMapping("/team")
	public ModelAndView toTeam(ModelMap model, RedirectAttributes ra,TeamBean teamBean) {

//		List<Team> teams = teamService.getAllTeams(0);
//		model.addAttribute("currentPage", 1);
//		model.addAttribute("totalPages", teamService.findTotalPages());
//		model.addAttribute("teams", teams);
//
//		return new ModelAndView("teamControl", "team", new TeamBean());

		String keyword = null;
	
		return searchTeam(model, ra,teamBean, 1, "teamId", "asc", keyword);
	}

	@GetMapping(value = "/searchTeam/{pageNumber}")
	public ModelAndView searchTeam(ModelMap model, RedirectAttributes ra,TeamBean teamBean ,@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<Team> page = teamService.listAllTeams(currentPage, sortField, sortDir, keyword);
		long totalTeams = page.getTotalElements();
		int totalPages = page.getTotalPages();
		List<Team> teams = page.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalDepts", totalTeams);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("teams", teams);
		System.out.println("show all");

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return new ModelAndView("teamControl", "team", teamBean);

	}

//	@GetMapping("/nextTeamPage")
//	public ModelAndView nextTeamPage(@RequestParam("currentPage") String cp, ModelMap model) {
//		int currentPage = Integer.parseInt(cp);
//		List<Team> teams = teamService.getAllTeams(currentPage);
//
//		model.addAttribute("currentPage", currentPage + 1);
//		model.addAttribute("totalPages", teamService.findTotalPages());
//		model.addAttribute("teams", teams);
//
//		return new ModelAndView("teamControl", "team", new TeamBean());
//	}

//	@GetMapping("/prevTeamPage")
//	public ModelAndView prevTeamPage(@RequestParam("currentPage") String cp, ModelMap model) {
//		int currentPage = Integer.parseInt(cp);
//		List<Team> teams = teamService.getAllTeams();
//		model.addAttribute("currentPage", currentPage - 1);
//		model.addAttribute("totalPages", teamService.findTotalPages());
//		model.addAttribute("teams", teams);
//		return new ModelAndView("teamControl", "team", new TeamBean());
//	}

	@PostMapping("/saveTeam")
	public ModelAndView saveTeam(@ModelAttribute("team") @Validated TeamBean teamBean, BindingResult bindingResult,
			RedirectAttributes ra, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return toTeam(model, ra, teamBean);
		}

		if (teamBean.getDepartmentName().equals("none")) {
			ra.addFlashAttribute("selectDept", "Please Select One Department!.");
			return new ModelAndView("redirect:/team");
		}

		Department dept = deptService.searchOneWithName(teamBean.getDepartmentName());

		Team result = teamService.searchWithNameAndDept(teamBean.getTeamName(), dept.getDepartmentId());

		if (result != null) {
			ra.addFlashAttribute("message", "Same Team and department already exists");
		} else {
			// create
			Team team = Team.builder().teamName(teamBean.getTeamName()).department(dept).build();
			Team value = teamService.createTeam(team);
			if (value != null) {
				ra.addFlashAttribute("message", "Successfully Added");
			}
		}
		return new ModelAndView("redirect:/team");
	}

	@GetMapping("/updateTeam")
	public ModelAndView toUpdateTeamPage(@RequestParam("id") long id, ModelMap model) {
		Team team = teamService.getById(id);
		TeamBean teamBean = TeamBean.builder().teamId(id).teamCode(team.getTeamCode()).teamName(team.getTeamName())
				.departmentName(team.getDepartment().getDepartmentName()).build();
		return new ModelAndView("teamAction", "team", teamBean);
	}

	@PostMapping("/updateTeam")
	public ModelAndView updateTeam(@ModelAttribute("team") @Validated TeamBean teamBean,
			@RequestParam("oldName") String oldName, @RequestParam("oldDeptName") String oldDeptName,
			BindingResult bindingResult, ModelMap model, RedirectAttributes ra) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("teamControl");
		}

		Department dept = deptService.searchOneWithName(teamBean.getDepartmentName());
		Team searchedTeam = teamService.searchWithNameAndDept(teamBean.getTeamName(), dept.getDepartmentId());

		if (searchedTeam == null) {

			// Update Team
			Team team = Team.builder().teamId(teamBean.getTeamId()).teamName(teamBean.getTeamName()).department(dept)
					.build();
			Team result = teamService.updateTeam(team);
			if (result != null) {

				ra.addFlashAttribute("message", "Successfully Updated.");
			}

		} else {
			if (teamBean.getTeamName().equals(oldName) && teamBean.getDepartmentName().equals(oldDeptName)) {

				ra.addFlashAttribute("message", "No Data Change");
			} else {
				model.addAttribute("message", "Team already exists!");
				return new ModelAndView("teamAction");
			}

		}

		return new ModelAndView("redirect:/team");
	}

	@GetMapping("/deleteTeam")
	public ModelAndView deleteTeam(@RequestParam("id") long id, ModelMap model,RedirectAttributes ra) {
		try {
			teamService.deleteTeam(id);
		}catch(Exception expection) {
			model.addAttribute("message","You can't delete");
			Team team = teamService.getById(id);
			TeamBean teamBean = TeamBean.builder().teamId(id).teamCode(team.getTeamCode()).teamName(team.getTeamName())
					.departmentName(team.getDepartment().getDepartmentName()).build();
			return new ModelAndView("teamAction", "team", teamBean);
		}
		ra.addFlashAttribute("message","Delete successfully!");
		return new ModelAndView("redirect:/team");
	}
//
//	@PostMapping("/searchTeam")
//	public ModelAndView searchTeam(@RequestParam("keyword") String keyword,
//			@RequestParam("departmentName") String deptName, ModelMap model) {
//		List<Team> teams = new ArrayList<>();
//		if (keyword.isEmpty() && deptName.equals("none")) {
//			teams.addAll(teamService.getAllTeams(0));
//		} else {
//			if (!keyword.isEmpty()) {
//				teams = teamService.searchTeam(keyword, keyword);
//			}
//			if (!deptName.isEmpty()) {
//				List<Department> depts = deptService.searchDept(deptName, deptName);
//				for (Department d : depts) {
//					for (Team t : d.getTeams()) {
//						teams.add(t);
//					}
//				}
//			}
//		}
//		teams = teams.stream().distinct().toList();
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("deptName", deptName);
//		model.addAttribute("teams", teams);
////		model.addAttribute("currentPage",1);
////		model.addAttribute("totalPages",teamService.findTotalPages());
//
//		return new ModelAndView("teamControl", "team", new TeamBean());
//	}

	@ModelAttribute("deptNameList")
	List<String> getDepNameList() {
		List<Department> depts = deptService.readAllDepts();
		List<String> deptNames = new ArrayList<>();
		for (Department d : depts) {
			deptNames.add(d.getDepartmentName());
		}
		return deptNames;
	}

}
