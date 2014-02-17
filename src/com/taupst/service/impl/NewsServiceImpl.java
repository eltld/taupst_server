package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taupst.service.NewsService;

@Service("newsService")
public class NewsServiceImpl extends BaseService implements NewsService{

	@Override
	public List<Map<String, Object>> list(String users_id, String news_id, int type) {
		return this.newsDao.list(users_id,news_id, type);
	}

	@Override
	public Map<String, Object> tmList(String source) {
		return this.newsDao.tmList(source);
	}

	@Override
	public Map<String, Object> signObject(String source) {
		return this.newsDao.signObject(source);
	}

	@Override
	public Map<String, Object> signInfo(String source) {
		return this.signDao.signInfo(source);
	}

	@Override
	public Map<String, Object> aftetTaskInfo(String source) {
		return this.newsDao.aftetTaskInfo(source);
	}

}
