package com.huike.report.domain.vo;

import java.util.ArrayList;
import java.util.List;

public class LineSeriesVO {

    private String name;

    private List<Object> data =new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}
