package com.saxo.openapi.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saxo.openapi.service.OpenApiService;
import com.saxo.openapi.util.OpenApiMVCUtil;

/**
 * @author yongjie
 * @date 2018/11/25
 * @desc Number Management controller
 */
@RestController
public class OpenApiController {

	@Resource
	private OpenApiService openApiService;


	/**
	 * @date 2019/02/20
	 * @desc info
	 */
	@ResponseBody
	@RequestMapping(value="/info",method = RequestMethod.GET)
	public Map<String, Object> info() throws Exception {

		return OpenApiMVCUtil.successResponse("open Api Server Running!");
	}
	
	/**
	 * @date 2019/02/20
	 * @desc submitToken
	 */
	/*
	@ResponseBody
	@RequestMapping(value="/submitToken",method = RequestMethod.GET)
	public Map<String, Object> submitToken(@RequestParam Map<String, Object> paramMap) throws Exception {
		
		return openApiService.submitToken(paramMap);
	}
	*/
	/**
	 * @date 2019/02/20
	 * @desc displayFX
	 */
	@ResponseBody
	@RequestMapping(value="/displayFX",method = RequestMethod.GET)
	public Map<String, Object> submitToken(@RequestParam Map<String, Object> paramMap) throws Exception {
		
		return openApiService.displayFX(paramMap);
	}
	@ResponseBody
	@RequestMapping(value="/myInfo",method = RequestMethod.GET)
	public Map<String, Object> myInfo(@RequestParam Map<String, Object> paramMap) throws Exception {
		
		return openApiService.myInfo(paramMap);
	}
	
}
