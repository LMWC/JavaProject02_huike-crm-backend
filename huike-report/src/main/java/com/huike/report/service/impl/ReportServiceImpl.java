package com.huike.report.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.huike.report.domain.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.vo.IndexStatisticsVo;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.utils.SecurityUtils;
import com.huike.contract.domain.TbContract;
import com.huike.contract.mapper.TbContractMapper;
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
    private SysDeptMapper deptMapper;

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;
    
    @Autowired
    private ReportMapper reportMpper;



    /**
     * 渠道统计
     */
    @Override
    public List<Map<String, Object>> chanelStatistics(String beginCreateTime, String endCreateTime) {
        List<Map<String, Object>> data= contractMapper.chanelStatistics(beginCreateTime,endCreateTime);
        for (Map<String, Object> datum : data) {
            String subjectValue= (String) datum.get("channel");
            String lable=  sysDictDataMapper.selectDictLabel("clues_item",subjectValue);
            datum.put("channel",lable);
        }
        return data;
    }

    @Override
    public List<TbContract> contractReportList(TbContract tbContract) {
       return contractMapper.selectTbContractList(tbContract);
    }


    @Override
    public List<Map<String, Object>> activityStatistics(String beginCreateTime, String endCreateTime) {
        List<Map<String, Object>> data= contractMapper.activityStatistics(beginCreateTime,endCreateTime);
        for (Map<String, Object> datum : data) {
            Long activityId= (Long) datum.get("activity_id");
            TbActivity tbActivity = activityMapper.selectTbActivityById(activityId);
            if(tbActivity==null){
                datum.put("activity", "其他");
            }else{
                datum.put("activity", tbActivity.getName());
            }
        }
        return data;
    }

    /**
     * 按照部门统计销售
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public List<Map<String, Object>> deptStatisticsList(String beginCreateTime, String endCreateTime) {
        List<Map<String, Object>> data= contractMapper.deptStatistics(beginCreateTime,endCreateTime);
        for (Map<String, Object> datum : data) {
            Long deptId= (Long) datum.get("dept_id");
            if(deptId!=null){
                SysDept dept= deptMapper.selectDeptById(deptId);
                datum.put("deptName", dept.getDeptName());
            }
        }
        return data;
    }


    /**
     * 按照渠道统计销售
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public List<Map<String, Object>> channelStatisticsList(String beginCreateTime, String endCreateTime) {
        List<Map<String, Object>> data= contractMapper.channelStatistics(beginCreateTime,endCreateTime);
        for (Map<String, Object> datum : data) {
            String subjectValue= (String) datum.get("channel");
            if(subjectValue!=null){
                String lable=  sysDictDataMapper.selectDictLabel("clues_item",subjectValue);
                datum.put("channel",lable);
            }
        }
        return data;
    }


    /**
     * 按照归属人统计销售
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public List<Map<String, Object>> ownerShipStatisticsList(String beginCreateTime, String endCreateTime) {
       return  contractMapper.ownerShipStatistics(beginCreateTime,endCreateTime);
    }


    @Override
    public List<TbClue> cluesStatisticsList(TbClue clue) {
        return clueMapper.selectTbClueForReport(clue);
    }

    @Override
    public List<ActivityStatisticsVo> activityStatisticsList(TbActivity query) {
        query.setStatus("2");
        List<TbActivity> activities= activityMapper.selectTbActivityList(query);
        Map<String, Object> timeMap = query.getParams();
        List<ActivityStatisticsVo> list=new ArrayList<>();
        for (TbActivity activity : activities) {
            ActivityStatisticsVo dto = new ActivityStatisticsVo();
            BeanUtils.copyProperties(activity, dto);
            TbClue tbClue = new TbClue();
            tbClue.setActivityId(activity.getId());
            tbClue.setChannel(activity.getChannel());
            tbClue.setParams(timeMap);
            Map<String, Object> clueCount = clueMapper.countByActivity(tbClue);
            if (clueCount != null) {
                dto.setCluesNum(Integer.parseInt(clueCount.get("total").toString()));
                if(clueCount.get("falseClues")!=null){
                    dto.setFalseCluesNum(Integer.parseInt(clueCount.get("falseClues").toString()));
                }
                if (clueCount.get("toBusiness") != null) {
                    dto.setBusinessNum(Integer.parseInt(clueCount.get("toBusiness").toString()));
                }
            }
            TbContract tbContract = new TbContract();
            tbContract.setChannel(activity.getChannel());
            tbContract.setActivityId(activity.getId());
            tbContract.setParams(timeMap);
            Map<String, Object> contractCount = contractMapper.countByActivity(tbContract);
            if (contractCount != null) {
                dto.setCustomersNum(Integer.parseInt(contractCount.get("customersNum").toString()));
                if(contractCount.get("amount")==null) {
                	dto.setAmount(0d);
                }else {
                	dto.setAmount((Double) contractCount.get("amount"));
                }
                if(contractCount.get("cost")==null) {
                	dto.setCost(0d);
                }else {
                	dto.setCost((Double) contractCount.get("cost"));
                }
                
            }
            list.add(dto);
        }
        return list;
    }


    @Override
    public IndexVo getIndex(IndexStatisticsVo request) {
        Long deptId= request.getDeptId();
        TbAssignRecord tbAssignRecord=new TbAssignRecord();
        tbAssignRecord.setLatest("1");
        assignRecordMapper.selectAssignRecordList(tbAssignRecord);
        return null;
    }


    /**
     * 获取首页基本数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public IndexBaseInfoVO getBaseInfo(String beginCreateTime, String endCreateTime) {
        //1 构建一个空的结果集对象
        IndexBaseInfoVO result = new IndexBaseInfoVO();
        //2 封装结果集属性
        // 2.1 由于查询需要用到用户名 调用工具类获取用户名
        String username = SecurityUtils.getUsername();
        // 2.2 开始查询第一个属性 线索数量
        result.setCluesNum(reportMpper.getCluesNum(beginCreateTime, endCreateTime, username));
        // 2.3 开始查询第一个属性 商机数量
        result.setBusinessNum(reportMpper.getBusinessNum(beginCreateTime, endCreateTime, username));
        // 2.4 开始查询第一个属性 合同数量
        result.setContractNum(reportMpper.getContractNum(beginCreateTime, endCreateTime, username));
        // 2.5 开始查询第一个属性 销售金额数量
        result.setSalesAmount(reportMpper.getSalesAmount(beginCreateTime, endCreateTime, username));
        //5 返回结果集对象
        return result;
    }
}