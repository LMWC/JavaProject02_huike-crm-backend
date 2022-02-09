package com.huike.report.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.vo.IndexStatisticsVo;
import com.huike.contract.domain.TbContract;
import com.huike.report.domain.vo.ActivityStatisticsVo;
import com.huike.report.domain.vo.IndexVo;
import com.huike.report.domain.vo.LineChartVo;
import com.huike.report.domain.vo.VulnerabilityMapVo;

public interface IReportService {

    /**
     *新增客户统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public LineChartVo contractStatistics(String beginCreateTime, String endCreateTime);


    /**
     * 学科分布统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String, Object>> subjectStatistics(String beginCreateTime, String endCreateTime);

    /**
     * 客户统计报表
     * @param tbContract
     * @return
     */
    public  List<TbContract> contractReportList(TbContract tbContract);

    /**
     * 销售统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public LineChartVo salesStatistics(String beginCreateTime, String endCreateTime);



    public List<Map<String, Object>> deptStatisticsList(String beginCreateTime, String endCreateTime);


    public List<Map<String, Object>> channelStatisticsList(String beginCreateTime, String endCreateTime);

    public List<Map<String, Object>> ownerShipStatisticsList(String beginCreateTime, String endCreateTime);

    /**
     * 线索统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public LineChartVo cluesStatistics(String beginCreateTime, String endCreateTime);


    /**
     * 渠道统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String, Object>> chanelStatistics(String beginCreateTime, String endCreateTime);


    /**
     * 活动统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String, Object>> activityStatistics(String beginCreateTime, String endCreateTime);



    public List<TbClue> cluesStatisticsList(TbClue clue);


    public List<ActivityStatisticsVo> activityStatisticsList(TbActivity activity);

    //漏洞统计
    public VulnerabilityMapVo getVulnerabilityMap(String beginCreateTime, String endCreateTime);


    public IndexVo getIndex(IndexStatisticsVo request);


    public List<Map<String,Object>> salesStatisticsForIndex(IndexStatisticsVo request);


    public List<Map<String,Object>> businessChangeStatisticsForIndex(IndexStatisticsVo request);


	public List<Map<String, Object>> clueChangeStatisticsForIndex(IndexStatisticsVo request);

	
	public Map<String, Object> getcontractsBasicInfo(IndexStatisticsVo request, String now);



}
