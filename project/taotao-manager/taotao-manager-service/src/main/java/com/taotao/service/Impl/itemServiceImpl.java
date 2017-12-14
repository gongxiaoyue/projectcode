package com.taotao.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dao.TbItemDescMapper;
import com.taotao.dao.TbItemMapper;
import com.taotao.dao.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.itemService;

import co.taotao.common.pojo.EasyUIDateGridResult;
import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.IDUtils;
import co.taotao.common.pojo.utils.JsonUtils;

@Service
public class itemServiceImpl implements itemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDesc;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getTBitemById(Long itemid) {
		TbItem tbitem = itemMapper.selectByPrimaryKey(itemid);
		return tbitem;
	}

	@Override
	public EasyUIDateGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult creatItem(TbItem item, String decs, String paramDate) {
		long itemid = IDUtils.genItemId();
		item.setId(itemid);
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		TbItemDesc desc = new TbItemDesc();
		desc.setItemId(itemid);
		desc.setItemDesc(decs);
		desc.setCreated(date);
		desc.setUpdated(date);
		itemDesc.insert(desc);
		TbItemParamItem p = new TbItemParamItem();
		p.setItemId(itemid);
		p.setParamData(paramDate);
		p.setCreated(date);
		p.setUpdated(date);
		itemParamItemMapper.insert(p);
		return TaotaoResult.ok(itemid);
	}

	@Override
	public String getItemParamHteml(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExample(example);
		if(list == null && list.isEmpty()){
			return "";
		}
		TbItemParamItem itemParamItem = list.get(0);
		itemParamItem.setItemId(itemId);
		//取json数据
		String paramData = itemParamItem.getParamData();
		//转换成java对象
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
		//遍历list生成html
		StringBuffer sb = new StringBuffer();
		
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map: mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
			sb.append("		</tr>\n");
			//取规格项
			List<Map>mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
				sb.append("			<td>"+map2.get("v")+"</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");
		
		return sb.toString();
	}

}
