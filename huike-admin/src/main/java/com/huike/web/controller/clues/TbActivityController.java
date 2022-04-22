package com.huike.web.controller.clues;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.service.ITbActivityService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;

/**
 * 活动管理Controller
 * 
 * @author wgl
 * @date 2021-04-01
 */
@RestController
@RequestMapping("/clues/activity")
public class TbActivityController extends BaseController {

    @Autowired
    private ITbActivityService tbActivityService;

    /**
     * 查询活动管理列表
     */
    @PreAuthorize("@ss.hasPermi('clues:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbActivity tbActivity)
    {
        startPage();
        List<TbActivity> list = tbActivityService.selectTbActivityList(tbActivity);
        Map<String,Object> countParms=tbActivityService.getCountByStatus();
        return getDataTable(list,countParms);
    }


    /**
     * 获取渠道下活动
     * @param channel
     * @return
     */
    @GetMapping("/listselect/{channel}")
    public AjaxResult list(@PathVariable("channel")  String channel)
    {
        TbActivity tbActivity =new TbActivity();
        tbActivity.setChannel(channel);
        tbActivity.setStatus("2");
        return AjaxResult.success(tbActivityService.selectTbActivityList(tbActivity));
    }

    /**
     * 获取活动管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('clues:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbActivityService.selectTbActivityById(id));
    }

    /**
     * 新增活动管理
     */
    @PreAuthorize("@ss.hasPermi('clues:activity:add')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbActivity tbActivity)
    {
        return toAjax(tbActivityService.insertTbActivity(tbActivity));
    }


    /**
     * 修改活动管理
     */
    @PreAuthorize("@ss.hasPermi('clues:activity:edit')")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbActivity tbActivity)
    {
        return toAjax(tbActivityService.updateTbActivity(tbActivity));
    }

    /**
     * 删除活动管理
     */
    @PreAuthorize("@ss.hasPermi('clues:activity:remove')")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbActivityService.deleteTbActivityByIds(ids));
    }
}
