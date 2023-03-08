package com.project.demo.utils;

import javax.servlet.http.HttpServletRequest;

public class SiteUrl {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
	public static void main(String args[]) {
		HttpServletRequest request = null ;
		System.out.println("Site URL : " + getSiteURL(request));
	}
}
