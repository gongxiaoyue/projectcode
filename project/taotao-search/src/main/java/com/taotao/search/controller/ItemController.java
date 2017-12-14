package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.service.ItemService;

import co.taotao.common.pojo.TaotaoResult;

@Controller
public class ItemController {

	@Autowired
	private ItemService service;
	
	@RequestMapping("/import")
	public TaotaoResult importAll(){
		try {
			TaotaoResult result = service.importItems();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
