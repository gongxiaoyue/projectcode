package com.taotao.portal.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.portal.service.SearchService;

import co.taotao.common.pojo.SearchResult;
import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.HttpClientUtil;
import co.taotao.common.pojo.utils.JsonUtils;
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Override
	public SearchResult search(String keyword, int page, int rows) {
		Map<String, String> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("page", "");
		map.put("rows", "");
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL, map);
		TaotaoResult formatToPojo = TaotaoResult.formatToPojo(json, SearchResult.class);
		SearchResult data = (SearchResult) formatToPojo.getData();
		return data;
	}

}
