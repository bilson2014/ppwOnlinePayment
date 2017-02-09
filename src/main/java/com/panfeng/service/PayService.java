package com.panfeng.service;

import com.panfeng.domain.BaseMsg;
import com.panfeng.resource.model.DealLog;

public interface PayService {
	BaseMsg pay(DealLog dealLog);
}
