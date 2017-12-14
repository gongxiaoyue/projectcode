package com.taotao.rest.service.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

import co.taotao.common.pojo.utils.JsonUtils;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper; 
	@Autowired
	private JedisClient client;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	@Override
	public List<TbContent> getContentList(long cid) {
		// TODO Auto-generated method stub
		try {
			String result = client.hget(REDIS_CONTENT_KEY, cid+"");
			if(!StringUtils.isBlank(result)){
				List<TbContent> jsonToList = JsonUtils.jsonToList(result, TbContent.class);
				return jsonToList;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		try {
			client.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

}
