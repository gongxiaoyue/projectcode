package com.taotao.search.mapper;

import java.util.List;

import co.taotao.common.pojo.SearchResult;
import co.taotao.common.pojo.SolrItem;

public interface UpdateMapper {

	List<SolrItem> Update(Long id);
}
