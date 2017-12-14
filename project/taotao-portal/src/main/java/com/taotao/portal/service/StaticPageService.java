package com.taotao.portal.service;

import co.taotao.common.pojo.TaotaoResult;

public interface StaticPageService {

	TaotaoResult getHtml(Long itemId) throws Exception;
}
