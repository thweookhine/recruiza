package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.demo.entity.JobPost;

public interface JobPostService {

	JobPost createJobPost(JobPost jobPost);

	JobPost updateJobPost(JobPost jobPost);

	void deleteJobPost(JobPost jobPost);

	List<JobPost> getAllJobPosts();

	Page<JobPost> listAllJobPosts(int pageNumber, String sortField, String sortDir, String keyword,String status);
	
	List<JobPost> findJobPost(String keyword,String startDate,String endDate);

	List<JobPost> searchBeforeDueDate(String endDate);

	List<JobPost> searchWithStatus(String status);

	Page<JobPost> searchJobPostsWithoutPending(int pageNumber, String sortField, String sortDir);
	
	List<JobPost> searchAfterDueDate(String dueDate);

	JobPost getByid(long id);

	List<JobPost> searchWithStartDateAndEndDate(String startDate, String endDate);

}
