package com.taotao.search.service;

import co.taotao.common.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String query, int page, int rows) throws Exception;
}
