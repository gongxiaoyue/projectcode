package com.taotao.rest.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.pojo.ItemCatTree;
import com.taotao.rest.service.ItemCatService;

import co.taotao.common.pojo.utils.JsonUtils;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper catMapper;
	@Autowired
	private JedisClient client;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	private String SHOWLIST;
	@Override
	public ItemCatResult getItemCatList() {
		try {
			String result = client.hget(REDIS_CONTENT_KEY, SHOWLIST);
			ItemCatResult catItem = JsonUtils.jsonToPojo(result, ItemCatResult.class);
			return catItem;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		List itemCatList = getItemCatList(0l);
		ItemCatResult catResult = new ItemCatResult();
		catResult.setDate(itemCatList);
		try {
			client.hset(REDIS_CONTENT_KEY, SHOWLIST, JsonUtils.objectToJson(catResult));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return catResult;
	}

	private List getItemCatList(long parentId){
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = catMapper.selectByExample(example);
		List result = new ArrayList<>();
		for (TbItemCat cat : list) {
			if(cat.getIsParent()){
				ItemCatTree catTree = new ItemCatTree();
				catTree.setUrl("/products/"+cat.getId()+".html");
				if(cat.getParentId()==0){
					catTree.setName("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
				}else{
					catTree.setName(cat.getName());
				}
				catTree.setItems(getItemCatList(cat.getId()));
				result.add(catTree);
			}else{
				String item = "/products/"+cat.getId()+".html|" + cat.getName();
				result.add(item);
			}
		}
		return result;
	}
}
