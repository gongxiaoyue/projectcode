package com.taotao.portal.service.Impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;

import co.taotao.common.pojo.TaotaoResult;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class StaticPageServiceImpl implements StaticPageService {

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH;
	@Override
	public TaotaoResult getHtml(Long itemId) throws Exception {
		TbItem itemById = itemService.getItemById(itemId);
		String itemDescById = itemService.getItemDescById(itemId);
		String itemParamById = itemService.getItemParamById(itemId);
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		//创建一个数据集
		Map root = new HashMap<>();
		//向数据集中添加属性
		root.put("item", itemById);
		root.put("itemDesc", itemDescById);
		root.put("itemParam", itemParamById);
		//创建一个Writer对象
		Writer out = new FileWriter(new File(STATIC_PAGE_PATH + itemId + ".html"));
		//生成静态文件
		template.process(root, out);
		out.flush();
		out.close();
		
		return TaotaoResult.ok();
	}

}
