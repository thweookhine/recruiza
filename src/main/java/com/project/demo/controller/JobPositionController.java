package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.History;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.User;
import com.project.demo.model.JobPositionBean;
import com.project.demo.model.UserBean;
import com.project.demo.repository.JobPositionRepository;
import com.project.demo.service.HistoryService;
import com.project.demo.service.JobPositionService;
import com.project.demo.service.UserService;

@RestController
public class JobPositionController {

	@Autowired
	JobPositionService service;
	
	@Autowired
	JobPositionRepository repo;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	UserService userService;

	@ModelAttribute("jobPosition")
	public JobPosition getJobPosition() {
		return new JobPosition();
	}

	@ModelAttribute("standardDate")
	public Date showDate() {
		return new Date();
	}

//	@RequestMapping(value="/jobposition", method=RequestMethod.GET)
//	public ModelAndView displayView(ModelMap model) {
//	
//		List<JobPosition> list = new ArrayList<JobPosition>();
//		//model.addAttribute("currentPage",1);
//		//model.addAttribute("totalPages",service.findTotalPages());
//		list=service.getAllJobPosition();
//		model.addAttribute("list",list);
//	   return new ModelAndView("jobPositonControl","jobposition",new JobPositionBean());
//
//	}

	@PostMapping(value = "/savejobposition")
	public ModelAndView saveJobPositio(@ModelAttribute("jobposition") @Validated JobPositionBean jobposition,
			BindingResult bs, RedirectAttributes ra, ModelMap model, HttpSession session) {

		if (bs.hasErrors()) {
			ra.addFlashAttribute("message", "Something Wrong!");

		}

		JobPosition position = JobPosition.builder().positionName(jobposition.getPositionName()).build();

		List<JobPosition> list = repo.findByName(jobposition.getPositionName());
		if (list.size() == 0) {
			service.createJobPosition(position);
			ra.addFlashAttribute("message", "Insert Successfully!");

		} else {
			ra.addFlashAttribute("message", "Position Already Exist!");

		}
		return new ModelAndView("redirect:/jobposition");
	}

	@RequestMapping(value = "/editjobposition", method = RequestMethod.GET)
	public ModelAndView displayUpdateJobPosition(@RequestParam("id") Long id, ModelMap model) {

		JobPosition position = service.getPositionById(id);
		JobPositionBean bean = JobPositionBean.builder().positionId(position.getPositionId())
				.positionName(position.getPositionName()).build();
		return new ModelAndView("editJobPosition", "jobposition", bean);
	}

	@PostMapping(value = "/updatejobposition")
	public ModelAndView updateJobPosition(@ModelAttribute("jobposition") @Validated JobPositionBean jobposition,
			BindingResult bs, ModelMap model, RedirectAttributes ra, @RequestParam("oldName") String oldName) {
		if (bs.hasErrors()) {
			ra.addFlashAttribute("message", "Update Fail!");
			return new ModelAndView("redirect:/editjobposition");
		}

		JobPosition position = JobPosition.builder().positionId(jobposition.getPositionId())
				.positionName(jobposition.getPositionName()).build();

		List<JobPosition> list = repo.findByName(jobposition.getPositionName());
		if (list.size() == 0 || oldName.equals(position.getPositionName())) {

			// System.out.print(position.getPositionId());
			service.updateJobPosition(position);
			if (oldName.equals(position.getPositionName())) {
				ra.addFlashAttribute("message", "No Data Changed!");
			} else
				ra.addFlashAttribute("message", "Update Successful!");
		} else {
			model.addAttribute("message", "Can't update!Existing Name!!");
			return new ModelAndView("editJobPosition");
		}
		return new ModelAndView("redirect:/jobposition");
	}

	@GetMapping(value = "/deletejobposition")
	public ModelAndView deleteJobPosition(RedirectAttributes model, @RequestParam("id") Long id) {

		model.addFlashAttribute("message", "delete successfully!");
		service.deleteJobPosition(id);
		return new ModelAndView("redirect:/jobposition");
	}

	@PostMapping(value = "/searchjobposition")
	public ModelAndView searchJobPosition(ModelMap model, @RequestParam("name") String name) {
		List<JobPosition> list = service.getPositionByCodeAndName(name, name);
		// model.addAttribute("currentPage",1);
		// model.addAttribute("totalPages",service.findTotalPages());
		model.addAttribute("list", list);
		System.out.print(list.toString());
		return new ModelAndView("jobPositonControl", "jobposition", new JobPositionBean());
	}

	@GetMapping(value = "/jobposition")
	public ModelAndView addJobPosition(ModelMap model, RedirectAttributes ra, HttpSession session,
			JobPositionBean beanPosition) {
		String keyword = null;
		return searchJobPosition(model, ra, session, beanPosition, 1, "positionId", "asc", keyword);
	}

	@GetMapping(value = "/searchPosition/{pageNumber}")
	public ModelAndView searchJobPosition(ModelMap model, RedirectAttributes ra, HttpSession session,
			JobPositionBean beanPosition, @PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		int index = Integer.parseInt((currentPage - 1) + "1");
		Page<JobPosition> page = service.listAllPositions(currentPage, sortField, sortDir, keyword);

		long totalPosition = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<JobPosition> list = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPositions", totalPosition);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("list", list);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("index", index);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		return new ModelAndView("jobPositonControl", "jobposition", beanPosition);
	}

	public void generateHistoryforPosition(JobPosition position, HttpSession session, String action) {
		
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userService.getById(userBean.getUserId());
		
		String data = position.getPositionId()+","+position.getPositionCode()+","+position.getPositionName();
		History history = History.builder()
				.user(user)
				.action(action)
				.dataName(position.getPositionName())
				.tableName("Position")
				.data(data)
				.historyCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
				.build();
		historyService.createHistory(history);
	}

}
