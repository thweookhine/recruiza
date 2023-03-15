package com.project.demo.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name="comment")
	private String comment;

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

	@Column(name = "apply_time",
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp applyTime;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
		name = "job_post_id",
		nullable = false
	)
	private JobPost jobPost;
	
	@JsonIgnore
	@OneToOne(mappedBy = "applicant")
	private Schedule schedule;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
		name = "jobPosition_id",
		referencedColumnName = "id",
		nullable = false
	)
	private JobPosition jobPosition;
	
	@Lob
	@Column(name="file")
	private byte[] file;
	
	@Column(name="source")
	private String source;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Applicant other = (Applicant) obj;
		return Objects.equals(address, other.address) && Objects.equals(applicantCode, other.applicantCode)
				&& Objects.equals(applicantEmail, other.applicantEmail) && applicantId == other.applicantId
				&& Objects.equals(applicantMobile, other.applicantMobile)
				&& Objects.equals(applicantName, other.applicantName)
				&& Objects.equals(applicantStatus, other.applicantStatus) && Objects.equals(applyTime, other.applyTime)
				&& Objects.equals(comment, other.comment) && Objects.equals(currentState, other.currentState)
				&& Arrays.equals(file, other.file) && Objects.equals(jobPosition, other.jobPosition)
				&& Objects.equals(jobPost, other.jobPost) && Objects.equals(link, other.link)
				&& Objects.equals(schedule, other.schedule) && Objects.equals(source, other.source);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(file);
		result = prime * result + Objects.hash(address, applicantCode, applicantEmail, applicantId, applicantMobile,
				applicantName, applicantStatus, applyTime, comment, currentState, jobPosition, jobPost, link, schedule,
				source);
		return result;
	}
	

}
