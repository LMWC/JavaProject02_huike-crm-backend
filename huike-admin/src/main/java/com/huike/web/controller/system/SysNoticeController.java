package com.huike.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.domain.SysNotice;
import com.huike.clues.service.ISysNoticeService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.SecurityUtils;

/**
 * 通知公告 信息操作处理
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController
{
    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    //@ApiOperation("获取系统提醒分页列表")
   // @PreAuthorize("@ss.hasPermi('system:notice:pagelist')")
    @GetMapping("/pagelist/{status}")
    public TableDataInfo pagelist(@PathVariable String status)
    {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(SecurityUtils.getUserId(),status);
        return getDataTable(list);
    }

    //@ApiOperation("获取系统提醒下拉列表")
   // @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list/{status}")
    public AjaxResult list(@PathVariable String status)
    {
        List<SysNotice> list = noticeService.selectNoticeList(SecurityUtils.getUserId(),status);
        return AjaxResult.success(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    //@ApiOperation("获取详情信息")
   // @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId)
    {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }


    /**
     * 修改通知公告
     */
    //@ApiOperation("未读变已读")
   // @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "修改已读", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{noticeId}")
    public AjaxResult edit(@PathVariable Long noticeId)
    {
        return toAjax(noticeService.updateNoticeRead(noticeId));
    }

}
