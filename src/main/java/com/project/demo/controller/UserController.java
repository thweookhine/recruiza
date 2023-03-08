package com.project.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

import com.project.demo.entity.User;
import com.project.demo.model.UserBean;
import com.project.demo.service.UserServiceImpl;
import com.project.demo.utils.NameCapital;
import com.project.demo.utils.PasswordGenerator;

@RestController
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
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
	public ModelAndView homePage(HttpSession session) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User bean = userServiceImpl.userSelectOne(email);
		
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
        	
        	Page<User> page = userServiceImpl.listAllUsers(currentPage, sortField, sortDir, keyword);
        	
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
		}else if(userServiceImpl.findUserName(bean.getUserName()) != null) {
			model.addAttribute("error", "User name is has been !");
		}else if(userServiceImpl.findUserEmail(bean.getUserEmail()) != null) {
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
			
			User cUser = userServiceImpl.insertUser(user);
			
			if(cUser == null) {
				model.addAttribute("error", "Add User Fail !");
			}else {
				model.addAttribute("msg", "Add User Successful !");
			}
		}
    	return addUser(bean, model, ra, session);
    }
    
    @GetMapping("/admin/editUser")
    public ModelAndView editUser(@RequestParam("id") Long userId,HttpSession session,RedirectAttributes ra) {
    	
    	 if(checkSessionUser(session) == null) {
         	ra.addFlashAttribute("error","Please login first !");
         	return new ModelAndView("redirect:/login");
         }else {
        	 
    	User user = userServiceImpl.selectUserOne(userId);
    	
    	UserBean bean = UserBean.builder()
				.userId(user.getUserId())
				.userCode(user.getUserCode())
				.userName(user.getUserName())
				.userEmail(user.getUserEmail())
				.userMobile(user.getUserMobile())
				.password(user.getPassword())
				.lastAction(user.getLastAction())
				.role(user.getRole())
				.userStatus(user.getUserStatus())
				.userCreatedTime(user.getUserCreatedTime())
				.enabled(user.isEnabled())
				.build();
    	
    	return new ModelAndView("editUser","bean",bean);
    }
}
    
//    @PostMapping(value = "/admin/updateUser")
//    public ModelAndView updateUser(@ModelAttribute("bean") @Valid UserBean bean,BindingResult bs,ModelMap model,RedirectAttributes ra,HttpSession session) {
//    	
//    	if(checkSessionUser(session) == null) {
//    		ra.addFlashAttribute("error","Please login first !");
//        	return new ModelAndView("redirect:/login");
//    	}else if (bs.hasErrors()) {
//    		model.addAttribute("error", "Field cannot be blank !");
//			return new ModelAndView("editUser");
//		}else if(!bean.getPassword().equals(bean.getConfPassword())) {
//			model.addAttribute("error", "check your confirm password again !");
//			return new ModelAndView("editUser");
//		}else {
//			
//			User cUser = userServiceImpl.updateUser(bean);
//			
//			if(cUser == null) {
//				model.addAttribute("error", "Update User Fail !");
//				return new ModelAndView("editUser");
//			}
//		}
//    	ra.addFlashAttribute("msg", "Update User Successful !");
//    	return new ModelAndView("redirect:/admin/addUser");
//    }
    
    @GetMapping("/admin/deleteUser")
    public ModelAndView deleteUser(@RequestParam("id") Long userId,RedirectAttributes ra) {
    	
            User user = userServiceImpl.deleteUser(userId);
            
            if (user != null) {
	            ra.addFlashAttribute("msg", "Delete User Successful !");
	        	return new ModelAndView("redirect:/admin/addUser");
            }else {
	        	ra.addAttribute("id",userId);
	        	ra.addFlashAttribute("msg", "Delete User Failed !");
	        	return new ModelAndView("redirect:/admin/editUser");
            }
    }
    
    @GetMapping("/admin/activeUser")
    public ModelAndView activeUser(@RequestParam("id") Long userId,RedirectAttributes ra) {
    	
    	User user = userServiceImpl.activeUser(userId);
        
        if (user != null) {
            ra.addFlashAttribute("msg", "Active User Successful !");
        	return new ModelAndView("redirect:/admin/addUser");
        }else {
        	ra.addAttribute("id",userId);
        	ra.addFlashAttribute("msg", "Active User Failed !");
        	return new ModelAndView("redirect:/admin/editUser");
        }
    }
}
