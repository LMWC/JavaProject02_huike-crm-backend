package com.huike.report.domain.vo;


import java.util.ArrayList;
import java.util.List;

/**
 * 折线图
 */
public class LineChartVo {

    private List<String> xAxis = new ArrayList<>();
    private List<LineSeriesVo> series = new ArrayList<>();

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<LineSeriesVo> getSeries() {
        return series;
    }

    public void setSeries(List<LineSeriesVo> series) {
        this.series = series;
    }
}
