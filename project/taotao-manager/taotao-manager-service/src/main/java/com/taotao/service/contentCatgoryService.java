package com.taotao.service;

import java.util.List;

import co.taotao.common.pojo.EasyUITreeNode;
import co.taotao.common.pojo.TaotaoResult;

public interface contentCatgoryService {
	List<EasyUITreeNode> getContentCatCategory(long parentId);
	TaotaoResult insertCategory(long parentId, String name);
	TaotaoResult deleteCategory(long parentId, long id);
	EasyUITreeNode renameCategory(long id, String name);
}
