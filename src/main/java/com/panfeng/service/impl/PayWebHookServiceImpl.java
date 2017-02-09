package com.panfeng.service.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.panfeng.domain.GlobalConstant;
import com.panfeng.resource.model.PayWebHook;
import com.panfeng.service.PayWebHookService;
import com.panfeng.util.HttpsUtils;

import cn.beecloud.BCCache;

@Service
public class PayWebHookServiceImpl implements PayWebHookService {

	public static String SUCCESS = "success";

	public static String FAIL = "fail";
	
	static String PAT_HOOK_URL=GlobalConstant.PAY_SERVER + "pay/hook";
	
	private static Logger logger = LoggerFactory.getLogger("service");

	/**
	 * 1.认证签名<br>
	 * 2.消息去重复<br>
	 * 3.认证价格<br>
	 * 4.处理定订单相关逻辑&&返回结果<br>
	 */
	public String hookInfoProcessing(PayWebHook payWebHook) {
		// 流程
		// 1.验证签名
		// 2.发送消息转移到拍片网后台服务器处理
		// 3.根据返回结果返回beecloud信息

		// step 1
		boolean status = authSign(payWebHook.getSign(), BCCache.getAppID() + BCCache.getAppSecret(),
				payWebHook.getTimestamp(), "UTF-8");
		logger.info(status+"");
		if (!status)
			return FAIL;// 签名认证失败

		// 签名数据--> 暂时不做
		//涉及重发
		logger.info("RUL:"+PAT_HOOK_URL +"  |   "+payWebHook.toString());
		String res=HttpsUtils.httpsPost(PAT_HOOK_URL, payWebHook, null,false);
		logger.info("请求结果："+res);
		if("success".equals(res))
			return SUCCESS;
		else
			return FAIL;
	}

	private boolean authSign(String sign, String text, Long key, String input_charset) {
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
		logger.info("本地签名："+mysign);
		long timeDifference = System.currentTimeMillis() - key;
		// 5分钟时差，
		if (mysign.equals(sign) && timeDifference <= 300000) {
			logger.info("未超时");
			return true;
		} else {
			logger.info("超时");
			return false;
		}
	}

	private byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}
}
