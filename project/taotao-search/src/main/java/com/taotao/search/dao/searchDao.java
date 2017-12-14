package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import co.taotao.common.pojo.SearchResult;

public interface searchDao {

	SearchResult Search(SolrQuery query) throws Exception;
}
