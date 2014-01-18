package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taupst.service.RankingService;


@Service("rankingService")
public class RankingServiceImpl extends BaseService implements RankingService {

	@Override
	public List<Map<String, Object>> list(String school,Integer type) {
		return this.rankingDao.list(school,type);
	}

	@Override
	public Map<String, Object> getRankByUserId(String users_id,String school,int type) {
		return this.rankingDao.getRankByUserId(users_id,school,type);
	}

}
