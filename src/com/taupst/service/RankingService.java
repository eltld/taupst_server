package com.taupst.service;

import java.util.List;
import java.util.Map;

public interface RankingService {

	List<Map<String, Object>> list(String school, Integer type);

}
