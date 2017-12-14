package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.service.SearchService;

import co.taotao.common.pojo.SearchResult;

@Controller
public class SearchController {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Autowired
	private SearchService service;
	@RequestMapping("/search")
	public String search(@RequestParam("p")String keyword, 
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="30")Integer rows, Model model){
		try {
			keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
		} catch (Exception e) {
			keyword = "";
			e.printStackTrace();
		}
		SearchResult result = service.search(keyword, page, rows);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPage", result.getPageCount());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("curPage", result.getPage());
		return "search";
	}

}
