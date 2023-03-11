package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.entity.Applicant;
import com.project.demo.model.ApplicantBean;
import com.project.demo.repository.ApplicantRepository;
import com.project.demo.utils.Text;
import com.project.demo.utils.codeGenerator;

@Service
public class ApplicantServiceImpl implements ApplicantService{
	
	@Autowired
	ApplicantRepository repo;
	
	@Autowired
	EmailSenderService emailSenderService;

	@Override
	public Applicant createApplicant(Applicant applicant) {
		// TODO Auto-generated method stub
		Applicant app=repo.save(applicant);
		app=repo.findById(applicant.getApplicantId()).get();
		app.setApplicantCode(codeGenerator.generateApplicantCode(app.getApplicantId()));
		return repo.save(applicant);
	}

	@Override
	public Applicant updateApplicant(Applicant applicant) {
		// TODO Auto-generated method stub
		Applicant app = repo.findById(applicant.getApplicantId()).get();
		app.setApplicantId(applicant.getApplicantId());
		app.setApplicantCode(applicant.getApplicantCode());
		app.setApplicantName(applicant.getApplicantName());
		app.setApplicantEmail(applicant.getApplicantEmail());
		app.setApplicantMobile(applicant.getApplicantMobile());
		app.setAddress(applicant.getAddress());
		app.setLink(applicant.getLink());
		app.setCurrentState(applicant.getCurrentState());
		app.setApplicantStatus(applicant.getApplicantStatus());
		app.setApplyTime(applicant.getApplyTime());
		app.setSchedule(applicant.getSchedule());
		app.setJobPost(applicant.getJobPost());
		
		return repo.save(app);
	}

	@Override
	public void deleteApplicant(Long id) {
		// TODO Auto-generated method stub
		 repo.deleteById(id);
		
	}

	@Override
	public List<Applicant> getAllApplicant() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Page<Applicant> listAllApplicants(int pageNumber, String sortField, String sortDir, String keyword) {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
		
		if (keyword != null) {
			return repo.findSearchAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}

	@Override
	public Applicant getApplicantById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public Page<Applicant> listApplicantProcess(int pageNumber, String sortField, String sortDir, String keyword) {
		
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
		
		if (!keyword.equals("allApplicants")) {
			return repo.findApplicantProcess(keyword, pageable);
		}
			return repo.findAll(pageable);
	}

	@Override
	public Applicant changeApplicantStatus(ApplicantBean bean, String checkStatus, String getStatus,String comment) {
		
		Applicant applicant = repo.findById(bean.getApplicantId()).get();
		
		if (checkStatus.equals("Pass")) {
			String status=applicant.getApplicantStatus();
			applicant.setApplicantStatus(getStatus);
			emailSenderService.sendEmail(applicant.getApplicantEmail(),"You Pass "+ status,Text.getEmailSuccess()+applicant.getApplicantStatus()+".");
			
			
			
			if (getStatus.equals("Hired")) {
				applicant.setCurrentState(checkStatus);
			}
		}else {
			
			emailSenderService.sendEmail(applicant.getApplicantEmail(), "You Fail "+ applicant.getApplicantStatus(), Text.getEmailFail());
			applicant.setCurrentState(checkStatus);
			
		}
		applicant.setComment(comment);
		return repo.save(applicant);
	}

}
