package com.taupst.service.impl;

import org.springframework.stereotype.Service;

import com.taupst.service.PullService;

@Service("pullService")
public class PullServiceImpl extends BaseService implements PullService{

	@Override
	public boolean update(String pull_id, String user_id, String channel_id) {
		return this.pullDao.update(pull_id, user_id, channel_id);
	}

}
