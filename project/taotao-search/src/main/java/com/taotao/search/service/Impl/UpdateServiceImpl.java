package com.taotao.search.service.Impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.mapper.UpdateMapper;
import com.taotao.search.service.UpdateSearchService;

import co.taotao.common.pojo.SearchResult;
import co.taotao.common.pojo.SolrItem;
import co.taotao.common.pojo.TaotaoResult;

public class UpdateServiceImpl implements UpdateSearchService {

	@Autowired
	private SolrServer server;
	@Autowired
	private UpdateMapper mapper;
	@Override
	public TaotaoResult UpdateSearch(Long id) throws Exception {
		List<SolrItem> update = mapper.Update(id);
		for (SolrItem searchResult : update) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchResult.getId());
			document.addField("item_title", searchResult.getTitle());
			document.addField("item_sell_point", searchResult.getSell_point());
			document.addField("item_price", searchResult.getPrice());
			document.addField("item_image", searchResult.getImage());
			document.addField("item_category_name", searchResult.getItem_cat_name());
			document.addField("item_desc", searchResult.getItem_desc());
			//写入索引库
			server.add(document);
		}
		return TaotaoResult.ok();
	}

}
