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
    private TbBusinessMapper businessMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;

    @Autowired
    private ReportMapper reportMpper;

    @Override
    public LineChartVO contractStatistics(String beginCreateTime, String endCreateTime) {
        LineChartVO lineChartVo =new LineChartVO();
        try {
            List<String> timeList= findDates(beginCreateTime,endCreateTime);
            lineChartVo.setxAxis(timeList);
            List<LineSeriesVO> series = new ArrayList<>();
            List<Map<String,Object>>  statistics = contractMapper.contractStatistics(beginCreateTime,endCreateTime);
            LineSeriesVO lineSeriesDTO1=new LineSeriesVO();
            lineSeriesDTO1.setName("新增客户数");
            LineSeriesVO lineSeriesDTO2=new LineSeriesVO();
            lineSeriesDTO2.setName("客户总数");
            int sum = 0;
            for (String s : timeList) {
                Optional optional=  statistics.stream().filter(d->d.get("dd").equals(s)).findFirst();
                if(optional.isPresent()){
                    Map<String,Object> cuurentData=  (Map<String,Object>)optional.get();
                    lineSeriesDTO1.getData().add(cuurentData.get("num"));
                    sum += Integer.parseInt(cuurentData.get("num").toString());
                }else{
                    lineSeriesDTO1.getData().add(0);
                }
                lineSeriesDTO2.getData().add(sum);
            }
            series.add(lineSeriesDTO1);
            series.add(lineSeriesDTO2);
            lineChartVo.setSeries(series);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return  lineChartVo;
    }

    @Override
    public LineChartVO salesStatistics(String beginCreateTime, String endCreateTime) {
        LineChartVO lineChartVo =new LineChartVO();
        try {
            List<String> timeList= findDates(beginCreateTime,endCreateTime);
            lineChartVo.setxAxis(timeList);
            List<LineSeriesVO> series = new ArrayList<>();
            List<Map<String,Object>>  statistics = contractMapper.salesStatistics(beginCreateTime,endCreateTime);
            LineSeriesVO lineSeriesVo=new LineSeriesVO();
            lineSeriesVo.setName("销售统计");
            int sum=0;
            for (String s : timeList) {
                Optional optional=  statistics.stream().filter(d->d.get("dd").equals(s)).findFirst();
                if(optional.isPresent()){
                    Map<String,Object> cuurentData=  (Map<String,Object>)optional.get();
                    lineSeriesVo.getData().add(cuurentData.get("sales"));
                }else{
                    lineSeriesVo.getData().add(0);
                }
            }
            series.add(lineSeriesVo);
            lineChartVo.setSeries(series);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return  lineChartVo;
    }




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

    /**
     * *************看我看我**************
     * 传入两个时间范围，返回这两个时间范围内的所有时间，并保存在一个集合中
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static List<String> findDates(String beginTime, String endTime)
            throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dBegin = sdf.parse(beginTime);
        Date dEnd = sdf.parse(endTime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        System.out.println("时间==" + allDate);
        return allDate;
    }


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


    /**
     * ************看我看我***********
     * 用我能少走很多路
     * 我是用来机选百分比的方法
      * @param all
     * @param num
     * @return
     */
    private BigDecimal getRadio(Integer all,Long num) {
        if(all.intValue()==0){
            return new BigDecimal(0);
        }
        BigDecimal numBigDecimal = new BigDecimal(num);
        BigDecimal allBigDecimal = new BigDecimal(all);
        BigDecimal divide = numBigDecimal.divide(allBigDecimal,4,BigDecimal.ROUND_HALF_UP);
        return divide.multiply(new BigDecimal(100));
    }


    /**
     * 获取首页基本数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public IndexBaseInfoVO getBaseInfo(String beginCreateTime, String endCreateTime) {
        //1）构建一个空的结果集对象
        IndexBaseInfoVO result = new IndexBaseInfoVO();
        //2 封装结果集属性
        // 2.1 由于查询需要用到用户名 调用工具类获取用户名
        String username = SecurityUtils.getUsername();
        try {
            //3 封装结果集对象
            result.setCluesNum(reportMpper.getCluesNum(beginCreateTime, endCreateTime, username));
            result.setBusinessNum(reportMpper.getBusinessNum(beginCreateTime, endCreateTime, username));
            result.setContractNum(reportMpper.getContractNum(beginCreateTime, endCreateTime, username));
            result.setSalesAmount(reportMpper.getSalesAmount(beginCreateTime, endCreateTime, username));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //4 返回结果集对象
        return result;
    }

}