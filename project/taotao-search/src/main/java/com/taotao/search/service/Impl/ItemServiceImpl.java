package com.taotao.search.service.Impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.ItemService;

import co.taotao.common.pojo.SolrItem;
import co.taotao.common.pojo.TaotaoResult;

public class ItemServiceImpl implements ItemService {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public TaotaoResult importItems() throws Exception {
		//查询数据库获得商品列表
		List<SolrItem> itemList = itemMapper.getItemList();
		//遍历列表
		for (SolrItem item : itemList) {
			//创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			//添加域
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_cat_name", item.getItem_cat_name());
			document.addField("item_desc", item.getItem_desc());
			//写入索引库
			solrServer.add(document);
		}
		//提交
		solrServer.commit();
		return TaotaoResult.ok();
	}

}
