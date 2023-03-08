package com.project.demo.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.zxing.WriterException;
import com.project.demo.entity.History;
import com.project.demo.entity.User;
import com.project.demo.model.UserBean;
import com.project.demo.service.HistoryService;
import com.project.demo.service.UserService;
import com.project.demo.utils.PasswordGenerator;
import com.project.demo.utils.qrCodeGenerator;

@RestController
public class ProfileController {
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	UserService userService;
	
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	public UserBean checkSessionUser(HttpSession session) {
		UserBean bean = null;
				 bean = (UserBean) session.getAttribute("user");
		return bean;
	}
	
    @GetMapping("/profile")
	public ModelAndView goToProfile(HttpSession session,Model model) {
    	UserBean userBean = (UserBean) session.getAttribute("user");
    	String qrData = "Code = "+userBean.getUserCode()+"\nName = "+userBean.getUserName()
    	+"\nEmail = "+userBean.getUserEmail()+"\nPhone Number = "+userBean.getUserMobile()
    	+"\nRole = "+userBean.getRole()
    	;
    	byte[] image = new byte[0];
    	
    	try {
    		// Generate and Return Qr Code in Byte Array
    		image = qrCodeGenerator.getQRCodeImage(qrData, 250, 250);
    	}catch (WriterException | IOException e) {
			e.printStackTrace();
		}
    	
    	List<History> histories = historyService.listLast20History(userBean.getUserId());
    	
    	model.addAttribute("histories",histories);
    	
    	// Convert Byte Array into Base64 Encode String
    	String qrcode = Base64.getEncoder().encodeToString(image);
    	
    	
    	model.addAttribute("qrcode", qrcode);
		return new ModelAndView("profile","userBean",userBean);
	}
    
    @PostMapping(value = "/admin/updateUser")
    public ModelAndView updateUser(@ModelAttribute("userBean") @Valid UserBean bean,BindingResult bs,ModelMap model,RedirectAttributes ra,HttpSession session) {
    	
    	if(checkSessionUser(session) == null) {
    		ra.addFlashAttribute("error","Please login first !");
        	return new ModelAndView("redirect:/login");
    	}else if (bs.hasErrors()) {
    		model.addAttribute("error", "Field cannot be blank !");
			return new ModelAndView("editUser");
		}else {
			User u = User.builder()
					.userId(bean.getUserId())
					.userName(bean.getUserName())
					.userEmail(bean.getUserEmail())
					.userMobile(bean.getUserMobile())
					.build();
			User cUser = userService.updateUser(u);
			
			if(cUser == null) {
				model.addAttribute("error", "Update User Fail !");
				return new ModelAndView("editUser");
			}
			UserBean loginUser = (UserBean) session.getAttribute("user");
			if (loginUser.getUserCode().equals(bean.getUserCode())) {
				loginUser.setUserName(bean.getUserName());
				loginUser.setUserEmail(bean.getUserEmail());
				loginUser.setUserMobile(bean.getUserMobile());
				session.setAttribute("loginUser", loginUser);
			}
		}
    	
    	ra.addFlashAttribute("msg", "Update User Successful !");
    	return new ModelAndView("redirect:/profile");
    }
    
    @PostMapping(value = "/admin/changePassword")
    public ModelAndView changePassword(@RequestParam("userId")String userId ,@RequestParam("oldPassword")String oldPw,@RequestParam("newPassword")String newPw, @RequestParam("confirmPassword") String conPw ,ModelMap model,RedirectAttributes ra,HttpSession session) {
    	
    	User user = userService.getById(Long.parseLong(userId));
    
    	boolean isPasswordMatches = bcrypt.matches(oldPw, user.getPassword());
    	
    	if(!isPasswordMatches) {
    		ra.addAttribute("message","Wrong Old Password!");
    	}else if(!newPw.equals(conPw)) {
    		ra.addAttribute("message","New Password and confirm pw are not same.");
    	}else {
    		user.setPassword(PasswordGenerator.generatePassword(newPw));
    		User result = userService.changePassword(user);
    		if(result != null) {
    			ra.addAttribute("message","Successfully Changed Password");
    		}
    	}
    	
    	return new ModelAndView("redirect:/profile");
    	
    }
    
}
