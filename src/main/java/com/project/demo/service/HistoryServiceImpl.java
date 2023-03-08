package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.demo.entity.History;
import com.project.demo.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService{
	
	@Autowired
	private HistoryRepository repo;

	@Override
	public History createHistory(History history) {
		return repo.save(history);
	}

	@Override
	public Page<History> listAllHistory(int pageNumber, String sortField, String sortDir, String keyword,long userId) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);
		
		if (userId != 0) {
			return repo.findAllWithUname(userId, pageable);
		}
		
		return repo.findAll(pageable);
	}

	@Override
	public List<History> listLast20History(long userId) {
		return repo.findLast20(userId);
	}

}
