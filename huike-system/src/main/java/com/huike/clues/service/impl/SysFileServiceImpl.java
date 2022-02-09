package com.huike.clues.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huike.clues.service.ISysFileService;
import com.huike.common.config.MinioConfig;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.utils.file.MinioUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysFileServiceImpl implements ISysFileService{
	
	@Autowired
	MinioConfig minioConfig;
	
	/**
	 * 文件上传
	 */
	@Override
	public AjaxResult upload(MultipartFile file) {
		MinioUtil miniUtil = new MinioUtil();
		InputStream inputStream = null;
		String bucketName = minioConfig.getBucketName();
		try {
			inputStream = file.getInputStream();
			//如果桶不存在--则删除桶
            if (!miniUtil.bucketExists(bucketName)) {
            	miniUtil.makeBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();
            String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));

            miniUtil.putObject(bucketName, objectName, inputStream);
            String url = miniUtil.getObjectUrl(bucketName, objectName);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            //url需要进行截取
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        } finally {
        	//防止内存泄漏
        	if (inputStream != null) {
                try {
                    inputStream.close(); // 关闭流
                } catch (IOException e) {
                    log.debug("inputStream close IOException:" + e.getMessage());
                }
            }
        }
	}
	
	
	@Override
	public AjaxResult downloadByMinio(String fileName) {
		try {
			String bucketName = minioConfig.getBucketName();
			MinioUtil miniUtil = new MinioUtil();
			String url = miniUtil.getObjectUrl(bucketName, fileName);
			AjaxResult ajax = AjaxResult.success();
		    ajax.put("fileName", fileName);
		    ajax.put("url", url);
		    return ajax;
		}catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}
}
