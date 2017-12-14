package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import co.taotao.common.pojo.PictureResult;

public interface pictureService {
	PictureResult uploadPic(MultipartFile uploadFile) throws Exception;
}
