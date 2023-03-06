package com.project.demo.service;

import org.springframework.data.domain.Page;

import com.project.demo.entity.History;

public interface HistoryService {

	History createHistory(History history);
	
	Page<History> listAllHistory(int pageNumber, String sortField, String sortDir, String keyword,String username);

}
