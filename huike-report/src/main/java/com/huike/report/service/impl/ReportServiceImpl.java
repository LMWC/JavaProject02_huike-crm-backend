package com.huike.report.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.vo.IndexStatisticsVo;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.utils.SecurityUtils;
import com.huike.contract.mapper.TbContractMapper;
import com.huike.report.domain.vo.IndexVo;
import com.huike.report.mapper.ReportMapper;
import com.huike.report.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private TbContractMapper contractMapper;


    @Autowired
    private SysDictDataMapper sysDictDataMapper;


    @Autowired
    private TbClueMapper clueMapper;

    @Autowired
    private TbActivityMapper activityMapper;

    @Autowired
    private TbBusinessMapper businessMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;
    
    @Autowired
    private ReportMapper reportMpper;


    @Override
    public IndexVo getIndex(IndexStatisticsVo request) {
        Long deptId= request.getDeptId();
        TbAssignRecord tbAssignRecord=new TbAssignRecord();
        tbAssignRecord.setLatest("1");
        assignRecordMapper.selectAssignRecordList(tbAssignRecord);
        return null;
    }

    @Override
    public List<Map<String, Object>> salesStatisticsForIndex(IndexStatisticsVo request) {
        List<Map<String, Object>> list= contractMapper.contractStatisticsByUser(request);
        for (Map<String, Object> datum : list) {
            Long deptId= (Long) datum.get("dept_id");
            if(deptId!=null){
                SysDept dept= deptMapper.selectDeptById(deptId);
                datum.put("deptName", dept.getDeptName());
            }
        }
        return list;
    }

	private BigDecimal getRadio(Integer all,Long num) {
        if(all.intValue()==0){
            return new BigDecimal(0);
        }
		BigDecimal numBigDecimal = new BigDecimal(num);
		BigDecimal allBigDecimal = new BigDecimal(all);
		BigDecimal divide = numBigDecimal.divide(allBigDecimal,4,BigDecimal.ROUND_HALF_UP);
		return divide.multiply(new BigDecimal(100));
	}

}