package com.project.demo.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
    public ModelAndView addApplicant(@RequestParam("jobPostId")Long id,ModelMap model,RedirectAttributes ra, HttpSession session) {
    	String keyword = null;
    
    	JobPost jobPost=jobPostService.getByid(id);
    	
    	return searchAppliicant(model, ra, session, jobPost );
    }
   
    @GetMapping(value = "/searchApplicants/{pageNumber}")
    public ModelAndView searchAppliicant(ModelMap model,RedirectAttributes ra,HttpSession session,JobPost jobPost) {
    	
                	
        	//Page<Applicant> page = service.listAllApplicants(currentPage, sortField, sortDir, keyword);
        	
        	//long totalApplicants = page.getTotalElements();
        	//int totalPages = page.getTotalPages();
        	
        	//List<Applicant> list = page.getContent();
        	
//        	model.addAttribute("currentPage", currentPage);
//        	model.addAttribute("totalApplicants", totalApplicants);
//        	model.addAttribute("totalPages", totalPages);
//        	model.addAttribute("list", list);
//        	model.addAttribute("sortField", sortField);
//        	model.addAttribute("sortDir", sortDir);
//        	model.addAttribute("keyword", keyword);
        	
        	//String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        	//model.addAttribute("reverseSortDir", reverseSortDir);
        
        	return new ModelAndView("applicantControl","jobPost",jobPost);
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
    
    @GetMapping(value="/saveapplicant")
    public ModelAndView saveApplicant(HttpServletRequest request,HttpSession session
    		,RedirectAttributes ra,ModelMap model) throws IOException {
    	
    	Long id=Long.parseLong(request.getParameter("jobPostId"));
    	
    	JobPost jobPost=jobPostService.getByid(id);
    	
    	Applicant applicant = Applicant.builder()
    			.applicantName(request.getParameter("name"))
    			.applicantEmail(request.getParameter("email"))
    			.applicantMobile(request.getParameter("mobile"))
    			.address(request.getParameter("address"))
    			.link(request.getParameter("link"))
    			.comment(request.getParameter("comment"))
    			.applyTime(Timestamp.valueOf(LocalDateTime.now()))
    			.applicantStatus("None")
    			.currentState("PENDING")
    			.jobPosition(jobPost.getJobPosition())
    			.jobPost(jobPost)
    			.file(request.getParameter("file").getBytes())
    			.build();
   
    	Applicant result = service.createApplicant(applicant);
    	
    	if(result != null) {
    		ra.addFlashAttribute("message","Successfully Added.");
    	}
    	
    	return new ModelAndView("redirect:/applicantProcess");
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
    			.jobPostBean(String.valueOf(applicant.getJobPost().getPostId()))
    			.jobPositionBean(String.valueOf(applicant.getJobPosition().getPositionId()))
    			.build();
    	
    	
    	
    	return new ModelAndView("editApplicantControl","applicant",bean);
    }

    @GetMapping(value="/deleteapplicant")
    public ModelAndView deleteApplicant(@RequestParam("id")Long id,RedirectAttributes ra) {
    	ra.addFlashAttribute("message","Successfully!");
    	
    	Applicant applicant = service.getApplicantById(id);
    	
    	applicant.setCurrentState("Rejected");
    	
    	service.updateApplicant(applicant);
    	ra.addFlashAttribute("message","Successfully!");
    	return new ModelAndView("redirect:/applicant");
    }
    
    @GetMapping(value = "/viewFile/{id}",
    			produces = {MediaType.IMAGE_PNG_VALUE,MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {

    	Applicant applicant=service.getApplicantById(id);
         byte[] imageContent = applicant.getFile();
        
        //final HttpHeaders headers = new HttpHeaders();

        //headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        return new ResponseEntity<>(imageContent, HttpStatus.FOUND);
    }
}

