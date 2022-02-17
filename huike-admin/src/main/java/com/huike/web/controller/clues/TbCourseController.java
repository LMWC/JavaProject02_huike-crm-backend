package com.huike.web.controller.clues;

import java.util.List;

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

import com.huike.clues.domain.TbCourse;
import com.huike.clues.service.ITbCourseService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;

/**
 * 课程管理Controller
 * @date 2021-04-01
 */
@RestController
@RequestMapping("/clues/course")
public class TbCourseController extends BaseController {
    @Autowired
    private ITbCourseService tbCourseService;

    /**
     * 查询课程管理列表
     */
    @PreAuthorize("@ss.hasPermi('clues:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbCourse tbCourse) {
        startPage();
        List<TbCourse> list = tbCourseService.selectTbCourseList(tbCourse);
        return getDataTable(list);
    }


    /**
     * @ApiOperation("课程下拉列表")
     * @param subject
     * @return
     */
    @GetMapping("/listselect")
    public AjaxResult list(String subject) {
        TbCourse query =new TbCourse();
        if(subject!=null){
            query.setSubject(subject);
        }
        return AjaxResult.success(tbCourseService.selectTbCourseList(query));
    }

    /**
     * 获取课程管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('clues:course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tbCourseService.selectTbCourseById(id));
    }

    /**
     * 新增课程管理
     */
    @PreAuthorize("@ss.hasPermi('clues:course:add')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbCourse tbCourse)
    {
        return toAjax(tbCourseService.insertTbCourse(tbCourse));
    }

    /**
     * 修改课程管理
     */
    @PreAuthorize("@ss.hasPermi('clues:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbCourse tbCourse)
    {
        return toAjax(tbCourseService.updateTbCourse(tbCourse));
    }

    /**
     * 删除课程管理
     */
    @PreAuthorize("@ss.hasPermi('clues:course:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbCourseService.deleteTbCourseByIds(ids));
    }
}
