package com.huike.report.domain.vo;


import lombok.Data;

/**
 * 首页--今日待办--返回前端VO类
 */
@Data
public class IndexTodoInfoVO {

    private Integer tofollowedCluesNum=0;  //待跟进线索数目
    private Integer tofollowedBusinessNum=0;  //待跟进商机数目
    private Integer toallocatedCluesNum=0;  //待分配线索数目
    private Integer toallocatedBusinessNum=0;  //待分配商机数目

}
