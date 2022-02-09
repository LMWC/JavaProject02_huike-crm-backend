package com.huike.clues.dto;

import com.huike.common.core.dto.PageRequestDto;
import lombok.Data;

@Data
public class TbCourseDto extends PageRequestDto {
    /** 课程名称 */
    private String name;

    /** 课程编码 */
    private String code;

    /** 课程学科 */
    private String subject;

    /** 价格 */
    private Integer price;

    /** 适用人群 */
    private String applicablePerson;

    /** 课程描述信息 */
    private String info;

    /** 开始时间*/
    private String beginCreateTime;

    /** 结束时间*/
    private String endCreateTime;
}
