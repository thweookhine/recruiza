package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.project.demo.entity.Applicant;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.model.ApplicantBean;
import com.project.demo.repository.ApplicantRepository;
import com.project.demo.service.ApplicantService;
import com.project.demo.service.JobPositionService;
import com.project.demo.service.JobPostService;
import com.project.demo.utils.CheckStatus;

@RestController
public class ApplicantController {
	
	@Autowired
	ApplicantService service;
	@Autowired
	ApplicantRepository repo;
	@Autowired
	JobPostService jobPostService;
	@Autowired
	JobPositionService jobPositionService;
	
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
    
    @GetMapping(value = "/applicantProcess")
    public ModelAndView applicantPro(ModelMap model) {
    	String keyword = "allApplicants";
    	return applicantProcess(1, keyword, "applicantId", "asc", model);
    }
    
    @GetMapping(value = "/applicantPro/{pageNumber}/{keyword}") 
    public ModelAndView applicantProcess(@PathVariable("pageNumber") int currentPage, @PathVariable("keyword") String keyword, 
    		@Param("sortField") String sortField,
			@Param("sortDir") String sortDir,
			ModelMap model) {
    	
    	Page<Applicant> aplicantPage = service.listApplicantProcess(currentPage, sortField, sortDir, keyword);
    	
    	long totalApplicants = aplicantPage.getTotalElements();
    	int totalPages = aplicantPage.getTotalPages();
    	
    	List<Applicant> list = aplicantPage.getContent();
    	
    	model.addAttribute("currentPage", currentPage);
    	model.addAttribute("totalApplicants", totalApplicants);
    	model.addAttribute("totalPages", totalPages);
    	model.addAttribute("applicants", list);
    	model.addAttribute("sortField", sortField);
    	model.addAttribute("sortDir", sortDir);
    	model.addAttribute("keyword", keyword);
    	
    	model.addAttribute("ct","Code Test");
    	model.addAttribute("ii","Intro Interview");
    	model.addAttribute("si","Second Interview");
    	model.addAttribute("jo","Job offer");
    	model.addAttribute("h","Hired");
    	
    	String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
    	model.addAttribute("reverseSortDir", reverseSortDir);
    			
    	return new ModelAndView("applicantProcess");
    }
    
    @GetMapping(value = "/nextProcess")
    public ModelAndView nextApplicantProcess(@RequestParam("id") Long applicantId,ModelMap model,HttpServletRequest request) {
    	
    	Applicant applicant = service.getApplicantById(applicantId);
    	model.addAttribute("nextStatus", CheckStatus.getApplicantStatus(applicant.getApplicantStatus()));
    	ApplicantBean applicantBean = ApplicantBean.builder()
    											   .applicantId(applicant.getApplicantId())
    											   .applicantName(applicant.getApplicantName())
    											   .applicantEmail(applicant.getApplicantEmail())
    											   .applicantMobile(applicant.getApplicantMobile())
    											   .address(applicant.getAddress())
    											   .link(applicant.getLink())
    											   .comment(applicant.getComment())
    											   .currentState(applicant.getCurrentState())
    											   .applicantStatus(applicant.getApplicantStatus())
    											   .jobPositionBean(applicant.getJobPosition().getPositionName())
    											   .jobPostBean(applicant.getJobPost().getPostName())
    											   .applyTime(applicant.getApplyTime().toString())
    											   .build();
    	return new ModelAndView("applicantChange", "bean", applicantBean);
    }
    
    @PostMapping(value = "/applicantStatusChange")
    public ModelAndView applicantStatusChange(@ModelAttribute("bean") ApplicantBean bean,HttpServletRequest request,ModelMap model) {
    	
    	Applicant applicant = service.changeApplicantStatus(bean, request.getParameter("checkStatus"), request.getParameter("getStatus"),request.getParameter("comment"));
    	
    	if (applicant != null) {
    		model.addAttribute("msg", "Applicant Status Change Successful !");
    	}else {
    		model.addAttribute("error", "Applicant Status Change Failed !");
    	}
    			
    	return applicantPro(model);
    }
  
    @ModelAttribute("jobPostList")
    public List<JobPost> getJobPostList() {
    	return jobPostService.getAllJobPosts();
    	
    }
    @ModelAttribute("positionList")
    public List<JobPosition> getAllPositionList(){
    	return jobPositionService.getAllJobPosition();
    }
    
    @PostMapping(value="/saveapplicant")
    public ModelAndView saveApplicant(@ModelAttribute("applicant")@Validated ApplicantBean applicantBean,BindingResult bindingResult,HttpSession session
    		,RedirectAttributes ra,ModelMap model) {
    	
    	if(bindingResult.hasErrors()) {
    		return new ModelAndView("redirect:/applicant");
    	}
    	
    	JobPost jobPost = jobPostService.getByid(Long.parseLong(applicantBean.getJobPostBean()));
    	
    	JobPosition jobPosition = jobPositionService.getPositionById(Long.parseLong(applicantBean.getJobPositionBean()));
    	
    	Applicant applicant = Applicant.builder()
    			.applicantName(applicantBean.getApplicantName())
    			.applicantEmail(applicantBean.getApplicantEmail())
    			.applicantMobile(applicantBean.getApplicantMobile())
    			.address(applicantBean.getAddress())
    			.link(applicantBean.getLink())
    			.comment(applicantBean.getComment())
    			.applyTime(Timestamp.valueOf(LocalDateTime.now()))
    			.applicantStatus("Intro Interview")
    			.currentState("PENDING")
    			.jobPosition(jobPosition)
    			.jobPost(jobPost)
    			.build();
    	
    	Applicant result = service.createApplicant(applicant);
    	
    	if(result != null) {
    		ra.addFlashAttribute("message","Successfully Added.");
    	}
    	
    	return new ModelAndView("redirect:/applicant");
    }
    
    @GetMapping(value="/editapplicant")
    public ModelAndView editApplicant(@RequestParam("id")Long id,ModelMap model) {
    	
    	Applicant applicant=service.getApplicantById(id);
    	
    	ApplicantBean bean = ApplicantBean.builder()
    			.applicantId(applicant.getApplicantId())
    			.applicantName(applicant.getApplicantName())
    			.applicantEmail(applicant.getApplicantEmail())
    			.applicantMobile(applicant.getApplicantMobile())
    			.address(applicant.getAddress())
    			.link(applicant.getLink())
    			.comment(applicant.getComment())
    			//.applyTime(Timestamp.valueOf(LocalDateTime.now()))
    			//.applicantStatus("PENDING")
    			//.currentState("none")
    			//.jobPosition(applicant.get)
    			//.jobPost(jobPost)
    			.build();
    	return new ModelAndView("editApplicantControl","applicant",bean);
    }

    @GetMapping(value="/deleteapplicant")
    public ModelAndView deleteApplicant(@RequestParam("id")Long id,RedirectAttributes ra) {
    	ra.addFlashAttribute("message","Successfully!");
    	
    	Applicant applicant = service.getApplicantById(id);
    	
    	applicant.setCurrentState("DEACTIVITE");
    	
    	service.updateApplicant(applicant);
    	ra.addFlashAttribute("message","Successfully!");
    	return new ModelAndView("redirect:/applicant");
    }
}
