package com.huike.web.controller.clues;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.ClueTrackRecordVo;
import com.huike.clues.dto.TbClueTrackRecordDto;
import com.huike.clues.service.ITbClueTrackRecordService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;

import io.swagger.annotations.ApiOperation;

/**
 * 线索跟进记录Controller
 * 
 * @date 2021-04-22
 */
@RestController
@RequestMapping("/clues/record")
public class TbClueTrackRecordController extends BaseController {
<<<<<<< HEAD
    @Autowired
    private ITbClueTrackRecordService tbClueTrackRecordService;

    /**
     * 查询线索跟进记录列表
     */
    //@ApiOperation("线索跟进记录列表")
    @PreAuthorize("@ss.hasPermi('clues:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbClueTrackRecord tbClueTrackRecord) {
        startPage();
        List<TbClueTrackRecord> list = tbClueTrackRecordService.selectTbClueTrackRecordList(tbClueTrackRecord);
        return getDataTable(list);
    }
=======
	@Autowired
	private ITbClueTrackRecordService tbClueTrackRecordService;
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	/**
	 * 查询线索跟进记录列表
	 */
	@ApiOperation("线索跟进记录列表")
	@PreAuthorize("@ss.hasPermi('clues:record:list')")
	@GetMapping("/list")
	public TableDataInfo list(TbClueTrackRecordDto tbClueTrackRecord) {
		Page<TbClueTrackRecord> page = tbClueTrackRecordService.selectTbClueTrackRecordList(tbClueTrackRecord);
		return getDataTable(page);
	}

	/**
	 * 获取线索跟进记录详细信息
	 */
	@ApiOperation("获取线索跟进记录详细信息")
	@PreAuthorize("@ss.hasPermi('clues:record:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(tbClueTrackRecordService.selectTbClueTrackRecordById(id));
	}

	/**
	 * 新增线索跟进记录
	 */
	@ApiOperation("新增线索跟进记录")
	@PreAuthorize("@ss.hasPermi('clues:record:add')")
	@Log(title = "线索跟进记录", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody ClueTrackRecordVo tbClueTrackRecord) {
		TbClueTrackRecord trackRecord = new TbClueTrackRecord();
		BeanUtils.copyProperties(tbClueTrackRecord, trackRecord);
		trackRecord.setCreateTime(DateUtils.getNowDate());
		trackRecord.setCreateBy(SecurityUtils.getUsername());
		TbClue tbClue = new TbClue();
		BeanUtils.copyProperties(tbClueTrackRecord, tbClue);
		tbClue.setStatus(TbClue.StatusType.FOLLOWING.getValue()); // 进行中
		tbClue.setId(tbClueTrackRecord.getClueId());
		return toAjax(tbClueTrackRecordService.insertTbClueTrackRecord(tbClue, trackRecord));
	}
}
