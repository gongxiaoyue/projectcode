package com.taotao.order.service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbOrderItemMapper;
import com.taotao.dao.TbOrderMapper;
import com.taotao.dao.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import co.taotao.common.pojo.TaotaoResult;
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper itemMapper;
	@Autowired
	private TbOrderShippingMapper shippingMapper;
	@Autowired
	private JedisClient client;
	@Value("${REDIS_ORDER_GEN_KEY}")
	private String REDIS_ORDER_GEN_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	@Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
	private String REDIS_ORDER_DETAIL_GEN_KEY;
	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		String id = client.get(REDIS_ORDER_GEN_KEY);
		if (StringUtils.isBlank(id)) {
			//如果订单号生成key不存在设置初始值
			client.set(REDIS_ORDER_GEN_KEY, ORDER_ID_BEGIN);
		}
		Long orderId = client.incr(REDIS_ORDER_GEN_KEY);
		orderInfo.setOrderId(orderId.toString());
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		// 4、插入订单表
		orderMapper.insert(orderInfo);
		// 二、插入订单明细
		// 2、补全字段
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem orderItem : orderItems) {
			// 1、生成订单明细id，使用redis的incr命令生成。
			Long detailId = client.incr(REDIS_ORDER_DETAIL_GEN_KEY);
			orderItem.setId(detailId.toString());
			//订单号
			orderItem.setOrderId(orderId.toString());
			// 3、插入数据
			itemMapper.insert(orderItem);
		}
		// 三、插入物流表
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		// 1、补全字段
		orderShipping.setOrderId(orderId.toString());
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		// 2、插入数据
		shippingMapper.insert(orderShipping);
		// 返回TaotaoResult包装订单号。
		return TaotaoResult.ok(orderId);
	}
	@Override
	public List<String> SearchAll(Date date) {
		// TODO Auto-generated method stub
		List<String> orderList = shippingMapper.selectByDate(date);
		return orderList;
	}
	@Override
	public TaotaoResult deleteOldOrders(List<String> orderList) {
		for (String string : orderList) {
			shippingMapper.deleteByPrimaryKey(string);
		}
		return TaotaoResult.ok();
	}

}
