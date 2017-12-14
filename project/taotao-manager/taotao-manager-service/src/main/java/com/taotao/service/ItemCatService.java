package com.taotao.service;

import java.util.List;

import co.taotao.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatList(long parentid);
}
