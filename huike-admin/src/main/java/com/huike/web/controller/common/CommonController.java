package com.huike.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huike.clues.service.ISysFileService;
import com.huike.common.config.HuiKeConfig;
import com.huike.common.constant.Constants;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.utils.StringUtils;
import com.huike.common.utils.file.FileUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 通用请求处理
 * 
 * 
 */
@Api("通用请求处理")
@RestController
public class CommonController extends BaseController {
<<<<<<< HEAD
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
=======
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private ISysFileService fileService;
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	/**
	 * 通用下载请求
	 * 
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
	@ApiOperation("通用下载请求")
	@GetMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			if (!FileUtils.checkAllowDownload(fileName)) {
				throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
			}
			String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
			String filePath = HuiKeConfig.getDownloadPath() + fileName;
			log.info("下载路径 {}", filePath);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, realFileName);
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * 通用下载请求
	 * 
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
	@GetMapping("common/downloadByMinio")
	@PreAuthorize("@ss.hasPermi('common:downLoad:downLoadMinio')")
	public AjaxResult fileDownload(String fileName, HttpServletResponse response, HttpServletRequest request) {
		try {
			if (!FileUtils.checkAllowDownload(fileName)) {
				throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
			}
			return fileService.downloadByMinio(fileName);
		} catch (Exception e) {
			log.error("下载文件失败", e);
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 通用上传请求
	 */
	@ApiOperation("通用上传请求")
	@PostMapping("/common/upload")
	public AjaxResult uploadFile(MultipartFile file) throws Exception {
		try {
			// 上传文件路径
			return fileService.upload(file);
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 本地资源通用下载
	 */
	@ApiOperation("资源通用下载")
	@GetMapping("/common/download/resource")
	public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (!FileUtils.checkAllowDownload(resource)) {
				throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
			}
			// 本地资源路径
			String localPath = HuiKeConfig.getProfile();
			// 数据库资源地址
			String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
			System.out.println("downloadPath---" + downloadPath);
			// 下载名称
			String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, downloadName);
			FileUtils.writeBytes(downloadPath, response.getOutputStream());
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}
}
