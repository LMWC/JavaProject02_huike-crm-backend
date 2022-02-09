package com.huike.web.controller.report;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.domain.vo.IndexStatisticsVo;
import com.huike.common.core.domain.AjaxResult;
import com.huike.report.service.IReportService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IReportService reportService;



    @ApiOperation("基础数据统计")
    @GetMapping()
    public AjaxResult contractStatistics(IndexStatisticsVo request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        request.setBeginCreateTime(request.getBeginCreateTime()+" 00:00:00");
        request.setEndCreateTime(request.getEndCreateTime()+" 23:59:59");
        Map<String,Object> result = reportService.getcontractsBasicInfo(request,sdf.format(new Date()));
        return AjaxResult.success(result); 
    }


    @ApiOperation("销售龙虎榜")
    @GetMapping("/salesStatistic")
    public AjaxResult salesStatistics(IndexStatisticsVo request){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        request.setBeginCreateTime(request.getBeginCreateTime()+" 00:00:00");
        request.setEndCreateTime(request.getEndCreateTime()+" 23:59:59");
    	List<Map<String,Object>> list= reportService.clueChangeStatisticsForIndex(request);
        return AjaxResult.success(list);
    }



    @ApiOperation("商机转换龙虎榜")
    @GetMapping("/businessChangeStatistics")
    public AjaxResult businessChangeStatistics(IndexStatisticsVo request){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        request.setBeginCreateTime(request.getBeginCreateTime()+" 00:00:00");
        request.setEndCreateTime(request.getEndCreateTime()+" 23:59:59");
        List<Map<String,Object>> list= reportService.businessChangeStatisticsForIndex(request);
        return AjaxResult.success(list);
    }
}
