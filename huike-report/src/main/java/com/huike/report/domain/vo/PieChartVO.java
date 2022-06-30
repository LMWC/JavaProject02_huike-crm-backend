package com.huike.report.domain.vo;

import lombok.Data;

/**
 * 饼图
 */
@Data
public class PieChartVO {

    /**
     * 学科
     */
    private String subject;

    /**
     * 合同数量 / 报名人数
     */
    private Integer num;
}
