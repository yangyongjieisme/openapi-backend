package com.saxo.openapi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2018/11/25
 * @desc utility class
 */
public class OpenApiMVCUtil {
	public static String INTERNAL_ERROR = "There is some problem,please try again later";
	public static String HTTP_404_ERROR = "Invalid request";
	public static String HTTP_500_ERROR = "Bad request";
	public static String HTTP_403_ERROR = "Not Authorized";
	
	
	/**
	 * @date 2018/11/25
	 * @desc check empty string
	 */
	public static boolean validateEmpty(String input) {

		return (input == null) || (input.length() == 0);
	}

	/**
	 * @date 2018/11/25
	 * @desc wrap good response
	 */
	public static Map<String, Object> successResponse(Object data) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 200);
		map.put("data", data);
		return map;

	}
	/**
	 * @date 2018/11/25
	 * @desc wrap bad response
	 */
	public static Map<String, Object> errorResponse(String message) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", -1);
		map.put("messge", message);
		return map;
	}

}
