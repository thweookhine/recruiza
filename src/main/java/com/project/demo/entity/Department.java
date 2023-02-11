package com.project.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_generator")
	@SequenceGenerator(name = "department_generator", sequenceName = "department_sequence_name", allocationSize = 1)
	@Column(name="id")
	private long departmentId;
	
	@Column(name="code")
	private String departmentCode;
	
	@Column(name="name")
	private String departmentName;
	
	@OneToMany(mappedBy = "department",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Team> teams;

}
