package com.project.demo.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
	@Id
	@Column(name="id")
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "team_generator"
	)
	@SequenceGenerator(
		name = "team_generator", 
		sequenceName = "team_sequence_name", 
		allocationSize = 1
	)
	private long teamId;
	
	@Column(name="code")
	private String teamCode;
	
	@Column(name="name")
	private String teamName;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
		name = "department_id",
		referencedColumnName = "id",
		nullable = false
	)
	@JsonIgnore
	private Department department;

	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", teamCode=" + teamCode + ", teamName=" + teamName + ", department="
				+ department + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		return Objects.equals(department, other.department) && Objects.equals(teamCode, other.teamCode)
				&& teamId == other.teamId && Objects.equals(teamName, other.teamName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, teamCode, teamId, teamName);
	}

	
}
