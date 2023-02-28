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
public class RecruitementResource {
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "resource_generator"
	)
	@SequenceGenerator(
		name = "resource_generator", 
		sequenceName = "resource_sequence_name", 
		allocationSize = 1
	)
	@Column(name="id")
	private long resourceId;

	@Column(name = "code")
	private String resourceCode;

	@Column(name = "name")
	private String resourceName;

	// for the source link of the resource eg. job.com
	@Column(name = "link")
	private String link;

	@Column(name = "address")
	private String address;

	@Column(name = "mobile")
	private String resourceMobile;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "recruitement_type")
	private String recruitementType;

	@Column(
		name = "created_time",
		columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
	)
	private Timestamp resourceCreatedTime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecruitementResource other = (RecruitementResource) obj;
		return Objects.equals(address, other.address) && Objects.equals(contactPerson, other.contactPerson)
				&& Objects.equals(link, other.link) && Objects.equals(recruitementType, other.recruitementType)
				&& Objects.equals(resourceCode, other.resourceCode)
				&& Objects.equals(resourceCreatedTime, other.resourceCreatedTime) && resourceId == other.resourceId
				&& Objects.equals(resourceMobile, other.resourceMobile)
				&& Objects.equals(resourceName, other.resourceName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, contactPerson, link, recruitementType, resourceCode, resourceCreatedTime,
				resourceId, resourceMobile, resourceName);
	}

//	@OneToOne(mappedBy = "resource")
//	private JobPost jobPost;

}
