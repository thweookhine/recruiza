package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.Department;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.model.JobPositionBean;
import com.project.demo.model.JobPostBean;
import com.project.demo.model.RecruitementResourceBean;
import com.project.demo.model.TeamBean;
import com.project.demo.model.UserBean;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.JobPositionService;
import com.project.demo.service.JobPostService;
import com.project.demo.service.RecruitementResourceService;
import com.project.demo.service.TeamService;
import com.project.demo.service.UserService;

@RestController
public class JobPostController {

	@Autowired
	JobPostService jobPostService;

	@Autowired
	RecruitementResourceService resourceService;

	@Autowired
	TeamService teamService;

	@Autowired
	JobPositionService positionService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/jobPost")
	public ModelAndView toJobPost(ModelMap model, RedirectAttributes ra, JobPostBean jobPostBean) {
		List<JobPost> jobPosts = jobPostService.getAllJobPosts();
		model.addAttribute("jobPosts", jobPosts);
		return new ModelAndView("jobPostControl", "jobPost", jobPostBean);
	}

	@PostMapping(value = "/saveJobPost")
	public ModelAndView saveJobPost(@ModelAttribute("jobPost") @Validated JobPostBean jobPostBean,
			BindingResult bindingResult, HttpSession session,
			RedirectAttributes ra, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return toJobPost(model, ra, jobPostBean);
		}

		Team team = teamService.getById(jobPostBean.getTeamBean());
		RecruitementResource resource = resourceService.getResourceById(Long.parseLong(jobPostBean.getResourceBean()));
		JobPosition jobPosition = positionService.getPositionById(Long.parseLong(jobPostBean.getJobPositionBean()));

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		JobPost jobPost = JobPost.builder()
				.postName(jobPostBean.getPostName())
				.count(jobPostBean.getCount())
				.comment(jobPostBean.getComment())
				.foc(jobPostBean.isFoc())
				.postDate(LocalDate.parse(jobPostBean.getPostDate()))
				.dueDate(LocalDate.parse(jobPostBean.getDueDate()))
				.team(team)
				.resource(resource)
				.jobPosition(jobPosition)
				.user(user)
				.postCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.sheetId(jobPostBean.getSheetId())
				.postStatus("PENDING")
				.build();

		JobPost result = jobPostService.createJobPost(jobPost);
		if (result != null) {
			ra.addFlashAttribute("message", "Successfully Added");
		}

		return new ModelAndView("redirect:/jobPost");
	}
	

	@ModelAttribute("teamList")
	List<TeamBean> getAllTeams() {
		List<TeamBean> list = new ArrayList<>();
		List<Team> teams = teamService.getAllTeams();

		for (Team team : teams) {
			TeamBean teamBean = TeamBean.builder()
					.teamId(team.getTeamId())
					.teamCode(team.getTeamCode())
					.teamName(team.getTeamName())
					.departmentName(team.getDepartment().getDepartmentName())
					.build();
			list.add(teamBean);
		}

		return list;
	}

	@ModelAttribute("resourceList")
	List<RecruitementResourceBean> getResources() {
		List<RecruitementResourceBean> list = new ArrayList<>();
		List<RecruitementResource> resources = resourceService.getAllRecruitementResource();

		for (RecruitementResource r : resources) {

			RecruitementResourceBean rBean = RecruitementResourceBean.builder()
					.resourceId(r.getResourceId())
					.resourceCode(r.getResourceCode())
					.resourceName(r.getResourceName())
					.link(r.getLink())
					.address(r.getAddress())
					.resourceMobile(r.getResourceMobile())
					.contactPerson(r.getContactPerson())
					.recruitementType(r.getRecruitementType())
					.build();

			list.add(rBean);
		}
		return list;
	}

	@ModelAttribute("positionList")
	List<JobPositionBean> getPositions() {
		List<JobPositionBean> list = new ArrayList<>();
		List<JobPosition> positions = positionService.getAllJobPosition();
		for (JobPosition p : positions) {
			JobPositionBean jpBean = JobPositionBean.builder()
					.positionId(p.getPositionId())
					.positionCode(p.getPositionCode())
					.positionName(p.getPositionName())
					.build();
			list.add(jpBean);
		}
		return list;
	}

	@ModelAttribute("departmentList")
	List<Department> getDepartments() {
		return departmentService.readAllDepts();
	}

	@GetMapping("teamList/{departmentId}")
	@ResponseBody
	public List<Team> getTeams(@PathVariable("departmentId") long departmentId) {
		return departmentService.getTeamList(departmentId);
	}

}
