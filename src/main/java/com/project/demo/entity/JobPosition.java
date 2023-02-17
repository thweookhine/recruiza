package com.project.demo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class JobPosition {
	
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "position_generator"
	)
	@SequenceGenerator(
		name = "position_generator", 
		sequenceName = "position_sequence_name", 
		allocationSize = 1
	)
	@Column(name="id")
	private long positionId;

	@Column(name = "code")
	private String positionCode;
	
	@Column(name = "name")
	private String positionName;

	@OneToOne(mappedBy = "jobPosition")
	private JobPost jobPost;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobPosition other = (JobPosition) obj;
		return Objects.equals(jobPost, other.jobPost) && Objects.equals(positionCode, other.positionCode)
				&& positionId == other.positionId && Objects.equals(positionName, other.positionName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobPost, positionCode, positionId, positionName);
	}
}
