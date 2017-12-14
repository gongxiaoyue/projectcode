package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.service.SearchService;

import co.taotao.common.pojo.SearchResult;
import co.taotao.common.pojo.TaotaoResult;

@Controller
public class SearchController {

	@Autowired
	private SearchService service;
	
	@RequestMapping("/p")
	@ResponseBody
	public TaotaoResult search(@RequestParam(defaultValue="")String kayword, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="30")Integer rows){
		try {
			String keyword = new String(kayword.getBytes("iso8859-1"), "utf-8");
			SearchResult search = service.search(keyword, page, rows);
			return TaotaoResult.ok(search);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
