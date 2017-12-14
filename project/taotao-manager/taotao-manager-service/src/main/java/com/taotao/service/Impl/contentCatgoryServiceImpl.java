package com.taotao.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.contentCatgoryService;

import co.taotao.common.pojo.EasyUITreeNode;
import co.taotao.common.pojo.TaotaoResult;
@Service
public class contentCatgoryServiceImpl implements contentCatgoryService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCatCategory(long parentId) {
		TbContentCategoryExample categoryExample = new TbContentCategoryExample();
		Criteria createCriteria = categoryExample.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(categoryExample);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory category : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult insertCategory(long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setParentId(parentId);
		category.setIsParent(false);
		category.setName(name);
		category.setSortOrder(1);
		category.setStatus(1);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		categoryMapper.insert(category);
		Long id = category.getId();
		TbContentCategory parentNode = categoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			categoryMapper.updateByPrimaryKey(parentNode);
		}
		return TaotaoResult.ok(id);
	}
	@Override
	public TaotaoResult deleteCategory(long parentId, long id) {
		TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
		//判断该节点是否为父节点，是就进行遍历删除
		if(category.getIsParent()){
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andParentIdEqualTo(id);
			List<TbContentCategory> list = categoryMapper.selectByExample(example);
			for (TbContentCategory tbc : list) {
				deleteCategory(id, tbc.getId());
			}
		}else{
			category.setStatus(2);
			category.setUpdated(new Date());
			categoryMapper.updateByPrimaryKey(category);
			TbContentCategory parentNode = categoryMapper.selectByPrimaryKey(parentId);
			parentNode.setIsParent(false);
			parentNode.setUpdated(new Date());
			categoryMapper.updateByPrimaryKey(parentNode);
		}
		return TaotaoResult.ok();
	}
	@Override
	public EasyUITreeNode renameCategory(long id, String name) {
		TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		category.setUpdated(new Date());
		categoryMapper.updateByPrimaryKey(category);
		EasyUITreeNode node = new EasyUITreeNode();
		node.setId(id);
		node.setText(name);
		node.setState(category.getIsParent()?"closed":"open");
		return node;
	}

}
