package com.taotao.search.service.Impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.searchDao;
import com.taotao.search.service.SearchService;

import co.taotao.common.pojo.SearchResult;
@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private searchDao dao;
	@Override
	public SearchResult search(String query, int page, int rows) throws Exception {
		// TODO Auto-generated method stub
		SolrQuery squery = new SolrQuery();
		squery.setQuery(query);
		squery.setStart((page-1)*rows);
		squery.setRows(rows);
		//设置默认搜索域
		squery.set("df", "item_title");
		//设置高亮
		squery.setHighlight(true);
		squery.addHighlightField("item_title");
		squery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		squery.setHighlightSimplePost("</font>");
		SearchResult search = dao.Search(squery);
		long pageCount = search.getPageCount();
		int page1 = (int) (pageCount/rows);
		if(pageCount%rows>0){
			page1++;
		}
		search.setPageCount(page1);
		search.setPage(page);
		return search;
	}

}
