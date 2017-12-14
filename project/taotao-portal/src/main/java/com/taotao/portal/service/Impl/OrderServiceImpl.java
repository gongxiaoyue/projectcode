package com.taotao.portal.service.Impl;

import org.springframework.beans.factory.annotation.Value;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;

import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.HttpClientUtil;
import co.taotao.common.pojo.utils.JsonUtils;

public class OrderServiceImpl implements OrderService{

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String createOrder(OrderInfo orderInfo) {
		//把OrderInfo转换成json
		String json = JsonUtils.objectToJson(orderInfo);
		//提交订单数据
		String jsonResult = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, json);
		//转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.format(jsonResult);
		//取订单号
		String orderId = taotaoResult.getData().toString();
		return orderId;
	}

}
