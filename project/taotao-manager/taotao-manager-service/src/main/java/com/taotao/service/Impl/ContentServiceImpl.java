package com.taotao.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

import co.taotao.common.pojo.TaotaoResult;
@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper contentMapper;
	@Override
	public List<TbContent> getContentList(long id) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> result = contentMapper.selectByExample(example);
		return result;
	}
	@Override
	public TaotaoResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult updateContent(TbContent content) {
		contentMapper.updateByPrimaryKey(content);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult deleteContent(long id) {
		// TODO Auto-generated method stub
		contentMapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

}
