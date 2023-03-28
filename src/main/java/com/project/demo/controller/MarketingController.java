package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.History;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.User;
import com.project.demo.model.UserBean;
import com.project.demo.service.HistoryService;
import com.project.demo.service.JobPostService;
import com.project.demo.service.UserService;

@RestController
public class MarketingController {

	@Autowired
	JobPostService jobPostService;

	@Autowired
	UserService userService;

	@Autowired
	HistoryService historyService;

	@GetMapping("/market/marketing")
	public ModelAndView toMarketing(ModelMap model, RedirectAttributes ra) {

		String keyword = "";

		return showJobPosts(model, ra, 1, "id", "asc", keyword);
	}

	@GetMapping(value = "/market/showJobPosts/{pageNumber}")
	public ModelAndView showJobPosts(ModelMap model, RedirectAttributes ra,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<JobPost> page = jobPostService.searchJobPostsWithoutPending(currentPage, sortField, sortDir, keyword);

		long totalJobPosts = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<JobPost> postedJobPosts = page.getContent();

		List<JobPost> pendingJobPosts = jobPostService.searchWithStatus("PENDING");

		int index = Integer.parseInt((currentPage - 1) + "1");

		model.addAttribute("totalPosted", jobPostService.getCountByType("POSTED"));
		model.addAttribute("totalPending", jobPostService.getCountByType("PENDING"));
		model.addAttribute("totalClosed", jobPostService.getCountByType("CLOSED"));
		model.addAttribute("totalDued", jobPostService.getCountByType("DUED"));

		System.out.println("total job posts" + totalJobPosts);

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalJobPosts", totalJobPosts);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("postedJobPosts", postedJobPosts);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		model.addAttribute("pendingJobPosts", pendingJobPosts);

		return new ModelAndView("marketingControl");

	}

	@GetMapping(value = "/market/postJP")
	public ModelAndView toPostJP(@RequestParam("jobPostId") long postId) {
		JobPost post = jobPostService.getByid(postId);
		return new ModelAndView("postAction", "jobPost", post);
	}

	@PostMapping(value = "/market/postJP")
	public ModelAndView postJP(
			Model model,
			@RequestParam("jobPostId") String postId,
			@RequestParam("postDate") String postDate,
			@RequestParam("dueDate") String dueDate,
			@RequestParam("sheetId") String sheetId,
			HttpSession session) {

		JobPost jobPost = jobPostService.getByid(Long.parseLong(postId));
		jobPost.setPostDate(LocalDate.parse(postDate));
		jobPost.setDueDate(LocalDate.parse(dueDate));
		jobPost.setSheetId(sheetId);
		jobPost.setPostStatus("POSTED");

		JobPost result = jobPostService.updateJobPost(jobPost);
		if (result != null) {
			generateHistoryForJobPost(result, session, "posted");
			model.addAttribute("message", "Successfully Posted.");
		}

		return new ModelAndView("redirect:/market/marketing");
	}

	public void generateHistoryForJobPost(JobPost jobPost, HttpSession session, String action) {
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		String data = jobPost.getPostId() + "," + jobPost.getPostCode() + "," + jobPost.getPostName() + ","
				+ jobPost.getCount() + "," + jobPost.getComment() + ","
				+ jobPost.isFoc() + "," + jobPost.getPostDate() + ","
				+ jobPost.getDueDate() + "," + jobPost.getTeam().getTeamName() + ","
				+ jobPost.getResource().getResourceName() + "," + jobPost.getJobPosition().getPositionName() + ","
				+ jobPost.getPostStatus() + "," + jobPost.getSheetId();

		History history = History.builder()
				.user(user)
				.action(action)
				.dataName(jobPost.getPostName())
				.tableName("Job Post")
				.data(data)
				.historyCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.build();
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
