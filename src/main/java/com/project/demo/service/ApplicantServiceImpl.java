package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.demo.entity.Applicant;
import com.project.demo.model.ApplicantBean;
import com.project.demo.repository.ApplicantRepository;
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
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 9, sort);
		
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
	public Page<Applicant> listApplicantProcess(int pageNumber, String sortField, String sortDir, String keyword, String searchKey) {
		
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 9, sort);
		
		if (!keyword.equals("allApplicants")) {
			return repo.findApplicantProcess(keyword, searchKey, pageable);
		}
			return repo.findWithSearchKey(searchKey, pageable);
	}

	@Override
	public Applicant changeApplicantStatus(ApplicantBean bean, String checkStatus, String getStatus,String comment) {
		
		Applicant applicant = repo.findById(bean.getApplicantId()).get();
		
		if (checkStatus.equals("Pass")) {
			String status=applicant.getApplicantStatus();
			try {
				if(status.equalsIgnoreCase("None")) {
				//emailSenderService.sendEmail(applicant.getApplicantEmail(),"Hello Applicant ","Dear Applicants,You have the opportunity to answer the code test!");
				applicant.setApplicantStatus(getStatus);
				}else if(status.equalsIgnoreCase("Code Test")) {
					//emailSenderService.sendEmail(applicant.getApplicantEmail(),"Hello Applicant ","Dear Applicant,You pass the Code Test.You have the opportunity to do the next state,Intro Interview.");
					applicant.setApplicantStatus(getStatus);
				}else if(status.equalsIgnoreCase("Intro Interview")) {
					//emailSenderService.sendEmail(applicant.getApplicantEmail(),"Hello Applicant ","Dear Applicants,You pass intro interview successfully and we want you to interview for the second time!");
					applicant.setApplicantStatus(getStatus);
				}else {
					//emailSenderService.sendEmail(applicant.getApplicantEmail(),"Hello Applicant ","Dear Applicants,You pass the second interview and we want you to hire position you applied!");
					applicant.setApplicantStatus(getStatus);
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Connection Error!");
			}
			
		
			if (getStatus.equals("Hired")) {
				applicant.setCurrentState(checkStatus);
			}
		}else {
			try {
				//emailSenderService.sendEmail(applicant.getApplicantEmail(), "Hello Applicant ","Sorry Applicant,You lost the opportunity to join our Company.Try Next Time!");
				applicant.setCurrentState(checkStatus);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Connection Error!");
			}
			
			
		}
		applicant.setComment(comment);
		return repo.save(applicant);
	}
	
	@Override
	public List<Applicant> getApplicantList(Long postId) {
		System.out.println(repo.getApplicantsByJobPostId(postId).size());
		return repo.getApplicantsByJobPostId(postId);
	}
}
