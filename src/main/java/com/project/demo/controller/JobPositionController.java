package com.project.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.JobPosition;
import com.project.demo.model.JobPositionBean;
import com.project.demo.repository.JobPositionRepository;
import com.project.demo.service.JobPositionService;

@RestController
public class JobPositionController {
	
	@Autowired
	JobPositionService service;
	@Autowired
	JobPositionRepository repo;
	
	//Previous Page Url 
	@RequestMapping(value="/previouspositionpage", method=RequestMethod.GET)
	public ModelAndView displayPrevious(@RequestParam("currentPage") String currentPage,ModelMap model) {
		
		model.addAttribute("currentPage",Integer.parseInt(currentPage)-1);
		model.addAttribute("totalPages",service.findTotalPages());
		List<JobPosition> list = new ArrayList<JobPosition>();
		list=service.getAllJobPosition(Integer.parseInt(currentPage)-2);
		model.addAttribute("list",list);
	   return new ModelAndView("jobPositonControl","jobposition",new JobPositionBean());

	}
	//Next Page Url
	@RequestMapping(value="/nextpositionpage", method=RequestMethod.GET)
	public ModelAndView displayNext(@RequestParam("currentPage") String currentPage,ModelMap model) {
		model.addAttribute("currentPage",Integer.parseInt(currentPage)+1);
		model.addAttribute("totalPages",service.findTotalPages());
		
		List<JobPosition> list = new ArrayList<JobPosition>();
		list=service.getAllJobPosition(Integer.parseInt(currentPage));
		model.addAttribute("list",list);
	   return new ModelAndView("jobPositonControl","jobposition",new JobPositionBean());

	}

	@ModelAttribute("jobPosition")
	public JobPosition getJobPosition() {
	return new JobPosition();
	}
	
	@ModelAttribute("standardDate")
	public Date showDate() {
		return new Date();
	}
	
	@RequestMapping(value="/jobposition", method=RequestMethod.GET)
	public ModelAndView displayView(ModelMap model) {
	
		List<JobPosition> list = new ArrayList<JobPosition>();
		model.addAttribute("currentPage",1);
		model.addAttribute("totalPages",service.findTotalPages());
		list=service.getAllJobPosition(0);
		model.addAttribute("list",list);
	   return new ModelAndView("jobPositonControl","jobposition",new JobPositionBean());

	}

	@PostMapping(value="/savejobposition")
	public ModelAndView saveJobPositio(@ModelAttribute("jobposition")@Validated JobPositionBean jobposition,
			BindingResult bs,RedirectAttributes model) {
		
		if(bs.hasErrors()) {
			model.addFlashAttribute("message","Something Wrong!");
			return new ModelAndView("redirect:/jobposition");
			
		}
		
		JobPosition position = JobPosition.builder()
								.positionName(jobposition.getPositionName())
								.build();
		
		List<JobPosition> list = repo.findByName(jobposition.getPositionName());
		if(list.size()==0) {
			service.createJobPosition(position);
			model.addFlashAttribute("message","Insert Successfully!");
			return new ModelAndView("redirect:/jobposition");
			
		}else {
			model.addFlashAttribute("message","Position Already Exist!");
			return new ModelAndView("redirect:/jobposition");
		}
		
	}
	@RequestMapping(value="/editjobposition",method=RequestMethod.GET)
	public ModelAndView displayUpdateJobPosition(@RequestParam("id") Long id,ModelMap model) {
	
		JobPosition position = service.getPositionById(id);
		JobPositionBean bean = JobPositionBean.builder()
				.positionId(position.getPositionId())
				.positionName(position.getPositionName())
				.build();
		return new ModelAndView("editJobPosition","jobposition",bean);
	}
	@PostMapping(value="/updatejobposition")
	public ModelAndView updateJobPosition(@ModelAttribute("jobposition")@Validated JobPositionBean jobposition,
			BindingResult bs,ModelMap model,RedirectAttributes ra,@RequestParam("oldName")String oldName) {
		if(bs.hasErrors()) {
			ra.addFlashAttribute("message","Update Fail!");
			return new ModelAndView("redirect:/editjobposition");
		}
		
		JobPosition position = JobPosition.builder()
				.positionId(jobposition.getPositionId())
				.positionName(jobposition.getPositionName())
				.build();
		
		List<JobPosition> list=repo.findByName(jobposition.getPositionName());
		if(list.size()==0 || oldName.equals(position.getPositionName())) {

		//System.out.print(position.getPositionId());
		service.updateJobPosition(position);
		ra.addFlashAttribute("message","No Data Changed!");
		
		}
		else {
			model.addAttribute("message","Can't update!Existing Name!!");
			return new ModelAndView("editJobPosition");
		}
			return new ModelAndView("redirect:/jobposition");
	}
	@GetMapping(value="/deletejobposition")
	public ModelAndView deleteJobPosition(RedirectAttributes model,@RequestParam("id")Long id) {
	
		model.addFlashAttribute("message","delete successfully!");
		service.deleteJobPosition(id);
		return new ModelAndView("redirect:/jobposition");
	}
	@PostMapping(value="/searchjobposition")
	public ModelAndView searchJobPosition(Model ra,@RequestParam("name") String name) {
		List<JobPosition> list=service.getPositionByCodeAndName(name, name);
		ra.addAttribute("currentPage",1);
		ra.addAttribute("totalPages",service.findTotalPages());
		ra.addAttribute("list",list);
		System.out.print(list.toString());
		   return new ModelAndView("jobPositonControl","jobposition",new JobPositionBean());
	}
}
