package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Applicant;
import com.project.demo.service.ApplicantService;


@RestController
public class AjaxController {
	
	@Autowired
	ApplicantService applicantService;
	
	@GetMapping(value = "/applicantList/{postId}")
	@ResponseBody
	public List<Applicant> getApplicants(@PathVariable("postId") long postId) {
		return applicantService.getApplicantList(postId);
	}
}









