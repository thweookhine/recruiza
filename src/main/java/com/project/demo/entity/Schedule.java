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
public class Schedule {
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "schedule_generator"
	)
	@SequenceGenerator(
		name = "schedule_generator", 
		sequenceName = "schedule_sequence_name", 
		allocationSize = 1
	)
	@Column(name="id")
	private long scheduleId;

	@Column(name = "code")
	private String scheduleCode;

	@Column(name ="name")
	private String scheduleName;

	@Column(
		name = "notes",
		columnDefinition = "varchar(255) default 'no notes'")
	private String notes;

	@Column(name = "time")
	private LocalDateTime time;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;

}
