package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.service.UpdateSearchService;

import co.taotao.common.pojo.TaotaoResult;

@Controller
public class UpdateSearchController {

	@Autowired
	private UpdateSearchService service;
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult update(Long id){
		try {
			TaotaoResult result = service.UpdateSearch(id);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
