package com.project.demo.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "code_unique", columnNames = "code")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobpost_generator")
	@SequenceGenerator(name = "jobpost_generator", sequenceName = "jobpost_sequence_name", allocationSize = 1)
	@Column(name = "id")
	private long postId;

	@Column(name = "code")
	private String postCode;

	@Column(name = "name")
	private String postName;

	@Column(name = "count")
	private int count;

	@Column(name = "comment")
	private String comment;

	@Column(name = "foc")
	private boolean foc;

	@Column(name = "post_date")
	private LocalDate postDate;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@OneToOne(cascade = CascadeType.ALL)
	private Department department;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	private Team team;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "resource_id")
	private RecruitementResource resource;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobposition_id")
	private JobPosition jobPosition;

	@Column(name = "sheet_id")
	private String sheetId;

	@Column(name = "post_status")
	private String postStatus;

	@Column(name = "created_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp postCreatedTime;

	@OneToMany(mappedBy = "jobPost", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Applicant> applicants;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobPost other = (JobPost) obj;
		return Objects.equals(applicants, other.applicants) && Objects.equals(comment, other.comment)
				&& count == other.count && Objects.equals(dueDate, other.dueDate) && foc == other.foc
				&& Objects.equals(jobPosition, other.jobPosition) && Objects.equals(postCode, other.postCode)
				&& Objects.equals(postCreatedTime, other.postCreatedTime) && Objects.equals(postDate, other.postDate)
				&& postId == other.postId && Objects.equals(postName, other.postName)
				&& Objects.equals(resource, other.resource) && Objects.equals(sheetId, other.sheetId)
				&& Objects.equals(team, other.team) && Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicants, comment, count, dueDate, foc, jobPosition, postCode, postCreatedTime, postDate,
				postId, postName, resource, sheetId, team, user);
	}

}
