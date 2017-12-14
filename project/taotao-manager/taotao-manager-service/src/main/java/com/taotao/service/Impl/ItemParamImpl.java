package com.taotao.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParam;

import co.taotao.common.pojo.TaotaoResult;
@Service
public class ItemParamImpl implements ItemParam {
	@Autowired
	private TbItemParamMapper paramMapper;
	@Override
	public TaotaoResult getItemParam(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = paramMapper.selectByExample(example);
		if(list != null && list.size()>0){
			TbItemParam tp = list.get(0);
			return TaotaoResult.ok(tp);
		}
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult saveItemParam(long cid, String paramData) {
		TbItemParam param = new TbItemParam();
		param.setItemCatId(cid);
		param.setParamData(paramData);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		
		paramMapper.insert(param);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult editItemParam(long cid, String paramData) {
		TbItemParam param = new TbItemParam();
		param.setParamData(paramData);
		param.setUpdated(new Date());
		TbItemParamExample example = new TbItemParamExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemCatIdEqualTo(cid);
		paramMapper.updateByExample(param, example);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult deleteItemParam(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemCatIdEqualTo(cid);
		paramMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}

}
