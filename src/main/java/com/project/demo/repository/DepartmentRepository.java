package com.project.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long>{

}
