package com.taotao.portal.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.service.ItemService;

import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.HttpClientUtil;
import co.taotao.common.pojo.utils.JsonUtils;

public class ItemServiceImpl implements ItemService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;
	@Value("${REST_ITEMDESC_BASE_URL}")
	private String REST_ITEMDESC_BASE_URL;
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;
	@Override
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_BASE_URL+itemId);
		TaotaoResult formatToPojo = TaotaoResult.formatToPojo(result, TbItem.class);
		TbItem data = (TbItem) formatToPojo.getData();
		return data;
	}
	@Override
	public String getItemDescById(long itemId) {
		// TODO Auto-generated method stub
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEMDESC_BASE_URL+itemId);
		TaotaoResult result = TaotaoResult.formatToList(json, TbItemDesc.class);
		TbItemDesc data = (TbItemDesc) result.getData();
		String itemDesc = data.getItemDesc();
		return itemDesc;
	}
	@Override
	public String getItemParamById(long itemId) {
		// TODO Auto-generated method stub
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
		TbItemParamItem data = (TbItemParamItem) result.getData();
		String paramData = data.getParamData();
		List<Map> jsonToPojo = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : jsonToPojo) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}

}
