package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.RecruitementResource;

public interface RecruitmentResourceRepository extends JpaRepository<RecruitementResource,Long>{

	@Query(value ="select * from recruit.recruitement_resource where (code like :code) or (name like :name) or (mobile like :mobile) or (recruitement_type like :recruitement_type)",
				nativeQuery=true )
	List<RecruitementResource> findByCodeNameMobileAndType(@Param("code")String code,@Param("name")String name,@Param("mobile")String mobile,@Param("recruitement_type")String recruitement_type);
}
