package com.project.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.demo.entity.Applicant;
import com.project.demo.entity.Department;
import com.project.demo.entity.JobPosition;
import com.project.demo.entity.JobPost;
import com.project.demo.entity.RecruitementResource;
import com.project.demo.entity.Schedule;
import com.project.demo.entity.Team;
import com.project.demo.entity.User;
import com.project.demo.repository.ApplicantRepository;

@SpringBootTest
class ApplicantServiceTest {
	
	@Mock
	ApplicantRepository repo;
	
	@InjectMocks
	ApplicantServiceImpl applicantService;

	@Test
	void testCreateApplicant() {
		//fail("Not yet implemented");

		Department dept = Department.builder()
				.departmentId(1)
				.departmentCode("D001")
				.departmentName("Testing")
				.build();
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.department(dept)
				.build();
		
		User user = User.builder()
				.userId((long)1)
				.userCode("U001")
				.userName("Lily")
				.userEmail("lily@gmail.com")
				.password("lily")
				.userCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.userMobile("097878787878")
				.lastAction("update job post")
				.role("DH")
				.userStatus("ACTIVE")
				.build();
		
		RecruitementResource resource = RecruitementResource.builder()
				.resourceId(1)
				.resourceCode("R001")
				.resourceName("Job.com.mm")
				.resourceMobile("097878787878")
				.link("www.google.com")
				.address("Yangon")
				.contactPerson("Rose")
				.recruitementType("University")
				.resourceCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
		
		JobPosition jobPosition = JobPosition.builder()
				.positionId(1)
				.positionCode("J001")
				.positionName("Senior Developer")
				.build();
		
		JobPost jp = JobPost.builder()
				.postId((long)1)
				.postName("JobPost")
				.count(2)
				.comment("Comment for Job Post-")
				.foc(false)
				.postDate(LocalDate.now())
				.dueDate(LocalDate.of(2023, 04, 25))
				.team(team)
				.user(user)
				.resource(resource)
				.jobPosition(jobPosition)
				.sheetId("Sheet ID")
				.postCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
				
		Applicant applicant = Applicant.builder()
							.applicantId((long)1)
							.applicantCode("A001")
							.applicantName("May Yamin Oo")
							.applicantEmail("may@gmail.com")
							.applicantMobile("09-87654321")
							.address("Yangon,Thamine")
							.link("job.com")
							.currentState("Interview")
							.applicantStatus("Activite")
							.applyTime(LocalDateTime.now())
							.jobPost(jp)
							.schedule(null)
							.build();
		
		
		when(repo.findById((long)1)).thenReturn(Optional.of(applicant));
		when(repo.save(applicant)).thenReturn(applicant);
		
		applicantService.createApplicant(applicant);
		verify(repo,times(2)).save(applicant);


	}

	@Test
	void testUpdateApplicant() {
		//fail("Not yet implemented");

		Department dept = Department.builder()
				.departmentId(1)
				.departmentCode("D001")
				.departmentName("Testing")
				.build();
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.department(dept)
				.build();
		
		User user = User.builder()
				.userId((long)1)
				.userCode("U001")
				.userName("Lily")
				.userEmail("lily@gmail.com")
				.password("lily")
				.userCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.userMobile("097878787878")
				.lastAction("update job post")
				.role("DH")
				.userStatus("ACTIVE")
				.build();
		
		RecruitementResource resource = RecruitementResource.builder()
				.resourceId(1)
				.resourceCode("R001")
				.resourceName("Job.com.mm")
				.resourceMobile("097878787878")
				.link("www.google.com")
				.address("Yangon")
				.contactPerson("Rose")
				.recruitementType("University")
				.resourceCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
		
		JobPosition jobPosition = JobPosition.builder()
				.positionId(1)
				.positionCode("J001")
				.positionName("Senior Developer")
				.build();
		
		JobPost jp = JobPost.builder()
				.postId((long)1)
				.postName("JobPost")
				.count(2)
				.comment("Comment for Job Post-")
				.foc(false)
				.postDate(LocalDate.now())
				.dueDate(LocalDate.of(2023, 04, 25))
				.team(team)
				.user(user)
				.resource(resource)
				.jobPosition(jobPosition)
				.sheetId("Sheet ID")
				.postCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
				
		Applicant applicant = Applicant.builder()
							.applicantId((long)1)
							.applicantCode("A001")
							.applicantName("May Yamin Oo")
							.applicantEmail("may@gmail.com")
							.applicantMobile("09-87654321")
							.address("Yangon,Thamine")
							.link("job.com")
							.currentState("Interview")
							.applicantStatus("Activite")
							.applyTime(LocalDateTime.now())
							.jobPost(jp)
							.schedule(null)
							.build();
		
		applicant.setApplicantMobile("0987654321");
		
		when(repo.findById((long) 1)).thenReturn(Optional.of(applicant));
		when(repo.save(applicant)).thenReturn(applicant);
		
		Applicant result = applicantService.createApplicant(applicant);
		System.out.println(result.getApplicantCode());
		assertEquals("may@gmail.com", result.getApplicantEmail());
		assertEquals("May Yamin Oo", result.getApplicantName());
	}

