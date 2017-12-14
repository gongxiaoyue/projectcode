package com.taotao.order.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import com.taotao.order.service.OrderService;

import co.taotao.common.pojo.TaotaoResult;

public class DeleteOldOrder implements Job {

	@Autowired
	private OrderService orderService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date date = new Date();
		List<String> orderList = orderService.SearchAll(date);
		TaotaoResult s = orderService.deleteOldOrders(orderList);
	}

}
