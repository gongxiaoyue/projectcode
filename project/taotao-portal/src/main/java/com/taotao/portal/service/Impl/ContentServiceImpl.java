package com.taotao.portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;

import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.HttpClientUtil;
import co.taotao.common.pojo.utils.JsonUtils;
@Service
public class ContentServiceImpl implements ContentService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;
	@Override
	public String getAd1List() {
		// TODO Auto-generated method stub
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
		TaotaoResult result1 = TaotaoResult.formatToPojo(json, TbContent.class);
		List<TbContent> contentList = (List<TbContent>) result1.getData();
		List<AdNode> resultList = new ArrayList<>();
		for (TbContent content : contentList) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(content.getPic());
			
			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(content.getPic2());
			
			node.setAlt(content.getSubTitle());
			node.setHref(content.getUrl());
			resultList.add(node);
		}
		String result = JsonUtils.objectToJson(resultList);
		return result;
	}

}
