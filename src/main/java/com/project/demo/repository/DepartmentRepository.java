package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.demo.entity.Department;
import java.lang.String;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	@Query(value = "select * from recruit.department where (code like :code) or (name like :name)", nativeQuery = true)
	List<Department> searchDept(@Param("code") String code, @Param("name") String name);

	@Query(value = "select * from recruit.department order by id desc limit 1", nativeQuery = true)
	Department findLastDept();
	
	List<Department> findByDepartmentName(String departmentname);
	
	@Query(value = "select * from recruit.department where name = :name ", nativeQuery = true)
	Department searchWithName(@Param("name")String name);
	
	@Query("SELECT d from Department d WHERE "
			+ "CONCAT(d.departmentId, ' ', d.departmentCode, ' ', d.departmentName, ' ')"
			+ "LIKE %?1%")
	public Page<Department> findAll(String keyword, Pageable pageable);
	
}
