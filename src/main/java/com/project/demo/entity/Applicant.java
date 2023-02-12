package com.project.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
	uniqueConstraints = { 
		@UniqueConstraint(
			name = "code_unique", 
			columnNames = "code"
		)
	}
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "applicant_generator"
	)
	@SequenceGenerator(
		name = "applicant_generator", 
		sequenceName = "applicant_sequence_name", 
		allocationSize = 1
	)
	@Column(name="id")
	private long applicantId;

	@Column(name = "code")
	private String applicantCode;

	@Column(name = "name")
	private String applicantName;

	@Column(name = "email")
	private String applicantEmail;

	@Column(name = "mobile")
	private String applicantMobile;

	@Column(name = "address")
	private String address;

	@Column(name = "link")
	private String link;

	@Column(
		name = "current_state",
		columnDefinition = "varchar(255) default 'Code Test'"
	)
	private String currentState;

	@Column(
		name = "status",
		columnDefinition = "varchar(255) default 'Pending'"
	)
	private String applicantStatus;

	@Column(name = "apply_time")
	private LocalDateTime applyTime;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "job_post_id")
	private JobPost jobPost;
	
	@OneToOne(mappedBy = "applicant")
	private Schedule schedule;
	
}
