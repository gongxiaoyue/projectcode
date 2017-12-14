package com.taotao.service.Impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.pictureService;

import co.taotao.common.pojo.PictureResult;

@Service
public class pictureServiceImpl implements pictureService {
	@Value("${path}")
	private String path;

	@Override
	public PictureResult uploadPic(MultipartFile uploadFile) throws IOException {
		PictureResult result = new PictureResult();
		if (uploadFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		String originalFilename = uploadFile.getOriginalFilename();
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		//FastDFSClient client = new FastDFSClient(path);

		//String url = client.uploadFile(uploadFile.getBytes(), extName);
		result.setError(0);
		//result.setUrl(url);
		return result;

	}

}
