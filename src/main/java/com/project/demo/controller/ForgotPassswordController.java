package com.project.demo.controller;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.demo.entity.User;
import com.project.demo.service.UserService;
import com.project.demo.utils.SiteUrl;

import net.bytebuddy.utility.RandomString;

@RestController
public class ForgotPassswordController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
    
    @PostMapping(value = "/forgotPassword")
    public ModelAndView checkUserMail(HttpServletRequest request,RedirectAttributes ra) {
    	
    	String email = request.getParameter("email");
    	String token = RandomString.make(45);
    	
    	User user = userService.updateToken(token, email);
    	
    	if (user != null) {
    		// generate reset password link
    		String resetPasswordLink = SiteUrl.getSiteURL(request) + "/reset_password?token=" + token;
    		// send mail
				try {
					sendEmail(email, resetPasswordLink);
					ra.addFlashAttribute("msg", "We have sent a reset password link to your email :" + email);
				} catch (Exception e) {
					ra.addFlashAttribute("mailError", "Error while sending email.");
				}
    	}else {
    		ra.addFlashAttribute("mailError", "Could not find any user with email :" + email);
    	}
    	
    	return new ModelAndView("redirect:/login");
    }
    
    @GetMapping(value = "/reset_password")
    public ModelAndView resetPasswordForm(@Param(value = "token") String token,RedirectAttributes ra) {
    	User user = userService.getUser(token);
    	if (user == null) {
    		ra.addFlashAttribute("msg", "Reset your password");
    		ra.addFlashAttribute("error", "Invalid Token");
    	}else {
    		ra.addFlashAttribute("token", token);
    	}
    	return new ModelAndView("redirect:/login");
    }
    
    @PostMapping(value = "/reset_password")
    public ModelAndView processResetPassword(HttpServletRequest request, ModelMap model, RedirectAttributes ra) {
    	String token = request.getParameter("token");
    	String password = request.getParameter("password");
    	
    	User user = userService.getUser(token);
    	if (user == null) {
    		ra.addFlashAttribute("msg", "Reset your password");
    		ra.addFlashAttribute("error", "Invalid Token");
    	}else {
    		userService.updatePassword(user, password);
    		ra.addFlashAttribute("msg", "You have successfully changed your password.");
    	}
    	return new ModelAndView("redirect:/login");
    }

	private void sendEmail(String email, String resetPasswordLink) throws Exception {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("contact@dat.com", "DAT Support");
		helper.setTo(email);
		
		String subject = "Here's the link to reset your password";
		String content = "<p>Hello,</p>"
						+"<p>You have requested to reset your password.</p>"
						+"<p>Click the link below to change your password :</p>"
						+"<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a><b></p>"
						+"<p>Ignore this email if you do remember your password, or you have not made the request.";
		
		helper.setSubject(subject);
		helper.setText(content, true);
		
		mailSender.send(message);
	}
}
