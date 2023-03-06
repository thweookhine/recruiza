package com.project.demo.controller;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.WriterException;
import com.project.demo.model.UserBean;
import com.project.demo.utils.qrCodeGenerator;

@RestController
public class ProfileController {
	
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
    	
    	// Convert Byte Array into Base64 Encode String
    	String qrcode = Base64.getEncoder().encodeToString(image);
    	model.addAttribute("qrcode", qrcode);
		return new ModelAndView("profile","userBean",userBean);
	}
}
