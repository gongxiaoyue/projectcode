package com.taotao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.contentCatgoryService;

import co.taotao.common.pojo.EasyUITreeNode;
import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.HttpClientUtil;

@Controller
@RequestMapping("/content/category")
public class ContentCatCategoryController {

	@Autowired
	private contentCatgoryService catgoryService;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatCategory(@RequestParam(value="id", defaultValue="0") Long parentId){
		List<EasyUITreeNode> resultList = catgoryService.getContentCatCategory(parentId);
		return resultList;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(Long parentId, String name){
		TaotaoResult result = catgoryService.insertCategory(parentId, name);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + result.getData());
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteCategory(@RequestParam("parentId") Long parentId,@RequestParam("id") Long id){
		TaotaoResult result = catgoryService.deleteCategory(parentId, id);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + id);
		return result;
	}
	@RequestMapping("/rename")
	@ResponseBody
	public EasyUITreeNode renameCategory(Long id, String name){
		EasyUITreeNode result = catgoryService.renameCategory(id, name);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + id);
		return result;
	}
}
