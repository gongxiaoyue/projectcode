package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}.html")
	@ResponseBody
	public TbItem getItemById(@PathVariable(value="itemId") Long itemId){
		TbItem result = itemService.getItemById(itemId);
		return result;
	}

	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDescById(@PathVariable(value="itemId") Long itemId){
		String result = itemService.getItemDescById(itemId);
		return result;
	}
	
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParamById(@PathVariable(value="itemId") Long itemId){
		String itemParamById = itemService.getItemParamById(itemId);
		return itemParamById;
	}
}
