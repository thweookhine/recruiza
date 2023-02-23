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

import com.project.demo.entity.Applicant;
import com.project.demo.model.ApplicantBean;
import com.project.demo.repository.ApplicantRepository;
import com.project.demo.service.ApplicantService;

@RestController
public class ApplicantController {
	
	@Autowired
	ApplicantService service;
	@Autowired
	ApplicantRepository repo;
	
	@GetMapping(value="/applicant")
    public ModelAndView addApplicant(ModelMap model,RedirectAttributes ra, HttpSession session,ApplicantBean beanApplicant) {
    	String keyword = null;
    	return searchAppliicant(model, ra, session, beanApplicant, 1, "applicantId", "asc", keyword);
    }
    
    @GetMapping(value = "/searchApplicants/{pageNumber}")
    public ModelAndView searchAppliicant(ModelMap model,RedirectAttributes ra,HttpSession session,ApplicantBean beanApplicant,
    							@PathVariable("pageNumber") int currentPage,
    							@Param("sortField") String sortField,
    							@Param("sortDir") String sortDir,
    							@Param("keyword") String keyword) {
    	
                	
        	Page<Applicant> page = service.listAllApplicants(currentPage, sortField, sortDir, keyword);
        	
        	long totalApplicants = page.getTotalElements();
        	int totalPages = page.getTotalPages();
        	
        	List<Applicant> list = page.getContent();
        	
        	model.addAttribute("currentPage", currentPage);
        	model.addAttribute("totalApplicants", totalApplicants);
        	model.addAttribute("totalPages", totalPages);
        	model.addAttribute("list", list);
        	model.addAttribute("sortField", sortField);
        	model.addAttribute("sortDir", sortDir);
        	model.addAttribute("keyword", keyword);
        	
        	String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        	model.addAttribute("reverseSortDir", reverseSortDir);
        
        	return new ModelAndView("applicantControl","applicant",beanApplicant);
        }
}
