package com.project.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.JobPost;
import com.project.demo.service.JobPostService;
import com.project.demo.service.JobPostServiceImpl;

@RestController
public class MarketingController {

	@Autowired
	JobPostService jobPostService;

	@GetMapping("/marketing")
	public ModelAndView toMarketing(ModelMap model, RedirectAttributes ra) {

		String keyword = null;

		return showJobPosts(model, ra, 1, "id", "asc", keyword);
	}

	@GetMapping(value = "/showJobPosts/{pageNumber}")
	public ModelAndView showJobPosts(ModelMap model, RedirectAttributes ra,
			@PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<JobPost> page = jobPostService.searchPostedJobPosts(currentPage, sortField, sortDir);

		long totalJobPosts = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<JobPost> postedJobPosts = page.getContent();
		
		List<JobPost> pendingJobPosts = jobPostService.searchWithStatus("PENDING");

		int index = Integer.parseInt((currentPage - 1) + "1");

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
		
		model.addAttribute("pendingJobPosts",pendingJobPosts);

		return new ModelAndView("marketingControl");

	}

	// @GetMapping("/marketing")
	// public ModelAndView toMarketing(Model model) {
	// List<JobPost> jobPosts = jobPostService.searchWithStatus("PENDING");
	// model.addAttribute("jobPosts", jobPosts);

	// return new ModelAndView("marketingControl");
	// }

	@GetMapping(value = "/postJP")
	public ModelAndView toPostJP(@RequestParam("jobPostId") long postId) {
		JobPost post = jobPostService.getByid(postId);
		return new ModelAndView("postAction", "jobPost", post);
	}

	@PostMapping(value = "/postJP")
	public ModelAndView postJP(
			Model model,
			@RequestParam("jobPostId") long postId,
			@RequestParam("postDate") String postDate,
			@RequestParam("dueDate") String dueDate,
			@RequestParam("sheetId") String sheetId) {

		JobPost jobPost = jobPostService.getByid(postId);
		jobPost.setPostDate(LocalDate.parse(postDate));
		jobPost.setDueDate(LocalDate.parse(dueDate));
		jobPost.setSheetId(sheetId);
		jobPost.setPostStatus("POSTED");
		JobPost result = jobPostService.updateJobPost(jobPost);
		if (result != null) {
			model.addAttribute("message", "Successfully Posted.");
		}

		return new ModelAndView("redirect:/marketing");
	}
	
}
