package com.huike.report.service;

import java.util.List;
import java.util.Map;

import com.huike.clues.domain.vo.IndexStatisticsVo;
import com.huike.report.domain.vo.IndexVo;

public interface IReportService {


    public IndexVo getIndex(IndexStatisticsVo request);


    public List<Map<String,Object>> salesStatisticsForIndex(IndexStatisticsVo request);


}
