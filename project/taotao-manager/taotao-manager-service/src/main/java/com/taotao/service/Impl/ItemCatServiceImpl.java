package com.taotao.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

import co.taotao.common.pojo.EasyUITreeNode;
@Service("itemCatService")
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentid) {
		TbItemCatExample ce = new TbItemCatExample();
		Criteria criteria = ce.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(ce);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for(TbItemCat tc:list){
			EasyUITreeNode e = new EasyUITreeNode();
			e.setId(tc.getId());
			e.setText(tc.getName());
			e.setState(tc.getIsParent()?"closed":"open");
			resultList.add(e);
		}
		return resultList;
	}

}
