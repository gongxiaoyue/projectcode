package com.taotao.order.service;

import java.util.Date;
import java.util.List;

import com.taotao.order.pojo.OrderInfo;

import co.taotao.common.pojo.TaotaoResult;
	
public interface OrderService {
	TaotaoResult createOrder(OrderInfo info);
	List<String> SearchAll(Date date);
	TaotaoResult deleteOldOrders(List<String> orderList);
}
