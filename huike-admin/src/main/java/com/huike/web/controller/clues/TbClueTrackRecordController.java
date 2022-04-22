package com.huike.web.controller.clues;


import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.ClueTrackRecordVo;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 线索跟进记录Controller
 * @date 2021-04-22
 */
@RestController
@RequestMapping("/clues/record")
public class TbClueTrackRecordController extends BaseController {

    /**
     * 查询线索跟进记录列表
     */
    @PreAuthorize("@ss.hasPermi('clues:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam("clueId")Long clueId) {
       return null;
    }


    /**
     * 新增线索跟进记录
     */
    @PreAuthorize("@ss.hasPermi('clues:record:add')")
    @Log(title = "线索跟进记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClueTrackRecordVo tbClueTrackRecord) {
        return null;
    }
}
