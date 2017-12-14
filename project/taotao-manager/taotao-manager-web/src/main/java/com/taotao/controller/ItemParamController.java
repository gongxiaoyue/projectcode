package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemParam;

import co.taotao.common.pojo.TaotaoResult;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParam param;
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable(value="cid") long cid){
		TaotaoResult itemParam = param.getItemParam(cid);
		return itemParam;
	}
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult saveParam(@PathVariable(value="cid") long cid, String paramData){
		TaotaoResult result = param.saveItemParam(cid, paramData);
		return result;
	}
	@RequestMapping("/edit/{cid}")
	@ResponseBody
	public TaotaoResult editParam(@PathVariable(value="cid") long cid, String paramData){
		TaotaoResult result = param.editItemParam(cid, paramData);
		return result;
	}
	
	@RequestMapping("/delete/{cid}")
	@ResponseBody
	public TaotaoResult deleteParam(@PathVariable(value="cid") long cid, String paramData){
		TaotaoResult result = param.deleteItemParam(cid);
		return result;
	}
}
