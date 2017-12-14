package com.taotao.search.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.dao.searchDao;

import co.taotao.common.pojo.SearchResult;
import co.taotao.common.pojo.SolrItem;

public class SearchDaoImpl implements searchDao{

	@Autowired
	private SolrServer server;
	@Override
	public SearchResult Search(SolrQuery query) throws Exception {
		// TODO Auto-generated method stub
		QueryResponse response = server.query(query);
		SolrDocumentList results = response.getResults();
		List<SolrItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			//创建一个SearchItem对象
			SolrItem item = new SolrItem();
			item.setItem_cat_name((String) solrDocument.get("item_category_name"));
			item.setId((long) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((Long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if (list != null && list.size() > 0) {
				//取高亮后的结果
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			item.setTitle(itemTitle);
			//添加到列表
			itemList.add(item);
		}
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		//查询结果总数量
		result.setPageCount(results.getNumFound());
		return result;
	}

}
