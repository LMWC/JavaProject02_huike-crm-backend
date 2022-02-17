package com.huike.web.controller.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.domain.TbRulePool;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.enums.BusinessType;

/**
 * 线索池规则Controller
 * @date 2021-04-16
 */
@RestController
@RequestMapping("/rule/pool")
public class TbRulePoolController extends BaseController
{
    @Autowired
    private ITbRulePoolService tbRulePoolService;

    /**
     * 查询线索池规则列表
     */
   // @PreAuthorize("@ss.hasPermi('clues:pool:list')")
    //@ApiOperation("获取基础信息type=0 线索 type=1 商机")
    @GetMapping("/{type}")
    public AjaxResult getInfo(@PathVariable("type") String type)
    {

        return AjaxResult.success(tbRulePoolService.selectTbRulePoolByType(type));
    }


    /**
     * 新增线索池规则
     */
    //@ApiOperation("新增线索池规则")
    //@PreAuthorize("@ss.hasPermi('clues:pool:add')")
    @Log(title = "线索池规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbRulePool tbRulePool)
    {
        return toAjax(tbRulePoolService.insertTbRulePool(tbRulePool));
    }

    /**
     * 修改线索池规则
     */
    //@ApiOperation("修改线索池规则")
    //@PreAuthorize("@ss.hasPermi('clues:pool:edit')")
    @Log(title = "线索池规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbRulePool tbRulePool)
    {
        return toAjax(tbRulePoolService.updateTbRulePool(tbRulePool));
    }


}
