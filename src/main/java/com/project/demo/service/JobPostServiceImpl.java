package com.project.demo.service;

import static com.project.demo.utils.codeGenerator.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.repository.JobPostRepository;

@Service
public class JobPostServiceImpl implements JobPostService {

	@Autowired
	JobPostRepository jobPostRepo;

	@Override
	public JobPost createJobPost(JobPost jobPost) {
		JobPost jP = jobPostRepo.save(jobPost);
		jP = jobPostRepo.findById(jobPost.getPostId()).get();
		jP.setPostCode(generateJobPostCode(jP.getPostId()));
		return jobPostRepo.save(jP);
	}

	@Override
	public JobPost updateJobPost(JobPost jobPost) {
		JobPost jP = jobPostRepo.findById(jobPost.getPostId()).get();
		jP.setPostName(jobPost.getPostName());
		jP.setCount(jobPost.getCount());
		jP.setComment(jobPost.getComment());
		jP.setFoc(jobPost.isFoc());
		jP.setPostDate(jobPost.getPostDate());
		jP.setDueDate(jobPost.getDueDate());
		jP.setTeam(jobPost.getTeam());
		jP.setUser(jobPost.getUser());
		jP.setResource(jobPost.getResource());
		jP.setJobPosition(jobPost.getJobPosition());
		jP.setSheetId(jobPost.getSheetId());
		return jobPostRepo.save(jP);
	}

	@Override
	public void deleteJobPost(JobPost jobPost) {
		jobPostRepo.deleteById(jobPost.getPostId());
	}

	@Override
	public List<JobPost> getAllJobPosts() {
		return jobPostRepo.findAll();
	}

//	@Override
//	public List<JobPost> searchJobPosts(String code, String name,String startDate,String endDate) {
//
//		List<JobPost> list = new ArrayList<>();
//		
//		if (code.isEmpty() && name.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
//			return jobPostRepo.findAll();
//		}
//		
//		if (!code.isEmpty() && !name.isEmpty()) {
//			List<JobPost> result = jobPostRepo.findWithCodeAndName(code, name);
//			for(JobPost jp : result ) {
//				list.add(jp);
//			}
//		}
		
//		if (!startDate.isEmpty() && !endDate.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findWithStartDateAndEndDate(startDate, endDate);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		} else if (!startDate.isEmpty() && endDate.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findWithStartDate(startDate);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		} else if (startDate.isEmpty() && !endDate.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findWithEndDate(endDate);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		}
//		
//		list = list.stream().distinct().toList();
//		
//		return list;
//	}
//	
	public List<JobPost> searchWithCodeAndName(String code,String name){
		List<JobPost> jobPosts = jobPostRepo.findWithCodeAndName("%" + code + "%", "%" + name + "%");
		return jobPosts;
	}

	public List<JobPost> searchWithStartDate(String startDate) {

		return jobPostRepo.findWithStartDate(startDate);
	}

	public List<JobPost> searchWithStartDateAndEndDate(String startDate, String endDate) {

		return jobPostRepo.findWithStartDateAndEndDate(startDate, endDate);
	}

	@Override
	public List<JobPost> searchWithEndDate(String endDate) {
		return jobPostRepo.findWithEndDate(endDate);
	}

	@Override
	public JobPost getByid(long id) {
		
		return jobPostRepo.findById(id).get();
	}

//	@Override
//	public List<JobPost> searchJobPosts(String code, String name) {
//
//		List<JobPost> list = new ArrayList<>();

//		if (code.isEmpty() && name.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
//			return jobPostRepo.findAll();
//		}
//
//		if (!code.isEmpty() && !name.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findByCodeAndName("%" + code + "%", "%" + name + "%");
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		}
//
//		if (!startDate.isEmpty() && !endDate.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findByStartDateAndEndDate(startDate, endDate);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		} else if (!startDate.isEmpty() && endDate.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findByStartDate(startDate);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		} else if (startDate.isEmpty() && !endDate.isEmpty()) {
//			List<JobPost> jobPosts = jobPostRepo.findByEndDate(endDate);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		}

//		List<JobPost> jobPosts = jobPostRepo.findByCodeAndName("%"+ code+"%", "%"+name +"%");
//		for (JobPost jp : jobPosts) {
//			System.out.println(jp.getPostName());
//			list.add(jp);
//		}
//
//		list = list.stream().distinct().toList();
//
//		return list;
//	}

}
