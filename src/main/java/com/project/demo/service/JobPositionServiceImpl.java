package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entity.JobPosition;
import com.project.demo.repository.JobPositionRepository;
import com.project.demo.utils.codeGenerator;

@Service
public class JobPositionServiceImpl implements JobPositionService{
	
	@Autowired
	JobPositionRepository repo;

	@Override
	public JobPosition createJobPosition(JobPosition jobPosition) {
		// TODO Auto-generated method stub
			JobPosition position = repo.save(jobPosition);
			position=repo.findById(position.getPositionId()).get();
			position.setPositionCode(codeGenerator.generateJobCode(position.getPositionId()));
			return repo.save(position);
	}

	@Override
	public JobPosition updateJobPosition(JobPosition jobPosition) {
		// TODO Auto-generated method stub
		System.out.println("jhahdaljdk" + jobPosition.getPositionId());
		JobPosition jp= repo.findById(jobPosition.getPositionId()).get();
		jp.setPositionCode(jobPosition.getPositionCode());
		jp.setPositionName(jobPosition.getPositionName());
		return repo.save(jp);
	}

	@Override
	public void deleteJobPosition(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
		
	}

	@Override
	public List<JobPosition> getAllJobPosition() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public List<JobPosition> getPositionByCodeAndName(String code, String name) {
		// TODO Auto-generated method stub
		return repo.findByCodeAndName("%"+code+"%","%"+name+"%");
	}

	@Override
	public JobPosition getLastJobPosition() {
		// TODO Auto-generated method stub
		return repo.findLastJobPosition();
	}

	@Override
	public JobPosition getPositionById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	

		
	
}
