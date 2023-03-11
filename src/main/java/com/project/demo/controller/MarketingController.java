package com.project.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.demo.entity.JobPost;
import com.project.demo.service.JobPostService;

@RestController
public class MarketingController {
	
	@Autowired
	JobPostService jobPostService;
	
	@GetMapping("/marketing")
	public ModelAndView toMarketing(Model model) {
		List<JobPost> jobPosts = jobPostService.searchWithStatus("PENDING");
		model.addAttribute("jobPosts",jobPosts);
		return new ModelAndView("marketingControl");
	}
	
	@GetMapping(value="/postJP")
	public ModelAndView toPostJP(@RequestParam("jobPostId")long postId) {
		JobPost post = jobPostService.getByid(postId);
		return new ModelAndView("postAction","jobPost",post);
	}
	
	@PostMapping(value = "/postJP")
	public ModelAndView postJP(
			Model model,
			@RequestParam("jobPostId")long postId,
			@RequestParam("postDate")String postDate,
			@RequestParam("dueDate")String dueDate,
			@RequestParam("sheetId")String sheetId) {
		
		JobPost jobPost = jobPostService.getByid(postId);
		jobPost.setPostDate(LocalDate.parse(postDate));
		jobPost.setDueDate(LocalDate.parse(dueDate));
		jobPost.setSheetId(sheetId);
		jobPost.setPostStatus("POSTED");
		JobPost result = jobPostService.updateJobPost(jobPost);
		if(result != null) {
			model.addAttribute("message","Successfully Posted.");
		}
		
		return new ModelAndView("redirect:/marketing");
	}
	

}
