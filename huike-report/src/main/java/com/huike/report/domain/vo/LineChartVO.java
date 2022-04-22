package com.huike.report.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线图
 */
public class LineChartVO {

    private List<String> xAxis = new ArrayList<>();
    private List<LineSeriesVO> series = new ArrayList<>();

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<LineSeriesVO> getSeries() {
        return series;
    }

    public void setSeries(List<LineSeriesVO> series) {
        this.series = series;
    }
}

