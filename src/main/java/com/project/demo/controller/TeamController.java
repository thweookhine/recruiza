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
import com.project.demo.model.TeamBean;
import com.project.demo.model.UserBean;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.HistoryService;
import com.project.demo.service.TeamService;
import com.project.demo.service.UserService;

@RestController
public class TeamController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private DepartmentService deptService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	@GetMapping("/team")
	public ModelAndView toTeam(ModelMap model, RedirectAttributes ra, TeamBean teamBean) {

		String keyword = null;

		return searchTeam(model, ra, teamBean, 1, "teamId", "asc", keyword);
	}

	@GetMapping(value = "/searchTeam/{pageNumber}")
	public ModelAndView searchTeam(ModelMap model, RedirectAttributes ra, TeamBean teamBean,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<Team> page = teamService.listAllTeams(currentPage, sortField, sortDir, keyword);
		long totalTeams = page.getTotalElements();
		int totalPages = page.getTotalPages();
		List<Team> teams = page.getContent();

		int index = Integer.parseInt((currentPage - 1) + "1");

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalTeams", totalTeams);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("teams", teams);

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return new ModelAndView("teamControl", "team", teamBean);

	}

	@PostMapping("/saveTeam")
	public ModelAndView saveTeam(@ModelAttribute("team") @Validated TeamBean teamBean, BindingResult bindingResult,
			RedirectAttributes ra, ModelMap model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return toTeam(model, ra, teamBean);
		}

		// if (teamBean.getDepartmentName().equals("none")) {
		// ra.addFlashAttribute("selectDept", "Please Select One Department!.");
		// return new ModelAndView("redirect:/team");
		// }

		Department dept = deptService.searchOneWithName(teamBean.getDepartmentName());

		Team result = teamService.searchWithNameAndDept(teamBean.getTeamName(), dept.getDepartmentId());

		if (result != null) {

			// Create History for Inserting existing data
			generateHistoryForTeam(result, session, "tried to insert existing data");
			ra.addFlashAttribute("message", "Same Team and department already exists");
		} else {
			// create
			Team team = Team.builder().teamName(teamBean.getTeamName()).department(dept).build();
			Team value = teamService.createTeam(team);
			if (value != null) {
				// Add
				generateHistoryForTeam(value, session, "added");
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
			BindingResult bindingResult, ModelMap model, RedirectAttributes ra, HttpSession session) {

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
				generateHistoryForTeam(result, session, "updated");
				ra.addFlashAttribute("message", "Successfully Updated.");
			}

		} else {
			if (teamBean.getTeamName().equals(oldName) && teamBean.getDepartmentName().equals(oldDeptName)) {
				ra.addFlashAttribute("message", "No Data Change");
			} else {
				generateHistoryForTeam(searchedTeam, session, "tried to update to existing data.");
				model.addAttribute("message", "Team already exists!");
				return new ModelAndView("teamAction");
			}

		}

		return new ModelAndView("redirect:/team");
	}

	@GetMapping("/deleteTeam")
	public ModelAndView deleteTeam(@RequestParam("id") long id, ModelMap model, RedirectAttributes ra,
			HttpSession session) {

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		try {
			Team t = teamService.getById(id);
			generateHistoryForTeam(t, session, "deleted");
			teamService.deleteTeam(id);

		} catch (Exception expection) {
			model.addAttribute("message", "You can't delete");
			Team team = teamService.getById(id);
			TeamBean teamBean = TeamBean.builder().teamId(id).teamCode(team.getTeamCode()).teamName(team.getTeamName())
					.departmentName(team.getDepartment().getDepartmentName()).build();
			generateHistoryForTeam(team, session, "Could not Delete");
			return new ModelAndView("teamAction", "team", teamBean);
		}
		ra.addFlashAttribute("message", "Delete successfully!");
		return new ModelAndView("redirect:/team");
	}

	@ModelAttribute("deptNameList")
	List<String> getDepNameList() {
		List<Department> depts = deptService.readAllDepts();
		List<String> deptNames = new ArrayList<>();
		for (Department d : depts) {
			deptNames.add(d.getDepartmentName());
		}
		return deptNames;
	}

	public void generateHistoryForTeam(Team team, HttpSession session, String action) {
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		String data = team.getTeamId() + "," + team.getTeamCode() + "," + team.getTeamName() + ","
				+ team.getDepartment().getDepartmentName();

		History history = History.builder()
				.user(user)
				.action(action)
				.dataName(team.getTeamName())
				.tableName("Team")
				.data(data)
				.historyCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.build();
		historyService.createHistory(history);

	}

}
