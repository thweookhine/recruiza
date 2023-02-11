package com.project.demo.entity;

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
@Table(uniqueConstraints = { @UniqueConstraint(name = "code_unique", columnNames = "code")})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPosition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "position_generator")
	@SequenceGenerator(name = "postition_generator", sequenceName = "position_sequence_name", allocationSize = 1)
	@Column(name="id")
	private long positionId;
	@Column(name = "code")
	private String positionCode;
	@Column(name = "name")
	private String positionName;
	@OneToOne(mappedBy = "jobPosition")
	private JobPost jobPost;
}
