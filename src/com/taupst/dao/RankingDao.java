package com.taupst.dao;

import java.util.List;
import java.util.Map;

public interface RankingDao {

	List<Map<String, Object>> list(String school, Integer type);

	Map<String, Object> getRankByUserId(String users_id,String school,int type);

}
