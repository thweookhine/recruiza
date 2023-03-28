package com.project.demo.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.Department;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.repository.JobPostRepository;
import com.project.demo.service.JobPostServiceImpl;

@SpringBootTest
class JobPostServiceTest {

	@Mock
	JobPostRepository jobPostRepo;

	@InjectMocks
	JobPostServiceImpl jobPostService;

	@Autowired
	JobPostRepository repo;

	@Autowired
	JobPostServiceImpl service;

	JobPost getJobPost(int num) {
		Department dept = Department.builder().departmentId(1).departmentCode("D001").departmentName("Testing").build();
		Team team = Team.builder().teamId(1).teamCode("T001").teamName("Testing Team").department(dept).build();

		User user = User.builder().userId((long) 1).userCode("U001").userName("Lily").userEmail("lily@gmail.com")
				.password("lily").userCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30")).userMobile("097878787878")
				.lastAction("update job post").role("DH").userStatus("ACTIVE").build();

		RecruitementResource resource = RecruitementResource.builder().resourceId(1).resourceCode("R001")
				.resourceName("Job.com.mm").resourceMobile("097878787878").link("www.google.com").address("Yangon")
				.contactPerson("Rose").recruitementType("University")
				.resourceCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30")).build();

		JobPosition jobPosition = JobPosition.builder().positionId(1).positionCode("J001")
				.positionName("Senior Developer").build();

		JobPost jp = JobPost.builder().postId(num).postName("JobPost" + num).count(2)
				.comment("Comment for Job Post-" + num).foc(false).postDate(LocalDate.of(2023, 01, 15))
				.dueDate(LocalDate.of(2023, 04, 25)).team(team).user(user).resource(resource).jobPosition(jobPosition)
				.sheetId("Sheet ID").postCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30")).build();

		return jp;
	}

	@Test
	void testCreateJobPost() {
		JobPost jobPost = getJobPost(1);
		when(jobPostRepo.findById(jobPost.getPostId())).thenReturn(Optional.of(jobPost));
		when(jobPostRepo.save(jobPost)).thenReturn(jobPost);
		JobPost result = jobPostService.createJobPost(jobPost);
		assertEquals("JobPost1", result.getPostName());
		assertEquals("Comment for Job Post-1", result.getComment());
		assertEquals(LocalDate.of(2023, 04, 25), result.getDueDate());
		System.out.println(jobPost);
	}

	@Test
	void testUpdateJobPost() {
		JobPost jobPost = getJobPost(1);
		jobPost.setComment("Updated Comment");
		jobPost.setCount(4);
		when(jobPostRepo.findById(jobPost.getPostId())).thenReturn(Optional.of(jobPost));
		when(jobPostRepo.save(jobPost)).thenReturn(jobPost);

		JobPost result = jobPostService.updateJobPost(jobPost);
		assertEquals("JobPost1", result.getPostName());
		assertEquals("Updated Comment", result.getComment());
		assertEquals(LocalDate.of(2023, 04, 25), result.getDueDate());
		assertEquals(4, result.getCount());
	}

	@Test
	void testDeleteJobPost() {
		JobPost jobPost = getJobPost(1);
		jobPostService.deleteJobPost(jobPost);
		verify(jobPostRepo, times(1)).deleteById(jobPost.getPostId());
	}

	@Test
	void testGetAllJobPosts() {
		JobPost jP1 = getJobPost(1);
		JobPost jP2 = getJobPost(2);
		JobPost jP3 = getJobPost(3);
		List<JobPost> list = new ArrayList<>();
		list.add(jP1);
		list.add(jP2);
		list.add(jP3);

		when(jobPostRepo.findAll()).thenReturn(list);

		List<JobPost> result = jobPostService.getAllJobPosts();

		assertEquals(3, result.size());
		assertEquals("Lily", result.get(0).getUser().getUserName());
		assertEquals("Testing Team", result.get(0).getTeam().getTeamName());
		assertEquals("Job.com.mm", result.get(0).getResource().getResourceName());
		assertEquals("Senior Developer", result.get(0).getJobPosition().getPositionName());

	}

	@Test
	void testSearchJobPostsWithCodeAndName() {
		String code = "JP001";
		String name = "JobPost2";
		String start = "";
		String end = "";
		
//		List<JobPost> result = search(code, name, start, end);
//		System.out.println("RESULT SIZE => "+result.size());
//		assertEquals(2, result.size());
//		assertEquals("JP001", result.get(0).getPostCode());
	}
	
	
//	@Test
//	void testSearchJobPostsWithStartAndEnd() {
//		String code = "";
//		String name = "";
//		String start = "2023-02-14";
//		String end = "2023-02-19";
//		
//		List<JobPost> result = search(code, name, start, end);
//		System.out.println("RESULT SIZE => "+result.size());
//		assertEquals(2, result.size());
//		assertEquals("JP001", result.get(0).getPostCode());
//	}
//
//	List<JobPost> search(String code, String name, String start, String end) {
//		
//		System.out.println(" code => " + code + "\n name => " + name+ "\n start => " + start + "\n end => " + end);
//		
//		List<JobPost> list = new ArrayList<>();
//		
//		if(code.equals("") && name.equals("") && start.equals("") && end.equals("")) {
//			System.out.println("All empty");
//			return service.getAllJobPosts();
//		}else if(!code.equals("") || !name.equals("")) {
//			System.out.println("code name search");
//			List<JobPost> result = service.searchWithCodeAndName("%"+code+"%", "%"+name+"%");
//			for (JobPost jp : result) {
//				list.add(jp);
//			}
//		}
		
//		if (!start.isEmpty() && !end.isEmpty()) {
//			List<JobPost> jobPosts = service.searchWithStartDateAndEndDate(start, end);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		} else if (!start.isEmpty() && end.isEmpty()) {
//			List<JobPost> jobPosts = service.searchWithStartDate(start);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		} else if (start.isEmpty() && !end.isEmpty()) {
//			List<JobPost> jobPosts = service.searchWithEndDate(end);
//			for (JobPost jp : jobPosts) {
//				list.add(jp);
//			}
//		}
		
		//for remove duplicate
//		list = list.stream().distinct().toList();
		
		//For remove duplicate
//		Set<JobPost> sets = new HashSet<>();
//		sets.addAll(list);
//		List<JobPost> result = new ArrayList<>();
//		result.addAll(sets);
//		return list;
	//}
}
