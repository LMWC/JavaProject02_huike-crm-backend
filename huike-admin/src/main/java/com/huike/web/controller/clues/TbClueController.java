package com.huike.web.controller.clues;

import java.util.List;

<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;

import com.huike.common.annotation.DataScope;
=======
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huike.business.service.ITbBusinessService;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.vo.AssignmentVo;
import com.huike.clues.domain.vo.FalseClueVo;
import com.huike.clues.domain.vo.TbClueExcelVo;
import com.huike.clues.dto.TbClueDto;
import com.huike.clues.service.ITbActivityService;
import com.huike.clues.service.ITbClueService;
import com.huike.clues.utils.easyExcel.ExcelListener;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;

import io.swagger.annotations.ApiOperation;

/**
 * 线索管理Controller
 * 
 * @date 2021-04-02
 */
@RestController
@RequestMapping("/clues/clue")
public class TbClueController extends BaseController {
	
	@Autowired
	private ITbClueService tbClueService;

	@Autowired
	private ITbBusinessService tbBusinessService;

	@Autowired
	private ITbActivityService activityService;

	/**
	 * 查询线索管理列表
	 */
	@ApiOperation("查询线索管理列表")
	@PreAuthorize("@ss.hasPermi('clues:clue:list')")
	@GetMapping("/list")
	public TableDataInfo list(TbClueDto tbClue) {
		Page<TbClue> page = tbClueService.selectTbClueList(tbClue);
		return getDataTable(page);
	}

	@ApiOperation("查询线索池")
	@PreAuthorize("@ss.hasPermi('clues:clue:pool')")
	@GetMapping("/pool")
	public TableDataInfo pool(TbClueDto tbClue) {
		Page<TbClue> page = tbClueService.selectTbCluePool(tbClue);
		return getDataTable(page);
	}

	/**
	 * 获取线索管理详细信息
	 */
	@ApiOperation("获取线索基础信息")
	@PreAuthorize("@ss.hasPermi('clues:clue:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(tbClueService.selectTbClueById(id));
	}

	/**
	 * 新增线索管理
	 */
	@ApiOperation("新增线索管理")
	@PreAuthorize("@ss.hasPermi('clues:clue:add')")
	@Log(title = "线索管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody TbClueDto tbClue) {
		if(!tbClueService.checkCluePhoneExis(tbClue.getPhone())) return error("手机号已存在");
		return toAjax(tbClueService.insertTbClue(tbClue));
	}

	/**
	 * 修改线索管理
	 */
	@ApiOperation("修改线索管理")
	@PreAuthorize("@ss.hasPermi('clues:clue:edit')")
	@Log(title = "线索管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody TbClueDto clueDto) {
		return toAjax(tbClueService.updateTbClue(clueDto));
	}

	@ApiOperation("线索转商机")
	@PreAuthorize("@ss.hasPermi('clues:clue:remove')")
	@Log(title = "线索转商机", businessType = BusinessType.UPDATE)
	@PutMapping("/changeBusiness/{id}")
	public AjaxResult changeBusiness(@PathVariable Long id) {
		return toAjax(tbBusinessService.changeBusiness(id));
	}

<<<<<<< HEAD
	// @ApiOperation("伪线索")
	@PreAuthorize("@ss.hasPermi('clues:clue:false')")
=======
	@ApiOperation("伪线索")
	// @PreAuthorize("@ss.hasPermi('clues:clue:false')")
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
	@Log(title = "伪线索", businessType = BusinessType.UPDATE)
	@PutMapping("/false/{id}")
	public AjaxResult cluesFalse(@PathVariable Long id, @RequestBody FalseClueVo falseClueVo) {
		return toAjax(tbClueService.falseClue(id, falseClueVo.getReason(), falseClueVo.getRemark()));
	}

	@ApiOperation("批量分配")
	@PreAuthorize("@ss.hasPermi('clues:clue:assignment')")
	@Log(title = "批量分配", businessType = BusinessType.UPDATE)
	@PutMapping("/assignment")
	public AjaxResult assignment(@RequestBody AssignmentVo assignmentVo) {
		return AjaxResult.success(tbClueService.assign(assignmentVo.getIds(), assignmentVo.getUserId()));
	}

	@ApiOperation("批量捞取")
	@PreAuthorize("@ss.hasPermi('clues:clue:gain')")
	@Log(title = "批量捞取", businessType = BusinessType.UPDATE)
	@PutMapping("/gain")
	public AjaxResult gain(@RequestBody AssignmentVo assignmentVo) {
		return AjaxResult.success(tbClueService.gain(assignmentVo.getIds(), assignmentVo.getUserId()));
	}

	@ApiOperation("上传线索")
	@Log(title = "上传线索", businessType = BusinessType.IMPORT)
	@PostMapping("/importData")
	public AjaxResult importData(MultipartFile file) throws Exception {
		ExcelListener excelListener = new ExcelListener(tbClueService);
		EasyExcel.read(file.getInputStream(), TbClueExcelVo.class, excelListener).sheet().doRead();
		return AjaxResult.success(excelListener.getResultData());
	}

}