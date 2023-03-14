package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.JobPost;
import com.project.demo.entity.User;
import com.project.demo.model.UserBean;
import com.project.demo.service.JobPostService;
import com.project.demo.service.UserService;
import com.project.demo.utils.NameCapital;
import com.project.demo.utils.PasswordGenerator;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JobPostService jobPostService;
	
	public UserBean checkSessionUser(HttpSession session) {
		UserBean bean = null;
				 bean = (UserBean) session.getAttribute("user");
		return bean;
	}
	
	@GetMapping(value = "/admin")
	public String helloAdmin() {
		
		return "Hello Admin";
		
	}
	
	@GetMapping(value = "/login")
	public ModelAndView login(ModelMap model,HttpSession session) {
		
		if(session != null) {
			model.addAttribute("error",session.getAttribute("error"));
			model.addAttribute("email",session.getAttribute("email"));
			model.addAttribute("password",session.getAttribute("password"));
			session.invalidate();
		}
		
		return new ModelAndView("index");
	}
	
	@GetMapping(value = "/basic")
	public String basicUser() {
		
		return "Hello Basic User";
	}
	
	@GetMapping(value = "/home")
	public ModelAndView homePage(HttpSession session,ModelMap model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User bean = userService.userSelectOne(email);
		
		closeJobPosts();
		
		List<JobPost> jobPosts = jobPostService.searchBeforeDueDate(LocalDate.now().toString());
		
		UserBean user = UserBean.builder()
						.userId(bean.getUserId())
						.userCode(bean.getUserCode())
						.userName(bean.getUserName())
						.userEmail(bean.getUserEmail())
						.userMobile(bean.getUserMobile())
						.password(bean.getPassword())
						.lastAction(bean.getLastAction())
						.role(bean.getRole())
						.userStatus(bean.getUserStatus())
						.userCreatedTime(bean.getUserCreatedTime())
						.enabled(bean.isEnabled())
						.build();
		
		session.setAttribute("user", user);
		model.addAttribute("jobPosts",jobPosts);
		
		return new ModelAndView("home");
	}
	
    @GetMapping(value = "/logout") 
    public ModelAndView logout(ModelMap model,HttpSession session){
    	if(session != null) {
    		session.invalidate();
    	}
    	model.addAttribute("msg", "Logout Successful !");
        return new ModelAndView("index");
    }
	
    @GetMapping(value = "/unauthorized") 
    public String deniedPage(){
    	
        return "No Approved";
    }
    
    @GetMapping(value = "/market")
    public String marketingPage() {
    	
    	return "Hello,Welcome Maketer";
    }
    
    @GetMapping(value = "/admin/addUser")
    public ModelAndView addUser(UserBean bean,ModelMap model,RedirectAttributes ra, HttpSession session) {
    	String keyword = null;
    	return searchUser(model, ra, session, bean, 1, "userId", "asc", keyword);
    }
    
    @GetMapping(value = "/admin/searchUser/{pageNumber}")
    public ModelAndView searchUser(ModelMap model,RedirectAttributes ra,HttpSession session,UserBean userBean,
    							@PathVariable("pageNumber") int currentPage,
    							@Param("sortField") String sortField,
    							@Param("sortDir") String sortDir,
    							@Param("keyword") String keyword) {
    	
        if(checkSessionUser(session) == null) {
        	ra.addFlashAttribute("error","Please login first !");
        	return new ModelAndView("redirect:/login");
        }else {
        	
        	Page<User> page = userService.listAllUsers(currentPage, sortField, sortDir, keyword);
        	
        	long totalUsers = page.getTotalElements();
        	int totalPages = page.getTotalPages();
        	
        	List<User> users = page.getContent();
        	
        	model.addAttribute("currentPage", currentPage);
        	model.addAttribute("totalUsers", totalUsers);
        	model.addAttribute("totalPages", totalPages);
        	model.addAttribute("users", users);
        	model.addAttribute("sortField", sortField);
        	model.addAttribute("sortDir", sortDir);
        	model.addAttribute("keyword", keyword);
        	
        	String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        	model.addAttribute("reverseSortDir", reverseSortDir);
        	
        	if(userBean.getUserMobile() == null) {
        		userBean = UserBean.builder()
    					.userMobile("09")
    					.build();
        	}
        	
        	return new ModelAndView("addUser","bean",userBean);
        }
        
    }
    
    @PostMapping(value = "/admin/addUserServlet")
    public ModelAndView addUserServlet(@ModelAttribute("bean") @Valid UserBean bean,BindingResult bs,ModelMap model,RedirectAttributes ra,HttpSession session) {
    	
    	if(checkSessionUser(session) == null) {
    		ra.addFlashAttribute("error","Please login first !");
        	return new ModelAndView("redirect:/login");
    	}else if (bs.hasErrors()) {
    		model.addAttribute("error", "Field cannot be blank !");
		}else if(!bean.getPassword().equals(bean.getConfPassword())) {
			model.addAttribute("error", "check your confirm password again !");
		}else if(userService.findUserName(bean.getUserName()) != null) {
			model.addAttribute("error", "User name is has been !");
		}else if(userService.findUserEmail(bean.getUserEmail()) != null) {
			model.addAttribute("error", "User email is has been !");
		}else {
			
			User user = User.builder()
							.userName(NameCapital.capitalizeFirstLetter(bean.getUserName()))
							.userEmail(bean.getUserEmail())
							.userMobile(bean.getUserMobile())
							.password(PasswordGenerator.generatePassword(bean.getPassword()))
							.role(bean.getRole())
							.userStatus("ACTIVE")
							.userCreatedTime(Timestamp.valueOf(LocalDateTime.now()))
							.enabled(true)
							.build();
			
			User cUser = userService.insertUser(user);
			
			if(cUser == null) {
				model.addAttribute("error", "Add User Fail !");
			}else {
				model.addAttribute("msg", "Add User Successful !");
			}
		}
    	return addUser(bean, model, ra, session);
    }
    
    @PostMapping(value = "/admin/updateUser")
    public ModelAndView updateUser(@RequestParam("id") Long userId, HttpSession session, RedirectAttributes ra, HttpServletRequest request, ModelMap model,UserBean bean) {
    	
    	if(checkSessionUser(session) == null) {
    		ra.addFlashAttribute("error","Please login first !");
        	return new ModelAndView("redirect:/login");
    	}else {
    		String role = request.getParameter("role");
    		System.out.println("Role " + role);
			User user = userService.editUserRole(userId, role);
			if(user != null) {
				model.addAttribute("msg", "Update Role Successfully !");
			}else {
				model.addAttribute("error", "Update Role Failed !");
			}
			return addUser(bean, model, ra, session);
		}
    }
    
    @GetMapping("/admin/deleteUser")
    public ModelAndView deleteUser(@RequestParam("id") Long userId,RedirectAttributes ra, ModelMap model,UserBean bean, HttpSession session) {
    	
            User user = userService.deleteUser(userId);
            
            if (user != null) {
	            model.addAttribute("msg", "Ban User Successful !");
            }else {
	        	model.addAttribute("msg", "Ban User Failed !");
            }
            return addUser(bean, model, ra, session);
    }
    
    @GetMapping("/admin/activeUser")
    public ModelAndView activeUser(@RequestParam("id") Long userId,RedirectAttributes ra, ModelMap model, HttpSession session, UserBean bean) {
    	
    	User user = userService.activeUser(userId);
        
    	if (user != null) {
            model.addAttribute("msg", "Active User Successful !");
        }else {
        	model.addAttribute("msg", "Active User Failed !");
        }
        return addUser(bean, model, ra, session);
    }
    
	public void closeJobPosts() {
		List<JobPost> list = jobPostService.searchAfterDueDate(LocalDate.now().toString());
		for(JobPost jp : list) {
			jp.setPostStatus("CLOSED");
			jobPostService.updateJobPost(jp);
		}
	}
}
