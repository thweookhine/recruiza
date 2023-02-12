package com.project.demo.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
		@UniqueConstraint(name = "code_unique", columnNames = "code"),
		@UniqueConstraint(name = "email_unique", columnNames = "email")
	}
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE, 
		generator = "user_generator"
	)
	@SequenceGenerator(
		name = "user_generator", 
		sequenceName = "user_sequence_name", allocationSize = 1
	)
	@Column(name = "id")
	private Long userId;

	@Column(name = "code")
	private String userCode;

	@Column(name = "name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "email",
	columnDefinition = "varchar(255) default 'not added'")
	private String userEmail;

	@Column(name = "mobile")
	private String userMobile;

	@Column(name = "last_action")
	private String lastAction;

	@Column(name = "role")
	private String role;

	@Column(name = "status",
	columnDefinition = "varchar(255) default 'ACTIVE'")
	private String userStatus;
	
	@Column(name = "created_time",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp userCreatedTime;
	
	@Column(name = "enabled")
	private boolean enabled;

}
