package com.saxo.openapi.third;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lotey
 * @date 2017/1/4 10:17
 * @desc 第三方服务调用
 */
public class ThirdServiceAPI {

	private static Logger logger = LogManager.getLogger(ThirdServiceAPI.class);
	public static String thirdToken=null;
	public static String baseUrl=null;
	public static String userMe=null;
	public static String connectionTimeout = null;
	public static String api_lotteries_url = null;
	private static ExecutorService executor = null;
	/**
	 * @date 2017/1/12 12:03
	 * @desc 读取配置文件初始化变量
	 */
	static {
		ResourceBundle resource = ResourceBundle.getBundle("application");
		connectionTimeout = resource.getString("third.connection.timeout");
		thirdToken=resource.getString("third.token");
		baseUrl=resource.getString("third.base.url");
		userMe=baseUrl+resource.getString("third.user.me");
		
	}

	
	public static JSONObject myInfo(String token) throws Exception{
		token=token==null?thirdToken:token;
		return JSON.parseObject(getResponse("GET",userMe,null,null,token));
		
	}
	
	private static String getResponse(String method, String url, Map<String, Object> map, String jsonParamStr,String header) throws Exception {

		executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(new ThirdCallingTask(method,url, map, jsonParamStr,header));

		String response = null;
		try {

			response = future.get(Long.parseLong(connectionTimeout), TimeUnit.MILLISECONDS);

		} catch (TimeoutException e) {
			future.cancel(true);
			logger.error("Connection to {} timeout", url);

		} catch (InterruptedException ex) {
			logger.error("interruptted exception", ex);
		} catch (Exception e2) {
			throw e2;
		} finally {

			executor.shutdownNow();
		}
		return response;
	}
}