package com.panfeng.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.panfeng.domain.BaseMsg;
import com.panfeng.resource.model.DealLog;
import com.panfeng.service.PayService;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCPay;
import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCOrder;

@Service
public class PayServiceImpl implements PayService {
	static int INDENT_TIME_OUT = 360;
	private static Logger logger = LoggerFactory.getLogger("service");
	@Override
	public BaseMsg pay(DealLog dealLog) {
		BaseMsg baseMsg = new BaseMsg();
		if (dealLog == null) {
			baseMsg.setErrorCode(BaseMsg.ERROR);
			baseMsg.setErrorMsg("订单对象不能为空");
			logger.info("订单对象不能为空");
		}
		String type = dealLog.getPayChannel();
		PAY_CHANNEL channel;

		try {
			channel = PAY_CHANNEL.valueOf(type);
			logger.info(channel.name());
		} catch (Exception e) {
			channel = null;
			baseMsg.setErrorCode(BaseMsg.ERROR);
			baseMsg.setErrorMsg("支付通道选择错误");
			logger.info("支付通道选择错误");
		}
		
		
		int points = (int) yuanConversionPoints(dealLog.getPayPrice());
		logger.info("金额："+points);
		BCOrder bcOrder = new BCOrder(channel, points, dealLog.getBillNo(), dealLog.getTitle());// 构建订单对象
		bcOrder.setBillTimeout(INDENT_TIME_OUT);// 订单超时时间6分钟
		if (dealLog.getOptional() != null) {
			bcOrder.setOptional(dealLog.getOptional());// 回调时携带数据（原样返回）
		}
		switch (channel) {
		case ALI_WAP:
			bcOrder.setReturnUrl(dealLog.getReturnUrl());
			try {
				bcOrder = BCPay.startBCPay(bcOrder);
				baseMsg.setErrorCode(BaseMsg.NORMAL);
				baseMsg.setErrorMsg("正常");
				baseMsg.setResult(bcOrder.getHtml());
				logger.info("正常");
			} catch (BCException e) {
				baseMsg.setErrorCode(BaseMsg.ERROR);
				baseMsg.setErrorMsg(e.getMessage());
				logger.info("支付错误"+e.getMessage());
			}
			break;

		case UN_WEB:
			bcOrder.setReturnUrl(dealLog.getReturnUrl());
			try {
				bcOrder = BCPay.startBCPay(bcOrder);
				baseMsg.setErrorCode(BaseMsg.NORMAL);
				baseMsg.setErrorMsg("正常");
				baseMsg.setResult(bcOrder.getHtml());
				logger.info("正常");
			} catch (BCException e) {
				baseMsg.setErrorCode(BaseMsg.ERROR);
				baseMsg.setErrorMsg(e.getMessage());
				logger.info("支付错误"+e.getMessage());
			}
			break;
		default:
			baseMsg.setErrorCode(BaseMsg.ERROR);
			baseMsg.setErrorMsg("支付通道选择错误");
			break;
		}
		return baseMsg;
	}

	private long yuanConversionPoints(double yuan) {
		BigDecimal bdYuan = BigDecimal.valueOf(yuan);
		BigDecimal bdPoints = bdYuan.multiply(BigDecimal.valueOf(100));
		return bdPoints.longValue();
	}

}
