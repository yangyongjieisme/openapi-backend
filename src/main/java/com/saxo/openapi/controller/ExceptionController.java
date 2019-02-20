package com.saxo.openapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.saxo.openapi.util.OpenApiMVCUtil;

/**
 * @date 2018/11/25
 * @desc Error Handler
 */
@ControllerAdvice
@RestController
public class ExceptionController {

	private static Logger logger = LogManager.getLogger(ExceptionController.class);

	@ExceptionHandler(Exception.class)
	public Map<String, Object> hanleInternalError(HttpServletRequest httpServletRequest, Exception ex) {
		
		return OpenApiMVCUtil.errorResponse(ex.getMessage());
	}

}