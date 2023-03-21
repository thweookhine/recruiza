package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.Department;
import com.project.demo.entity.History;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.model.JobPostBean;
import com.project.demo.model.UserBean;
import com.project.demo.service.ApplicantService;
import com.project.demo.service.DepartmentService;
import com.project.demo.service.HistoryService;
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

	@Autowired
	HistoryService historyService;
	
	@Autowired
	ApplicantService applicantService;

	@GetMapping("/jobPost")
	public ModelAndView toJobPost(ModelMap model, RedirectAttributes ra, JobPostBean jobPostBean) {

		String keyword = null;
		String status = "";
		return searchJobPost(model, jobPostBean, ra, 1, "postId", "asc", keyword, status);
	}

	@GetMapping(value = "/searchJobPost/{pageNumber}")
	public ModelAndView searchJobPost(ModelMap model, JobPostBean jobPostBean, RedirectAttributes ra,
			@PathVariable("pageNumber") int currentPage, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword,
			@RequestParam("postStatus") String status) {

		if (status != null && status.equals("ALL")) {
			status = "";
		}

		Page<JobPost> page = jobPostService.listAllJobPosts(currentPage, sortField, sortDir, keyword, status);

		long totalJobPosts = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<JobPost> jobPosts = page.getContent();

		Map<Long, Integer> map = new HashMap<>();

		for (JobPost jp : jobPosts) {
			int count = applicantService.getCountByJobPost(jp.getPostId());
			map.put((long) jp.getPostId(), count);
		}
		
		model.addAttribute("countMap",map);

		int index = Integer.parseInt((currentPage - 1) + "1");

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalJobPosts", totalJobPosts);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("jobPosts", jobPosts);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("postStatus", status.isEmpty() ? "ALL" : status);
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return new ModelAndView("jobPostControl", "jobPost", jobPostBean);

	}

	@PostMapping(value = "/searchPostedJobPost")
	public ModelAndView searchPostedJobPost(ModelMap model, RedirectAttributes ra,
			@RequestParam("keyword") String keyword, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {

		List<JobPost> jobPosts = jobPostService.findJobPost(keyword, startDate, endDate);

		model.addAttribute("jobPosts", jobPosts);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return new ModelAndView("home");

	}

	@PostMapping(value = "/saveJobPost")
	public ModelAndView saveJobPost(@ModelAttribute("jobPost") @Validated JobPostBean jobPostBean,
			BindingResult bindingResult, HttpSession session, RedirectAttributes ra, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return toJobPost(model, ra, jobPostBean);
		}

		Team team = teamService.getById(jobPostBean.getTeamBean());
		RecruitementResource resource = resourceService.getResourceById(jobPostBean.getResourceId());
		JobPosition jobPosition = positionService.getPositionById(jobPostBean.getJobPositionId());

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		JobPost jobPost = JobPost.builder().postName(jobPostBean.getPostName()).count(jobPostBean.getCount())
				.comment(jobPostBean.getComment()).foc(jobPostBean.isFoc()).team(team).resource(resource)
				.jobPosition(jobPosition).user(user).postCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.postStatus("PENDING").build();

		JobPost result = jobPostService.createJobPost(jobPost);
		if (result != null) {
			generateHistoryForJobPost(result, session, "Added");
			ra.addFlashAttribute("message", "Successfully Added");
		}

		return new ModelAndView("redirect:/jobPost");
	}

	@GetMapping(value = "/updateJobPost")
	public ModelAndView toUpdateJobPost(@RequestParam("jobPostId") long id, ModelMap model) {
		JobPost post = jobPostService.getByid(id);

		JobPostBean jobPostBean = JobPostBean.builder().postId(id).postCode(post.getPostCode())
				.postName(post.getPostName()).count(post.getCount()).comment(post.getComment()).foc(post.isFoc())
				.teamBean(post.getTeam().getTeamId()).departmentId(post.getTeam().getDepartment().getDepartmentId())
				.resourceId(post.getResource().getResourceId()).postStatus(post.getPostStatus())
				.jobPositionId(post.getJobPosition().getPositionId()).build();
		return new ModelAndView("jobPostAction", "jobPost", jobPostBean);
	}

	@PostMapping(value = "/updateJobPost")
	public ModelAndView updateJobPost(@ModelAttribute("jobPost") @Validated JobPostBean jobPostBean,
			BindingResult bindingResult, ModelMap model, RedirectAttributes ra, HttpSession session) {

		Team team = teamService.getById(jobPostBean.getTeamBean());
		RecruitementResource resource = resourceService.getResourceById(jobPostBean.getResourceId());
		JobPosition position = positionService.getPositionById(jobPostBean.getJobPositionId());

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		JobPost jobPost = JobPost.builder().postId(jobPostBean.getPostId()).postName(jobPostBean.getPostName())
				.count(jobPostBean.getCount()).comment(jobPostBean.getComment()).foc(jobPostBean.isFoc()).team(team)
				.user(user).resource(resource).jobPosition(position).postStatus(jobPostBean.getPostStatus()).build();
		JobPost result = jobPostService.updateJobPost(jobPost);

		if (result != null) {
			generateHistoryForJobPost(jobPost, session, "updated");
			ra.addFlashAttribute("message", "Successfully Updated.");
		}

		return new ModelAndView("redirect:/jobPost");
	}

	@GetMapping(value = "/closeJobPost")
	public ModelAndView closeJobPost(@RequestParam("jobPostId") long id, ModelMap model, RedirectAttributes ra,
			HttpSession session) {

		JobPost jobPost = jobPostService.getByid(id);
		jobPost.setPostStatus("CLOSED*");
		JobPost result = jobPostService.updateJobPost(jobPost);
		if (result != null) {
			generateHistoryForJobPost(jobPost, session, "closed");
			ra.addFlashAttribute("message", "Successfully Closed.");
		}

		return new ModelAndView("redirect:/jobPost");
	}

	@GetMapping(value = "/openJobPost")
	public ModelAndView openJobPost(@RequestParam("jobPostId") long id, ModelMap model, RedirectAttributes ra,
			HttpSession session) {

		JobPost jobPost = jobPostService.getByid(id);
		jobPost.setPostStatus("POSTED");
		JobPost result = jobPostService.updateJobPost(jobPost);
		if (result != null) {
			generateHistoryForJobPost(jobPost, session, "opened");
			ra.addFlashAttribute("message", "Successfully Opened.");
		}

		return new ModelAndView("redirect:/jobPost");
	}

	@ModelAttribute("teamList")
	List<Team> getAllTeams() {
		return teamService.getAllTeams();
	}

	@ModelAttribute("resourceList")
	List<RecruitementResource> getResources() {
		return resourceService.getAllRecruitementResource();
	}

	@ModelAttribute("positionList")
	List<JobPosition> getPositions() {
		return positionService.getAllJobPosition();
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

	public void generateHistoryForJobPost(JobPost jobPost, HttpSession session, String action) {
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		String data = jobPost.getPostId() + "," + jobPost.getPostCode() + "," + jobPost.getPostName() + ","
				+ jobPost.getCount() + "," + jobPost.getComment() + "," + jobPost.isFoc() + "," + jobPost.getPostDate()
				+ "," + jobPost.getDueDate() + "," + jobPost.getTeam().getTeamName() + ","
				+ jobPost.getResource().getResourceName() + "," + jobPost.getJobPosition().getPositionName() + ","
				+ jobPost.getPostStatus() + "," + jobPost.getSheetId();

		History history = History.builder().user(user).action(action).dataName(jobPost.getPostName())
				.tableName("Job Post").data(data).historyCreatedTime(Timestamp.valueOf(LocalDateTime.now())).build();
		historyService.createHistory(history);
	}

	@ModelAttribute("postStatusList")
	public List<String> getAllPostStatus() {
		List<String> list = new ArrayList<>();
		list.add("ALL");
		list.add("PENDING");
		list.add("POSTED");
		list.add("CLOSED");
		list.add("CLOSED*");
		return list;
	}

}
