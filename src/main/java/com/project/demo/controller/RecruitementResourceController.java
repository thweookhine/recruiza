package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.History;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.model.JobPositionBean;
import com.project.demo.model.RecruitementResourceBean;
import com.project.demo.model.TeamBean;
import com.project.demo.model.UserBean;
import com.project.demo.repository.RecruitmentResourceRepository;
import com.project.demo.service.HistoryService;
import com.project.demo.service.RecruitementResourceService;
import com.project.demo.service.UserService;
import com.project.demo.utils.UIOptionData;

@RestController
public class RecruitementResourceController {

	@Autowired
	RecruitementResourceService service;
	@Autowired
	RecruitmentResourceRepository repo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	HistoryService historyService;

//	@RequestMapping(value="/recruitementresource",method=RequestMethod.GET)
//	public ModelAndView display(ModelMap model) {
//		
//		List<RecruitementResource> list = new ArrayList<RecruitementResource>();
//		model.addAttribute("currentPage",1);
//		model.addAttribute("totalPages",service.findTotalPages());
//		list=service.getAllRecruitementResource(0);
//		model.addAttribute("rList",UIOptionData.generateResourceType());
//		model.addAttribute("list",list);
//		return new ModelAndView("recruitementResourceControl","resource",new RecruitementResourceBean());
//	}

	@PostMapping(value = "/saveresource")
	public ModelAndView saveResource(@ModelAttribute("resource") @Validated RecruitementResourceBean resource,
			HttpSession session, RedirectAttributes ra, BindingResult bs, ModelMap model) {

		if (bs.hasErrors()) {
			ra.addFlashAttribute("message", "Insert Fail!");
			return new ModelAndView("redirect:/recruitementresource");
		}

		RecruitementResource resource1 = RecruitementResource.builder().resourceName(resource.getResourceName())
				.link(resource.getLink()).address(resource.getAddress()).resourceMobile(resource.getResourceMobile())
				.contactPerson(resource.getContactPerson()).resourceCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.recruitementType(resource.getRecruitementType()).build();

		service.createRecruitementResource(resource1);
		ra.addFlashAttribute("message", "Save Successfully!");
		return new ModelAndView("redirect:/recruitementresource");

	}

	@GetMapping(value = "/editresource")
	public ModelAndView displayEditResource(@RequestParam("id") Long id, RedirectAttributes model, ModelMap m) {

		RecruitementResource resource = service.getResourceById(id);
		RecruitementResource bean = RecruitementResource.builder().resourceId(resource.getResourceId())
				.resourceName(resource.getResourceName()).link(resource.getLink()).address(resource.getAddress())
				.resourceMobile(resource.getResourceMobile()).contactPerson(resource.getContactPerson())
				.recruitementType(resource.getRecruitementType()).build();

		m.addAttribute("rList", UIOptionData.generateResourceType());
		return new ModelAndView("editResourceControl", "resource", bean);
	}

	@PostMapping(value = "/updateresource")
	public ModelAndView updateResource(@ModelAttribute("resource") @Validated RecruitementResourceBean resource,
			RedirectAttributes model, BindingResult bs) {
		if (bs.hasErrors()) {
			model.addFlashAttribute("message", "Update Fail!");
			return new ModelAndView("editResourceControl");
		}
		RecruitementResource bean = RecruitementResource.builder().resourceId(resource.getResourceId())
				.resourceName(resource.getResourceName()).link(resource.getLink()).address(resource.getAddress())
				.resourceMobile(resource.getResourceMobile()).contactPerson(resource.getContactPerson())
				.resourceCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.recruitementType(resource.getRecruitementType()).build();

		service.updateRecruitementResource(bean);
		model.addFlashAttribute("message", "Updated Successfully!");
		return new ModelAndView("redirect:/recruitementresource");
	}

	@GetMapping(value = "/deleteresource")
	public ModelAndView deleteResource(ModelMap model, RedirectAttributes ra, @RequestParam("id") Long id, ModelMap m) {

		// model.addFlashAttribute("message","delete successfully!");

		try {
			service.deleteRecruitementResource(id);
		} catch (Exception expection) {
			model.addAttribute("message", "You can't delete");
			RecruitementResource resource = service.getResourceById(id);
			RecruitementResource bean = RecruitementResource.builder().resourceId(resource.getResourceId())
					.resourceName(resource.getResourceName()).link(resource.getLink()).address(resource.getAddress())
					.resourceMobile(resource.getResourceMobile()).contactPerson(resource.getContactPerson())
					.recruitementType(resource.getRecruitementType()).build();

			m.addAttribute("rList", UIOptionData.generateResourceType());
			return new ModelAndView("editResourceControl", "resource", bean);

		}
		ra.addFlashAttribute("message", "delete successfully!");
		return new ModelAndView("redirect:/recruitementresource");
	}

	@PostMapping(value = "/searchresource")
	public ModelAndView searchResource(Model ra, @RequestParam("name") String name) {
		List<RecruitementResource> list = service.getByCodeNameMobileAndType(name, name, name, name);

		ra.addAttribute("list", list);
		ra.addAttribute("rList", UIOptionData.generateResourceType());
		System.out.print(list.toString());
		return new ModelAndView("recruitementResourceControl", "resource", new RecruitementResourceBean());
	}

	@GetMapping(value = "/recruitementresource")
	public ModelAndView addResource(ModelMap model, RedirectAttributes ra, HttpSession session,
			RecruitementResourceBean beanResource) {
		String keyword = null;
		model.addAttribute("rList", UIOptionData.generateResourceType());
		return searchResource(model, ra, session, beanResource, 1, "resourceId", "asc", keyword);
	}

	@GetMapping(value = "/searchResource/{pageNumber}")
	public ModelAndView searchResource(ModelMap model, RedirectAttributes ra, HttpSession session,
			RecruitementResourceBean beanResource, @PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		int index = Integer.parseInt((currentPage - 1) + "1");
		Page<RecruitementResource> page = service.listAllResources(currentPage, sortField, sortDir, keyword);

		long totalResource = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<RecruitementResource> list = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalResources", totalResource);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("list", list);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("rList", UIOptionData.generateResourceType());
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

//        	if(beanResource.getResourceMobile()==null) {
//        		beanResource=RecruitementResourceBean.builder()
//        				.resourceMobile("09")
//        				.build();
//        	}
//        	
////        	RecruitementResourceBean beanResource = RecruitementResourceBean.builder()
////					        			.build();
		return new ModelAndView("recruitementResourceControl", "resource", beanResource);
	}

	public void generateHistoryforResource(RecruitementResource resource, HttpSession session, String action) {

		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());

		String data = resource.getResourceId()+","+resource.getResourceCode()+","+resource.getResourceName()+","
				+resource.getLink()+","+resource.getAddress()+","+resource.getResourceMobile() +","+resource.getContactPerson()
				+","+resource.getRecruitementType();
				;
		History history = History.builder()
				.user(user)
				.action(action)
				.dataName(resource.getResourceName())
				.tableName("Recruitment Resource")
				.data(data)
				.historyCreatedTime(Timestamp.valueOf(LocalDateTime.now())).build();
		historyService.createHistory(history);
	}

}
