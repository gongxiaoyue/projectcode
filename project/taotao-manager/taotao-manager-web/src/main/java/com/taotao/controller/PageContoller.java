package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.itemService;

import co.taotao.common.pojo.EasyUIDateGridResult;

@Controller
public class PageContoller {
	@Autowired
	private itemService is;
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDateGridResult showList(Integer page, Integer rows){
		EasyUIDateGridResult itemList = is.getItemList(page, rows);
		return itemList;
	}
}
