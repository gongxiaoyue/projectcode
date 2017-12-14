package com.taotao.portal.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(long itemId);
	String getItemDescById(long itemId);
	String getItemParamById(long itemId);
}
