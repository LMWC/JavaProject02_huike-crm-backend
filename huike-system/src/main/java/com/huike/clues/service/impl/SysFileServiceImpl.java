package com.huike.clues.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huike.clues.service.ISysFileService;
import com.huike.common.config.MinioConfig;
import com.huike.common.core.domain.AjaxResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysFileServiceImpl implements ISysFileService{

	@Autowired
	MinioConfig minioConfig;

	/**
	 * 文件上传至Minio
	 * 使用try catch finally进行上传
	 * finally里进行资源的回收
	 */
	@Override
	public AjaxResult upload(MultipartFile file) {
		InputStream inputStream = null;
		//创建Minio的连接对象
		MinioClient minioClient = getClient();
		String bucketName = minioConfig.getBucketName();
		try {
			inputStream = file.getInputStream();
			//基于官网的内容，判断文件存储的桶是否存在 如果桶不存在就创建桶
			//TODO 补全这部分代码
			/**
			 * ================================操作文件================================
			 * 思路：我们上传的文件是:合同.pdf
			 * 那么我们应该上传到配置的bucket内 我们配置的bucketName是huike-crm
			 * 那么我们存在桶里的文件应该是什么样的 也叫“合同.pdf”吗?
			 * 应该按照上传的年月日进行区分
			 * 举例：2021-05-05日进行上传的
			 * 那么存在桶里的路径应该是
			 * huike-crm/2021/05/05/这个目录下
			 * 而对于同一个文件，存在重名问题，所以我们应该利用UUID生成一个新的文件名，并拼接上 .pdf 作为文件后缀
			 * 那么完整的路径就是 huike-crm/2021/05/05/uuid.pdf
			 *
			 * 如果上述思路你无法理解，那么就直接存放在桶内生成uuid+.pdf即可
			 * 即：huike-crm/uuid.pdf
			 */
			//TODO 基于上述逻辑补全代码
			/**
			 * 构建返回结果集
			 */
			AjaxResult ajax = AjaxResult.success();
			/**
			 * 封装需要的数据进行返回
			 */
			return ajax;
		}catch(Exception e){
			e.printStackTrace();
			return AjaxResult.error("上传失败");
		}finally {
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


	/**
	 * 免费提供一个获取Minio连接的方法
	 * 获取Minio连接
	 * @return
	 */
	private MinioClient getClient(){
		MinioClient minioClient =
				MinioClient.builder()
						.endpoint("http://"+minioConfig.getEndpoint()+":"+ minioConfig.getPort())
						.credentials(minioConfig.getAccessKey(),minioConfig.getSecretKey())
						.build();
		return minioClient;
	}
}
