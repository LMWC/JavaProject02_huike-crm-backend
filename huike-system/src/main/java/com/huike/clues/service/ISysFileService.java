package com.huike.clues.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.huike.common.core.domain.AjaxResult;

public interface ISysFileService {

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	AjaxResult upload(MultipartFile file);

	/**
	 * 文件下载
	 * 将minio中文件的内容写入到response中
	 * 通过InputStream读取数据
	 * 通过OutputStream写入到response
	 * @param fileName
	 * @param response
	 * @return
	 */
	AjaxResult downloadByMinio(String fileName,HttpServletResponse response);

}
