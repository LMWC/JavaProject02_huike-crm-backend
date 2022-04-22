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

/**
 * 通用请求处理
 */
@RestController
public class CommonController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ISysFileService fileService;


    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file){
        try{
            // 上传文件路径
            return fileService.upload(file);
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }
}