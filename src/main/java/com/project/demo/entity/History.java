package com.project.demo.entity;

import java.sql.Timestamp;

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
public class History {
	
	@Id
	@Column(name="id")
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "history_generator"
	)
	@SequenceGenerator(
		name = "history_generator", 
		sequenceName = "history_sequence_name", 
		allocationSize = 1
	)
	private long historyId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "id",
		nullable = false
	)
	@JsonIgnore
	private User user;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "data_name")
	private String dataName;
	
	@Column(name = "table_name")
	private String tableName;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "created_time",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp historyCreatedTime;

}
