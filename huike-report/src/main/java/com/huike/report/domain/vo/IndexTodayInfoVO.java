package com.huike.report.domain.vo;

import lombok.Data;

/**
 * 今日简报数据
 */
@Data
public class IndexTodayInfoVO {
    private Integer todayCluesNum=0;  //今日线索数目
    private Integer todayBusinessNum=0;  //今日商机数目
    private Integer todayContractNum=0;  //今日合同数目
    private Double todaySalesAmount=0.0;  //今日销售金额
}
