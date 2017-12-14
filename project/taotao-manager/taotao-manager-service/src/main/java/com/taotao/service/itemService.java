package com.taotao.service;

import com.taotao.pojo.TbItem;

import co.taotao.common.pojo.EasyUIDateGridResult;
import co.taotao.common.pojo.TaotaoResult;

public interface itemService {
	public TbItem getTBitemById(Long itemid);
	EasyUIDateGridResult getItemList(int page,int rows);
	TaotaoResult creatItem(TbItem item, String decs, String paramDate);
	String getItemParamHteml(long itemId);
}
