package com.huike.web.controller.report;

import java.util.List;
import java.util.Map;

import com.huike.report.domain.vo.LineChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbClue;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.contract.domain.TbContract;
import com.huike.report.domain.vo.ActivityStatisticsVo;
import com.huike.report.service.IReportService;


@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

    @Autowired
    private IReportService reportService;

    /**
     * 客户统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/contractStatistics/{beginCreateTime}/{endCreateTime}")
    public LineChartVO contractStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.contractStatistics(beginCreateTime,endCreateTime);
    }

    /**
     * 销售统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/salesStatistics/{beginCreateTime}/{endCreateTime}")
    public LineChartVO salesStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return reportService.salesStatistics(beginCreateTime,endCreateTime);
    }



    /**
     * 渠道统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/chanelStatistics/{beginCreateTime}/{endCreateTime}")
    public AjaxResult chanelStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return AjaxResult.success(reportService.chanelStatistics(beginCreateTime,endCreateTime));
    }


    /**
     * 活动统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/activityStatistics/{beginCreateTime}/{endCreateTime}")
    public AjaxResult activityStatistics(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        return AjaxResult.success(reportService.activityStatistics(beginCreateTime,endCreateTime));
    }

    /**
     * 查询活动管理列表
     */
    @GetMapping("/contractStatisticsList")
    public TableDataInfo contractStatisticsList(TbContract contract){
        startPage();
        List<TbContract> list= reportService.contractReportList(contract);
        return getDataTable(list);
    }


    /**
     * 销售统计部门报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/deptStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo deptStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        startPage();
        List<Map<String, Object>> list= reportService.deptStatisticsList(beginCreateTime,endCreateTime);
        return getDataTablePage(list);
    }

    /**
     * 销售统计渠道报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/channelStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo channelStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        startPage();
        List<Map<String, Object>> list= reportService.channelStatisticsList(beginCreateTime,endCreateTime);
        return getDataTablePage(list);
    }

    /**
     * 销售统计归属人报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/ownerShipStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo ownerShipStatisticsList(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){
        startPage();
        List<Map<String, Object>> list = reportService.ownerShipStatisticsList(beginCreateTime,endCreateTime);
        return getDataTablePage(list);
    }


    /**
     * 线索统计报表
     * @param clue
     * @return
     */
    @GetMapping("/cluesStatisticsList")
    public TableDataInfo cluesStatisticsList(TbClue clue){
        startPage();
        List<TbClue> list= reportService.cluesStatisticsList(clue);
        return getDataTable(list);
    }

    /**
     * 活动渠道统计
     * @param activity
     * @return
     */
    @GetMapping("/activityStatisticsList")
    public TableDataInfo activityStatisticsList(TbActivity activity) {
        List<ActivityStatisticsVo> list= reportService.activityStatisticsList(activity);
        return getDataTablePage(list);
    }


}
