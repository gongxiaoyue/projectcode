package com.taotao.search.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.dao.ItemDao;

import co.taotao.common.pojo.SearchResult;
import co.taotao.common.pojo.SolrItem;

public class ItemDaoImpl implements ItemDao{

	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult searchItem(SolrQuery solrQuery) throws Exception {
		
		//根据查询条件查询索引库
		QueryResponse response = solrServer.query(solrQuery);
		//从response中取查询结果
		SolrDocumentList list = response.getResults();
		//查询结果list
		List<SolrItem> itemList = new ArrayList<>();
		//遍历查询结果
		for (SolrDocument solrDocument : list) {
			//创建一商品对象
			SolrItem item = new SolrItem();
			item.setId(Long.parseLong((String)solrDocument.get("id")));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> hList = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			//判断是否有高亮信息
			if (hList != null && hList.size() > 0) {
				title = hList.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setImage((String) solrDocument.get("item_image"));
			//添加到商品列表
			itemList.add(item);
		}
		//创建SearchResult
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		//查询返回结果的总数量
		result.setTotal(list.getNumFound());
		//返回结果
		return result;
	}
	
}
