package com.saxo.openapi.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.saxo.openapi.third.ThirdServiceAPI;

/**
 *
 * @date 2018/11/25
 * @desc business logic for Number Management
 */
@Service
public class OpenApiService {
	
	private static Logger logger = LogManager.getLogger(OpenApiService.class);
	
	//public Map<String, Object> submitToken(Map<String, Object> paramMap) throws Exception {
		
		
	//}
	
	public Map<String, Object> displayFX(Map<String, Object> paramMap) throws Exception {
		
		return null;
	}
	public Map<String, Object> myInfo(Map<String, Object> paramMap) throws Exception {
		
		return ThirdServiceAPI.myInfo((String)paramMap.get("accessToken"));
	}
	
	
}
