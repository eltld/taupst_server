package com.taupst.dao;

import java.util.List;
import java.util.Map;

public interface RankingDao {

	List<Map<String, Object>> list(String school, Integer type);

}
