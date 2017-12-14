package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.HttpClientUtil;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@RequestMapping("/showlist")
	@ResponseBody
	public List<TbContent> getContentList(@RequestParam("parentId") Long id){
		List<TbContent> contentList = contentService.getContentList(id);
		return contentList;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content){
		TaotaoResult result = contentService.addContent(content);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getId());
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content){
		TaotaoResult result = contentService.updateContent(content);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getId());
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContent(@RequestParam("cid")Long id){
		TaotaoResult result = contentService.deleteContent(id);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL +id);
		return result;
	}
}
