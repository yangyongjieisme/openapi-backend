package com.saxo.openapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saxo.openapi.service.OpenApiService;
import com.saxo.openapi.third.ThirdServiceAPI;
import com.saxo.openapi.util.OpenApiMVCUtil;
import com.saxo.openapi.weixin.WxUtil;


@RestController
public class WeChatController {

	private static Logger logger = LogManager.getLogger(WeChatController.class);

	@ResponseBody
	@RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
	public Map<String, Object> doLogin(@RequestParam Map<String, Object> paramsMap) {
		
		logger.debug("Start get SessionKey");
		String rawData = (String) paramsMap.get("rawData");
		String signature = (String) paramsMap.get("signature");
		String encrypteData = (String) paramsMap.get("encrypteData");
		String iv = (String) paramsMap.get("iv");
		String code = (String) paramsMap.get("code");

		Map<String, Object> map = new HashMap<String, Object>();

		JSONObject responseObj = getSessionKeyOrOpenId(code);
		logger.debug("response SessionAndopenId=" + responseObj);
		String openId = "";
        String unionId = "";
        String sessionKey = "";
        
        if(responseObj.containsKey("session_key") && responseObj.containsKey("openid") && responseObj.containsKey("unionid")){
            sessionKey = responseObj.getString("session_key");
            openId = responseObj.getString("openid");
            unionId = responseObj.getString("unionid");
            String sha1 = rawData + sessionKey;
            logger.debug("openid=" + openId + ",session_key=" + sessionKey);
       //     String sinature2 = WXUtils.getSha1(sha1);
        //    if(signature.equals(sinature2)){//签名验证
        //        JSONObject info = WXUtils.getUserInfo(encrypteData,sessionKey,iv);
       //         map.put("userInfo", info);
        //    }
        }

		return map;
	}

	


	private  JSONObject getSessionKeyOrOpenId(String code) {

		Map<String, Object> requestUrlParam = new HashMap<String, Object>();
		requestUrlParam.put("appid", "你的小程序appId");
		requestUrlParam.put("secret", "你的小程序appSecret");
		requestUrlParam.put("js_code", code);
		requestUrlParam.put("grant_type", "authorization_code");

		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) JSON.parse(
					ThirdServiceAPI.getResponse(1, "GET", WxUtil.mini_api_login_url, requestUrlParam,null, null));

		} catch (Exception e) {
			logger.error(e);
		}
		return jsonObject;
	}
}
