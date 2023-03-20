package com.project.demo.service;

import static com.project.demo.utils.codeGenerator.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
		// jP.setPostDate(jobPost.getPostDate());
		// jP.setDueDate(jobPost.getDueDate());
		jP.setTeam(jobPost.getTeam());
		jP.setUser(jobPost.getUser());
		jP.setResource(jobPost.getResource());
		jP.setJobPosition(jobPost.getJobPosition());
		// jP.setSheetId(jobPost.getSheetId());
		jP.setPostStatus(jobPost.getPostStatus());
		return jobPostRepo.save(jP);
	}

	@Override
	public void deleteJobPost(JobPost jobPost) {
		jobPostRepo.deleteById(jobPost.getPostId());
	}

	public List<JobPost> searchWithStartDateAndEndDate(String startDate, String endDate) {

		return jobPostRepo.findWithStartDateAndEndDate(startDate, endDate);
	}

	@Override
	public List<JobPost> searchBeforeDueDate(String endDate) {
		return jobPostRepo.findBeforeDueDate(endDate);
	}

	@Override
	public JobPost getByid(long id) {
		return jobPostRepo.findById(id).get();
	}

	@Override
	public List<JobPost> searchWithStatus(String status) {
		return jobPostRepo.findByPostStatus(status);
	}

	@Override
	public Page<JobPost> searchJobPostsWithoutPending(int pageNumber, String sortField, String sortDir,String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);
		
		if(!keyword.isEmpty()) {
			return jobPostRepo.findJobPostsWithKeywordAndNotPending(keyword, pageable);
		}
		
		return jobPostRepo.findJobPostsWithoutPending(pageable);
	}

	@Override
	public Page<JobPost> listAllJobPosts(int pageNumber, String sortField, String sortDir, String keyword,
			String status) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

		if (keyword != null && !status.isEmpty()) {
			return jobPostRepo.findAll(keyword, status, pageable);
		} else if (keyword != null && status.isEmpty()) {
			return jobPostRepo.findAllWithKeyword(keyword, pageable);
		}

		return jobPostRepo.findAll(pageable);

	}

	@Override
	public List<JobPost> getAllJobPosts() {
		return jobPostRepo.findAll();
	}

	public List<JobPost> searchAfterDueDate(String dueDate) {
		return jobPostRepo.findAfterDueDate(dueDate);
	}

	@Override
	public List<JobPost> findJobPost(String keyword, String startDate, String endDate) {

		if (!keyword.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
			return jobPostRepo.findWithKeywordAndStartDateAndEndDate(keyword, startDate, endDate);
		} else if (!keyword.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
			return jobPostRepo.findWithKeywordAndStatus(keyword, "POSTED");
		} else if (!keyword.isEmpty() && !startDate.isEmpty() && endDate.isEmpty()) {
			return jobPostRepo.findWithKeywordAndStartDate(keyword, startDate);
		} else if (!keyword.isEmpty() && startDate.isEmpty() && !endDate.isEmpty()) {
			return jobPostRepo.findWithKeywordAndEndDate(keyword, endDate);
		} else if (keyword.isEmpty() && !startDate.isEmpty() && endDate.isEmpty()) {
			return jobPostRepo.findWithStartDate(startDate);
		} else if (keyword.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
			return jobPostRepo.findWithStartDateAndEndDate(startDate, endDate);
		} else if (keyword.isEmpty() && startDate.isEmpty() && !endDate.isEmpty()) {
			return jobPostRepo.findWithEndDate(endDate);
		} else {
			return jobPostRepo.findByPostStatus("POSTED");
		}

	}

	@Override
	public List<JobPost> searchWithCodeAndName(String code, String name) {
		throw new UnsupportedOperationException("Unimplemented method 'searchWithCodeAndName'");
	}

	@Override
	public List<JobPost> searchWithStartDate(String startDate) {
		throw new UnsupportedOperationException("Unimplemented method 'searchWithStartDate'");
	}

	@Override
	public Integer getCountByPosition(long positionId) {
		return jobPostRepo.getCountByPosition(positionId);
	}

}
