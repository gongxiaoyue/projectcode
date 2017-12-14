package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.itemService;

import co.taotao.common.pojo.EasyUIDateGridResult;
import co.taotao.common.pojo.TaotaoResult;

@Controller
public class ItemController {
	@Autowired
	private itemService its;
	@Value("${SEARCH_UPDATA_URL}")
	private String SEARCH_UPDATA_URL;
	@RequestMapping("/item/{itemid}")
	@ResponseBody
	public TbItem getItem(@PathVariable Long itemid){
		TbItem ti = its.getTBitemById(itemid);
		return ti;
	}
	@RequestMapping(value="/item/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDateGridResult getItemList(Integer page, Integer rows) {
		EasyUIDateGridResult itemList = its.getItemList(page, rows);
		return itemList;
	}
	
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String decs, String paramDate){
		TaotaoResult creatItem = its.creatItem(item, decs, paramDate);
		/*long data = (long) creatItem.getData();
		Map<String, String> map = new HashMap<>();
		map.put("id", data+"");
		HttpClientUtil.doPost(SEARCH_UPDATA_URL+UPDATE_URL, map);*/
		return creatItem;
	}
	
	@RequestMapping("/item/{itemId}")
	public String showItemParam(@PathVariable(value="itemId") long itemId, Model model){
		String itemParamHteml = its.getItemParamHteml(itemId);
		model.addAttribute("myhtml", itemParamHteml);
		return "itemParam";
	}
}
