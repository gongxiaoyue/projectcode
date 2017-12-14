package com.taotao.portal.service;

import co.taotao.common.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String keyword, int page, int rows);
}
