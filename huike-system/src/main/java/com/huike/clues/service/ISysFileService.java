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

}
