package com.taotao.portal.controller;

import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.service.StaticPageService;

import co.taotao.common.pojo.TaotaoResult;

public class StaticController {

	@Autowired
	private StaticPageService pageService;
	
	@RequestMapping("/gen/item/{itemId}")
	@ResponseBody
	public TaotaoResult getStaticPage(@PathVariable Long itemId){
		try {
			TaotaoResult myhtml = pageService.getHtml(itemId);
			return myhtml;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
