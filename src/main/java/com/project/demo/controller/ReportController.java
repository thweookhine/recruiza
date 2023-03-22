package com.project.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.project.demo.service.JobPostService;

@RestController
public class ReportController {

	@Autowired
	JobPostService jobPostService;
	
	@GetMapping(value = "/recruizareport")
	public ModelAndView recruizAndView(ModelMap model) {
		
		if (jobPostService.getAllJobPosts().size() != 0) {
			model.addAttribute("reportList", jobPostService.recruizaReport());
		}
		
		return new ModelAndView("recruizaReport");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/report")
	public void report(HttpServletResponse response) throws Exception {
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=RecruizaReport.pdf");
		
		// Parameters for report
		Map parameters = new HashMap();
		parameters.put("ReportTitle", "Recruiza Report");
		
		JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:report.jrxml").getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jobPostService.recruizaReport());
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
		JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
	}
	
	@GetMapping(value = "/jobPostReport/{postId}")
	public ModelAndView jobPostReportAndView(@PathVariable("postId") long postId, ModelMap model) {
		
		model.addAttribute("para", jobPostService.getParameters(postId));
		model.addAttribute("applicantList", jobPostService.getApplicantsList(postId));
		model.addAttribute("postId", postId);
		
		return new ModelAndView("jobPostReport");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/jobpostreport/{postId}")
	public void jobPosteport(@PathVariable("postId") long postId, HttpServletResponse response) throws Exception {
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=RecruizaReport.pdf");
		
		// Parameters for report
		Map parameters = jobPostService.getParameters(postId);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:jobPostReport.jrxml").getAbsolutePath());
	
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
		
		JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
	}
}
