package com.huike.report.domain.vo;

import lombok.Data;

/**
 * 首页基本数据VO对象
 */
@Data
public class IndexBaseInfoVO {
    private Integer cluesNum=0;  //线索数目
    private Integer businessNum=0;  //商机数目
    private Integer contractNum=0;  //合同数目
    private Double salesAmount=0.0;  //销售金额
}
