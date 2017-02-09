package com.panfeng.resource.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.panfeng.domain.BaseMsg;
import com.panfeng.resource.model.DealLog;
import com.panfeng.resource.model.PayWebHook;
import com.panfeng.service.PayService;
import com.panfeng.service.PayWebHookService;
import com.panfeng.service.impl.PayWebHookServiceImpl;

import cn.beecloud.BCUtil;

@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger("service");
	static String RETURN_KEY = "billNo";
	static String RESULT_KEY = "result";
	@Autowired
	PayService payService;
	@Autowired
	PayWebHookService payWebHookService;

	/**
	 * 请求支付方法，错误时返回错误代码和错误消息，正确时返回html页面代码
	 * 
	 * @param dealLog
	 * @return
	 */
	@RequestMapping(value = "/income", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public BaseMsg payIncome(@RequestBody final DealLog dealLog) {
		logger.info(dealLog.toString());
		BaseMsg baseMsg = payService.pay(dealLog);
		return baseMsg;
	}

	@RequestMapping("/get/billno")
	public Map<String, String> getBillNo() {
		System.out.println("ok");
		String billNo = BCUtil.generateRandomUUIDPure();
		Map<String, String> map = new HashMap<>();
		map.put(RETURN_KEY, billNo);
		return map;
	}

	@RequestMapping(value = "/hook/callback", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public void callback(@RequestBody PayWebHook payWebHook, HttpServletResponse response) {
		logger.info(payWebHook.toString());
		String res = payWebHookService.hookInfoProcessing(payWebHook);
		if (PayWebHookServiceImpl.SUCCESS.equals(res)) {
			logger.info("res:" + res);
			response.setCharacterEncoding("utf-8");
			try {
				PrintWriter pw = response.getWriter();
				pw.println(res);
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("res:" + PayWebHookServiceImpl.SUCCESS.equals(res));
		}
	}

	// @RequestMapping(value = "/hook/callback", method = RequestMethod.POST,
	// produces = "application/json; charset=UTF-8")
	// public void callback(@RequestBody PayWebHook payWebHook,
	// HttpServletResponse response) {
	// logger.info(payWebHook.toString());
	// response.setCharacterEncoding("utf-8");
	// try {
	// PrintWriter pw = response.getWriter();
	// pw.println("success");
	// pw.flush();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
