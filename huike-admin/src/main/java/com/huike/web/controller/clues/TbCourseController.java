package com.huike.web.controller.clues;

import com.huike.clues.domain.TbCourse;
import com.huike.clues.dto.TbCourseDto;
import com.huike.clues.service.ITbCourseService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 课程管理Controller
 * 
 * @date 2021-04-01
 */
@RestController
@RequestMapping("/clues/course")
public class TbCourseController extends BaseController {
<<<<<<< HEAD
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
=======
	@Autowired
	private ITbCourseService tbCourseService;
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	/**
	 * 查询课程管理列表
	 */
	@ApiOperation("查询课程管理列表")
	@PreAuthorize("@ss.hasPermi('clues:course:list')")
	@GetMapping("/list")
	public TableDataInfo list(TbCourseDto dto) {
		return tbCourseService.list(dto);
	}

<<<<<<< HEAD
    //@ApiOperation("课程下拉列表")
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
=======
	@ApiOperation("课程下拉列表")
	@GetMapping("/listselect")
	public AjaxResult list(String subject) {
		TbCourseDto dto = new TbCourseDto();
		if (subject != null) {
			dto.setSubject(subject);
		}
		return AjaxResult.success(tbCourseService.list(dto));
	}

	/**
	 * 获取课程管理详细信息
	 */
	@ApiOperation("获取课程管理详细信息")
	@PreAuthorize("@ss.hasPermi('clues:course:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(tbCourseService.getById(id));
	}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	/**
	 * 新增课程管理
	 */
	@PreAuthorize("@ss.hasPermi('clues:course:add')")
	@Log(title = "课程管理", businessType = BusinessType.INSERT)
	@ApiOperation("课程管理")
	@PostMapping
	public AjaxResult add(@RequestBody TbCourse tbCourse) {
		return toAjax(tbCourseService.add(tbCourse));
	}

	/**
	 * 修改课程管理
	 */
	@ApiOperation("修改课程管理")
	@PreAuthorize("@ss.hasPermi('clues:course:edit')")
	@Log(title = "课程管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody TbCourse tbCourse) {
		return toAjax(tbCourseService.updateById(tbCourse));
	}

	/**
	 * 删除课程管理
	 */
	@ApiOperation("删除课程管理")
	@PreAuthorize("@ss.hasPermi('clues:course:remove')")
	@Log(title = "课程管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(tbCourseService.removeByIds(Arrays.asList(ids)));
	}
}
