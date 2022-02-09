package com.huike.clues.service;

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
	 * @param fileName
	 * @return
	 */

	AjaxResult downloadByMinio(String fileName);

}
