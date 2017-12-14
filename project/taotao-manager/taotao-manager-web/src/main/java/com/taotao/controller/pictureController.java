package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.pictureService;

import co.taotao.common.pojo.PictureResult;
import co.taotao.common.pojo.utils.JsonUtils;

public class pictureController {
	@Autowired
	private pictureService pictureService;
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String upLoad(MultipartFile uploadFile) throws Exception{
		PictureResult uploadPic = pictureService.uploadPic(uploadFile);
		String result = JsonUtils.objectToJson(uploadPic);
		return result;
	}
}
