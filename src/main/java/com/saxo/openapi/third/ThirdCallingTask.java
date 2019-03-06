package com.saxo.openapi.third;

import java.util.Map;
import java.util.concurrent.Callable;

import com.saxo.openapi.util.HttpClientUtil;

/**
 * @author Yongjie
 *
 */
public class ThirdCallingTask implements Callable<String> {

	private String method = null;
	private String url;
	private Map<String, Object> map;
	private String json = null;
	private String header = null;
	private int secure = 0;

	/**
	 * @param url
	 * @param map
	 */
	public ThirdCallingTask(String url, Map<String, Object> map) {
		super();
		this.url = url;
		this.map = map;
	}

	/**
	 * @param url
	 * @param map
	 * @param json
	 */
	public ThirdCallingTask(int secure, String method, String url, Map<String, Object> map, String json) {
		super();
		this.method = method;
		this.url = url;
		this.map = map;
		this.json = json;
		this.secure = secure;
	}

	public ThirdCallingTask(int secure, String method, String url, Map<String, Object> map, String json,
			String header) {
		super();
		this.method = method;
		this.url = url;
		this.map = map;
		this.json = json;
		this.header = header;
		this.secure = secure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() throws Exception {

		return HttpClientUtil.processRequest(secure, method, url, map, json, header);

	}

}