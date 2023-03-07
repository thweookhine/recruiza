package com.project.demo.utils;

import java.util.*;

public class CheckStatus {
	
	public static String getApplicantStatus(String status) {
		List<String> statusList = Arrays.asList("None","Code Test","Intro Interview","Second Interview","Job Offer","Hired");
		
		int statusIndex = statusList.indexOf(status);
		
		return statusList.get(statusIndex + 1);
	}
	
	public static void main(String argString []) {
		String statuString = "Code Test";
		System.out.println("Status :" + getApplicantStatus(statuString));
	}
}
