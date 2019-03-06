package com.saxo.openapi.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saxo.openapi.weixin.MyX509TrustManager;

/**
 * @author
 * @date 
 * @desc HTTP
 */
public class HttpClientUtil {

	private static Logger logger = LogManager.getLogger(HttpClientUtil.class);

	/**
	 * HTTP GET
	 * 
	 * @param baseUrl  
	 * @param paramMap 
	 */
	public static String getRequest(String baseUrl, Map<String, Object> paramMap, String header) throws Exception {
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(baseUrl);
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(5000).build();
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("Authorization", "Bearer " + header);
		try {
			
			if (paramMap != null && paramMap.size() > 0) {
				URIBuilder builder = new URIBuilder().setPath(baseUrl);
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				NameValuePair nameValuePair = null;
				Iterator ite = paramMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
					nameValuePair = new BasicNameValuePair(entry.getKey(),
							entry.getValue() instanceof String ? entry.getValue() : String.valueOf(entry.getValue()));
					paramList.add(nameValuePair);
				}
				
				builder.addParameters(paramList);
				
				httpGet.setURI(builder.build());
			}
			
			CloseableHttpResponse response = client.execute(httpGet);
			
			int status_code = response.getStatusLine().getStatusCode();
			logger.info("==========================调用状态码：{}==========================", status_code);
			
			String respStr = null;
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				respStr = EntityUtils.toString(responseEntity, "UTF-8");
			}
			logger.info("=========================={}开始==========================", CommonUtil.getCurrentDateTimeStr());
			logger.info("==========================结果集：{}==========================", respStr);
			logger.info("=========================={}结束==========================", CommonUtil.getCurrentDateTimeStr());
			
			EntityUtils.consume(responseEntity);
			return respStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HTTP POST
	 * 
	 * @param baseUrl  
	 * @param paramMap 
	 */
	public static String postRequest(String baseUrl, Map<String, Object> paramMap) {
		
		CloseableHttpClient client = HttpClients.createDefault();
	
		HttpPost httpPost = new HttpPost(baseUrl);
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(5000).build();
		httpPost.setConfig(requestConfig);
		try {
			
			if (paramMap != null && paramMap.size() > 0) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				NameValuePair nameValuePair = null;
				Iterator ite = paramMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
					nameValuePair = new BasicNameValuePair(entry.getKey(),
							entry.getValue() instanceof String ? entry.getValue() : String.valueOf(entry.getValue()));
					paramList.add(nameValuePair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
			}
			logger.info("==========================发送信息：{}，datamap:{}==========================", baseUrl, paramMap);
			
			CloseableHttpResponse response = client.execute(httpPost);
			
			int status_code = response.getStatusLine().getStatusCode();
			logger.info("==========================调用状态码：{}==========================", status_code);
			
			String respStr = null;
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				respStr = EntityUtils.toString(responseEntity, "UTF-8");
			}
			logger.debug("=========================={}开始==========================",
					CommonUtil.getCurrentDateTimeStr());
			logger.debug("==========================结果集：{}==========================", respStr);
			logger.debug("=========================={}结束==========================",
					CommonUtil.getCurrentDateTimeStr());
			
			EntityUtils.consume(responseEntity);
			return respStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HTTP POST
	 * 
	 * @param baseUrl
	 * @param paramMap 
	 */
	public static String postJsonRequest(String baseUrl, Map<String, Object> paramMap, String jsonParamStr) {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(baseUrl);
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(5000).build();
		httpPost.setConfig(requestConfig);
		try {
			
			if (paramMap != null && paramMap.size() > 0) {
				Iterator ite = paramMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
					httpPost.addHeader(entry.getKey(),
							entry.getValue() instanceof String ? entry.getValue() : String.valueOf(entry.getValue()));
				}
			}
			StringEntity entity = null;
			if (StringUtils.isNotEmpty(jsonParamStr)) {
				entity = new StringEntity(jsonParamStr, Consts.UTF_8);
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			httpPost.setEntity(entity);
			
			CloseableHttpResponse response = client.execute(httpPost);
			
			int status_code = response.getStatusLine().getStatusCode();
			logger.info("==========================调用状态码：{}==========================", status_code);
			
			String respStr = null;
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				respStr = EntityUtils.toString(responseEntity, "UTF-8");
			}
			logger.info("=========================={}开始==========================", CommonUtil.getCurrentDateTimeStr());
			logger.info("==========================结果集：{}==========================", respStr);
			logger.info("=========================={}结束==========================", CommonUtil.getCurrentDateTimeStr());
			
			EntityUtils.consume(responseEntity);
			return respStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String processRequest(int secure, String method, String baseUrl, Map<String, Object> paramMap,
			String jsonParamStr, String header) {
		
		CloseableHttpClient client = HttpClients.createDefault();
		if (secure == 1) {
			client = HttpClients.custom().setSSLSocketFactory(useTrustingTrustManager()).build();
		}
		
		HttpRequestBase httpReq = creatRequest(method, baseUrl);

		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(5000).build();
		httpReq.setConfig(requestConfig);
		if (header != null) {
			httpReq.setHeader("Authorization", "Bearer " + header);
		}

		try {
			
			if (paramMap != null) {
				URIBuilder builder = new URIBuilder().setPath(baseUrl);
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				NameValuePair nameValuePair = null;
				Iterator ite = paramMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
					nameValuePair = new BasicNameValuePair(entry.getKey(),
							entry.getValue() instanceof String ? entry.getValue() : String.valueOf(entry.getValue()));
					paramList.add(nameValuePair);
				}
				
				builder.addParameters(paramList);
				
				httpReq.setURI(builder.build());
			}
			StringEntity entity = null;
			if (StringUtils.isNotEmpty(jsonParamStr)) {
				entity = new StringEntity(jsonParamStr, Consts.UTF_8);
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");

			}
			if (httpReq instanceof HttpPost) {
				((HttpPost) httpReq).setEntity(entity);
			}
			
			CloseableHttpResponse response = client.execute(httpReq);
			
			int status_code = response.getStatusLine().getStatusCode();
			logger.info("==========================调用状态码：{}==========================", status_code);
			
			String respStr = null;
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				respStr = EntityUtils.toString(responseEntity, "UTF-8");
			}
			logger.info("=========================={}开始==========================", CommonUtil.getCurrentDateTimeStr());
			logger.info("==========================结果集：{}==========================", respStr);
			logger.info("=========================={}结束==========================", CommonUtil.getCurrentDateTimeStr());
			
			EntityUtils.consume(responseEntity);
			return respStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SSLConnectionSocketFactory useTrustingTrustManager() {
		try {
			// First create a trust manager that won't care.
			MyX509TrustManager trustManager = new MyX509TrustManager();
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { trustManager }, null);
			// ssl socket factory
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[] { "TLS" }, null,
					SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			return sslsf;
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	private static HttpRequestBase creatRequest(String method, String url) {

		switch (method) {

		case "HEAD":
			return new HttpHead(url);
		case "GET":
			return new HttpGet(url);
		case "DELETE":
			return new HttpDelete(url);
		case "OPTIONS":
			return new HttpOptions(url);
		case "PATCH":
			return new HttpPatch(url);
		case "PUT":
			return new HttpPut(url);
		case "POST":
			return new HttpPost(url);

		default:
			throw new RuntimeException("Invalid HTTP method " + method);
		}

	}
}