package com.project.demo.entity;

import java.sql.Timestamp;
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
	
	@Column(name = "token")
	private String token;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return enabled == other.enabled && Objects.equals(lastAction, other.lastAction)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(userCode, other.userCode) && Objects.equals(userCreatedTime, other.userCreatedTime)
				&& Objects.equals(userEmail, other.userEmail) && Objects.equals(userId, other.userId)
				&& Objects.equals(userMobile, other.userMobile) && Objects.equals(userName, other.userName)
				&& Objects.equals(userStatus, other.userStatus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, lastAction, password, role, userCode, userCreatedTime, userEmail, userId,
				userMobile, userName, userStatus);
	}
	
	

}
