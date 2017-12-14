package com.taotao.service;

import co.taotao.common.pojo.TaotaoResult;

public interface ItemParam {
	TaotaoResult getItemParam(long cid);
	TaotaoResult saveItemParam(long cid, String paramData);
	TaotaoResult editItemParam(long cid, String paramData);
	TaotaoResult deleteItemParam(long cid);
}
