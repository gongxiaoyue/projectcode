package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.service.RedisSyncService;

import co.taotao.common.pojo.TaotaoResult;

@Controller
public class SyncController {

	@Autowired
	private RedisSyncService redisSyncService;
	
	@RequestMapping("/sync/content/{cid}")
	@ResponseBody
	public TaotaoResult Sync(@PathVariable Long cid){
		TaotaoResult syncContent = redisSyncService.syncContent(cid);
		return syncContent;
	}
}
