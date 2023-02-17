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
		list=service.getAllJobPosition();
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
		
		service.createJobPosition(position);
		model.addFlashAttribute("message","Successfully!");
		return new ModelAndView("redirect:/jobposition");
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
			BindingResult bs,RedirectAttributes model) {
		if(bs.hasErrors()) {
			model.addFlashAttribute("message","Update Fail!");
			return new ModelAndView("redirect:/editjobposition");
		}
		
		JobPosition position = JobPosition.builder()
				.positionId(jobposition.getPositionId())
				.positionName(jobposition.getPositionName())
				.build();

		System.out.print(position.getPositionId());
		service.updateJobPosition(position);
		model.addAttribute("message","Update Successfully!");
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
		ArrayList<JobPosition> list=(ArrayList<JobPosition>) service.getPositionByCodeAndName(name, name);
		ra.addAttribute("list",list);
		System.out.print(list.toString());
		   return new ModelAndView("jobPositonControl","jobposition",new JobPositionBean());
	}
}