	@Test
	void testDeleteApplicant() {
		//fail("Not yet implemented");

		Department dept = Department.builder()
				.departmentId(1)
				.departmentCode("D001")
				.departmentName("Testing")
				.build();
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.department(dept)
				.build();
		
		User user = User.builder()
				.userId((long)1)
				.userCode("U001")
				.userName("Lily")
				.userEmail("lily@gmail.com")
				.password("lily")
				.userCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.userMobile("097878787878")
				.lastAction("update job post")
				.role("DH")
				.userStatus("ACTIVE")
				.build();
		
		RecruitementResource resource = RecruitementResource.builder()
				.resourceId(1)
				.resourceCode("R001")
				.resourceName("Job.com.mm")
				.resourceMobile("097878787878")
				.link("www.google.com")
				.address("Yangon")
				.contactPerson("Rose")
				.recruitementType("University")
				.resourceCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
		
		JobPosition jobPosition = JobPosition.builder()
				.positionId(1)
				.positionCode("J001")
				.positionName("Senior Developer")
				.build();
		
		JobPost jp = JobPost.builder()
				.postId((long)1)
				.postName("JobPost")
				.count(2)
				.comment("Comment for Job Post-")
				.foc(false)
				.postDate(LocalDate.now())
				.dueDate(LocalDate.of(2023, 04, 25))
				.team(team)
				.user(user)
				.resource(resource)
				.jobPosition(jobPosition)
				.sheetId("Sheet ID")
				.postCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
				
		Applicant applicant = Applicant.builder()
							.applicantId((long)1)
							.applicantCode("A001")
							.applicantName("May Yamin Oo")
							.applicantEmail("may@gmail.com")
							.applicantMobile("09-87654321")
							.address("Yangon,Thamine")
							.link("job.com")
							.currentState("Interview")
							.applicantStatus("Activite")
							.applyTime(LocalDateTime.now())
							.jobPost(jp)
							.schedule(null)
							.build();
		
		applicantService.deleteApplicant(applicant);
		verify(repo,times(1)).deleteById(applicant.getApplicantId());
		
	}

	@Test
	void testGetAllApplicant() {
		//fail("Not yet implemented");

		Department dept = Department.builder()
				.departmentId(1)
				.departmentCode("D001")
				.departmentName("Testing")
				.build();
		Team team = Team.builder()
				.teamId(1)
				.teamCode("T001")
				.teamName("Testing Team")
				.department(dept)
				.build();
		
		User user = User.builder()
				.userId((long)1)
				.userCode("U001")
				.userName("Lily")
				.userEmail("lily@gmail.com")
				.password("lily")
				.userCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.userMobile("097878787878")
				.lastAction("update job post")
				.role("DH")
				.userStatus("ACTIVE")
				.build();
		
		RecruitementResource resource = RecruitementResource.builder()
				.resourceId(1)
				.resourceCode("R001")
				.resourceName("Job.com.mm")
				.resourceMobile("097878787878")
				.link("www.google.com")
				.address("Yangon")
				.contactPerson("Rose")
				.recruitementType("University")
				.resourceCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
		
		JobPosition jobPosition = JobPosition.builder()
				.positionId(1)
				.positionCode("J001")
				.positionName("Senior Developer")
				.build();
		
		JobPost jp = JobPost.builder()
				.postId((long)1)
				.postName("JobPost")
				.count(2)
				.comment("Comment for Job Post-")
				.foc(false)
				.postDate(LocalDate.now())
				.dueDate(LocalDate.of(2023, 04, 25))
				.team(team)
				.user(user)
				.resource(resource)
				.jobPosition(jobPosition)
				.sheetId("Sheet ID")
				.postCreatedTime(Timestamp.valueOf("2023-02-13 14:08:30"))
				.build();
				
		Applicant applicant1 = Applicant.builder()
							.applicantId((long)1)
							.applicantCode("A001")
							.applicantName("May Yamin Oo")
							.applicantEmail("may@gmail.com")
							.applicantMobile("09-87654321")
							.address("Yangon,Thamine")
							.link("job.com")
							.currentState("Interview")
							.applicantStatus("Activite")
							.applyTime(LocalDateTime.now())
							.jobPost(jp)
							.schedule(null)
							.build();
		
		Applicant applicant2 = Applicant.builder()
				.applicantId((long)1)
				.applicantCode("A001")
				.applicantName("May Yamin Oo")
				.applicantEmail("may@gmail.com")
				.applicantMobile("09-87654321")
				.address("Yangon,Thamine")
				.link("job.com")
				.currentState("Interview")
				.applicantStatus("Activite")
				.applyTime(LocalDateTime.now())
				.jobPost(jp)
				.schedule(null)
				.build();
		
		List<Applicant> list = new ArrayList<>();
		list.add(applicant1);
		list.add(applicant2);
		
when(repo.findAll()).thenReturn(list);
		
		List<Applicant> result = applicantService.getAllApplicant();
		
		assertEquals(2, result.size());


	}

}
