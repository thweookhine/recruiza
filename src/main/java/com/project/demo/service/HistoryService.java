package com.project.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.demo.entity.History;

public interface HistoryService {

	History createHistory(History history);
	
	Page<History> listAllHistory(int pageNumber, String sortField, String sortDir, String keyword,long userId);

	List<History> listLast20History(long userId);
}
