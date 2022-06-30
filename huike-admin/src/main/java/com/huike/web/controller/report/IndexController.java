package com.huike.web.controller.report;


import com.huike.clues.domain.vo.IndexStatisticsVo;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huike.common.core.domain.AjaxResult;
import com.huike.report.service.IReportService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IReportService reportService;


    /**
     * 首页--基础数据统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/getBaseInfo")
    public AjaxResult getBaseInfo(@RequestParam("beginCreateTime") String beginCreateTime,
                                  @RequestParam("endCreateTime") String endCreateTime){
        return AjaxResult.success(reportService.getBaseInfo(beginCreateTime,endCreateTime));
    }

    /**
     * 首页--获取今日简报数据
     * @return
     */
    @GetMapping("/getTodayInfo")
    public AjaxResult getTodayInfo() {
        return AjaxResult.success(reportService.getTodayInfo(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
    }

    /**
     * 首页--获取待办数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/getTodoInfo")
    public AjaxResult getTodoInfo(@RequestParam("beginCreateTime") String beginCreateTime,@RequestParam("endCreateTime") String endCreateTime){
        return AjaxResult.success(reportService.getTodoInfo(beginCreateTime,endCreateTime));
    }

    /**
     * 首页--商机转换龙虎榜
     * @param request
     * @return
     */
    @GetMapping("/businessChangeStatistics")
    public AjaxResult businessChangeStatistics(IndexStatisticsVo request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        request.setBeginCreateTime(request.getBeginCreateTime()+" 00:00:00");
        request.setEndCreateTime(request.getEndCreateTime()+" 23:59:59");
        List<Map<String,Object>> list= reportService.businessChangeStatisticsForIndex(request);
        return AjaxResult.success(list);
    }

    /**
     * 首页--线索转化龙虎榜
     * @param request
     * @return
     */
    @GetMapping("/salesStatistic")
    public AjaxResult salesStatistics(IndexStatisticsVo request){
        request.setBeginCreateTime(request.getBeginCreateTime()+" 00:00:00");
        request.setEndCreateTime(request.getEndCreateTime()+" 23:59:59");
        List<Map<String,Object>> list= reportService.clueChangeStatisticsForIndex(request);
        return AjaxResult.success(list);
    }

}