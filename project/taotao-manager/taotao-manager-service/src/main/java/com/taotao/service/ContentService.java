package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TbContent;

import co.taotao.common.pojo.TaotaoResult;

public interface ContentService {

	List<TbContent> getContentList(long id);
	TaotaoResult addContent(TbContent content);
	TaotaoResult updateContent(TbContent content);
	TaotaoResult deleteContent(long id);
}
