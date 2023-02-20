package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.project.demo.entity.RecruitementResource;

import com.project.demo.model.RecruitementResourceBean;
import com.project.demo.repository.RecruitmentResourceRepository;
import com.project.demo.service.RecruitementResourceService;

@RestController
public class RecruitementResourceController {
	
	@Autowired
	RecruitementResourceService service;
	@Autowired
	RecruitmentResourceRepository repo;
	
	//Previous Page Url 
	@RequestMapping(value="/previousresourcepage", method=RequestMethod.GET)
	public ModelAndView displayPreviousResource(@RequestParam("currentPage") String currentPage,ModelMap model) {
		
		model.addAttribute("currentPage",Integer.parseInt(currentPage)-1);
		model.addAttribute("totalPages",service.findTotalPages());
		
		List<RecruitementResource> list = new ArrayList<RecruitementResource>();
		list=service.getAllRecruitementResource(Integer.parseInt(currentPage)-2);
		model.addAttribute("list",list);
	   return new ModelAndView("recruitementResourceControl","resource",new RecruitementResourceBean());

	}
	//Next Page Url
	@RequestMapping(value="/nextresourcepage", method=RequestMethod.GET)
	public ModelAndView displayNextResource(@RequestParam("currentPage") String currentPage,ModelMap model) {
		model.addAttribute("currentPage",Integer.parseInt(currentPage)+1);
		model.addAttribute("totalPages",service.findTotalPages());
		
		List<RecruitementResource> list = new ArrayList<RecruitementResource>();
		list=service.getAllRecruitementResource(Integer.parseInt(currentPage));
		model.addAttribute("list",list);
	   return new ModelAndView("recruitementResourceControl","resource",new RecruitementResourceBean());

	}
	
	@RequestMapping(value="/recruitementresource",method=RequestMethod.GET)
	public ModelAndView display(ModelMap model) {
		
		List<RecruitementResource> list = new ArrayList<RecruitementResource>();
		model.addAttribute("currentPage",1);
		model.addAttribute("totalPages",service.findTotalPages());
		list=service.getAllRecruitementResource(0);
		model.addAttribute("list",list);
		return new ModelAndView("recruitementResourceControl","resource",new RecruitementResourceBean());
	}

	@PostMapping(value="/saveresource")
	public ModelAndView saveResource(@ModelAttribute("resource")@Validated RecruitementResourceBean resource,
	ModelMap model,BindingResult bs) {
		
		if(bs.hasErrors()) {
			model.addAttribute("message","Insert Fail!");
			return new ModelAndView("redirect:/recruitementresource");
		}
		
		RecruitementResource resource1 = RecruitementResource.builder()
				.resourceName(resource.getResourceName())
				.link(resource.getLink())
				.address(resource.getAddress())
				.resourceMobile(resource.getResourceMobile())
				.contactPerson(resource.getContactPerson())
				.resourceCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.recruitementType(resource.getRecruitementType())
				.build();
		
		service.createRecruitementResource(resource1);
		model.addAttribute("messae","Save Successfully!");
		return new ModelAndView("redirect:/recruitementresource");
		
	}
	
	@GetMapping(value="/editresource")
	public ModelAndView displayEditResource(@RequestParam("id")Long id,ModelMap model) {
		
		RecruitementResource resource = service.getResourceById(id);
		RecruitementResource bean = RecruitementResource.builder()
				.resourceId(resource.getResourceId())
				.resourceName(resource.getResourceName())
				.link(resource.getLink())
				.address(resource.getAddress())
				.resourceMobile(resource.getResourceMobile())
				.contactPerson(resource.getContactPerson())
				.recruitementType(resource.getRecruitementType())
				.build();
		
		return new ModelAndView("editResourceControl","resource",bean);
	}
	
	@PostMapping(value="/updateresource")
	public ModelAndView updateResource(@ModelAttribute("resource")@Validated RecruitementResourceBean resource,ModelMap model,
			BindingResult bs) {
		if(bs.hasErrors()) {
			model.addAttribute("message","Update Fail!");
			return new ModelAndView("editResourceControl");
		}
		
		RecruitementResource bean = RecruitementResource.builder()
				.resourceId(resource.getResourceId())
				.resourceName(resource.getResourceName())
				.link(resource.getLink())
				.address(resource.getAddress())
				.resourceMobile(resource.getResourceMobile())
				.contactPerson(resource.getContactPerson())
				.resourceCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.recruitementType(resource.getRecruitementType())
				.build();

		System.out.print("jsdakfjhk"+ bean);
			service.updateRecruitementResource(bean);
			model.addAttribute("message","Updated Successfully!");
			return new ModelAndView("redirect:/recruitementresource");
	}
	
	@GetMapping(value="/deleteresource")
	public ModelAndView deleteResource(RedirectAttributes model,@RequestParam("id")Long id) {
	
		model.addFlashAttribute("message","delete successfully!");
		service.deleteRecruitementResource(id);
		return new ModelAndView("redirect:/recruitementresource");
	}
	@PostMapping(value="/searchresource")
	public ModelAndView searchResource(Model ra,@RequestParam("name") String name) {
		List<RecruitementResource> list=service.getByCodeNameMobileAndType(name, name,name,name);
		ra.addAttribute("currentPage",1);
		ra.addAttribute("totalPages",service.findTotalPages());
		ra.addAttribute("list",list);
		System.out.print(list.toString());
		   return new ModelAndView("recruitementResourceControl","resource",new RecruitementResourceBean());
	}
}
