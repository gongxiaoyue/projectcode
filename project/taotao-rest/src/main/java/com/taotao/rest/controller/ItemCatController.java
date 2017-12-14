package com.taotao.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;


import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

import co.taotao.common.pojo.utils.JsonUtils;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService catService;
	@RequestMapping(value="/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	public Object getItemCatList(String callback){
		ItemCatResult itemCatList = catService.getItemCatList();
		if(StringUtils.isBlank(callback)){
			String json = JsonUtils.objectToJson(itemCatList);
			return json;
		}/*
		String json = JsonUtils.objectToJson(itemCatList);
		return callback +"("+json+")";*/
		MappingJacksonValue jacksonValue = new MappingJacksonValue(itemCatList);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}
