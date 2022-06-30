package com.huike.web.controller.business;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.TbBusinessTrackRecord;
import com.huike.business.domain.vo.BusinessTrackVo;
import com.huike.business.service.ITbBusinessService;
import com.huike.business.service.ITbBusinessTrackRecordService;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;

/**
 * 商机跟进记录Controller
 * @date 2021-04-28
 */
@RestController
@RequestMapping("/business/record")
public class TbBusinessTrackRecordController extends BaseController {

    @Autowired
    private ITbBusinessTrackRecordService tbBusinessTrackRecordService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询商机跟进记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:record:list')")
    @GetMapping("/list")
    public  AjaxResult list(@RequestParam("businessId")Long id){

        List<TbBusinessTrackRecord> list= tbBusinessTrackRecordService.selectTbBusinessTrackRecordList(id);
        for (TbBusinessTrackRecord businessTrackRecord : list) {
            String[] items= businessTrackRecord.getKeyItems().split(",");
            for (String item : items) {
                String dictLable= sysDictDataService.selectDictLabel("communication_point",item);
                businessTrackRecord.getKeys().add(dictLable);
            }
        }
        return AjaxResult.success(list);

    }

    /**
     * 新增商机跟进记录
     */
    @PreAuthorize("@ss.hasPermi('business:record:add')")
    @Log(title = "商机跟进记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusinessTrackVo businessTrackVo){
        System.out.println("-------"+businessTrackVo);
        TbBusinessTrackRecord trackRecord=new TbBusinessTrackRecord();
        BeanUtils.copyProperties(businessTrackVo,trackRecord);
        trackRecord.setCreateTime(DateUtils.getNowDate());
        trackRecord.setCreateBy(SecurityUtils.getUsername());
        TbBusiness business=new TbBusiness();
        BeanUtils.copyProperties(businessTrackVo,business);
        business.setStatus(TbBusiness.StatusType.FOLLOWING.getValue());
        business.setId(businessTrackVo.getBusinessId());
        return toAjax(tbBusinessTrackRecordService.insertTbBusinessTrackRecord(business,trackRecord));
    }
}