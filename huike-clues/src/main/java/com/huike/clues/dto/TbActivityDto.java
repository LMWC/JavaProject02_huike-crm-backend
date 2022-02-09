package com.huike.clues.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.common.core.dto.PageRequestDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TbActivityDto extends PageRequestDto{
	
	private static final long serialVersionUID = 1L;
	
    /** 活动编号 */
	@ApiModelProperty(value="code",name="活动编码")
	private String code;

    /** 渠道来源 */
	@ApiModelProperty(value="channel",name="渠道来源")
	private String channel;
	
	@ApiModelProperty(value="channel",name="渠道来源")
	private String state;

    /** 创建开始时间 */
	@ApiModelProperty(value="beginCreateTime",name="创建开始时间")
    private String beginCreateTime;

    /** 创建结束时间 */
	@ApiModelProperty(value="endCreateTime",name="创建结束时间")
    private String endCreateTime;

    /** 活动开始时间 */
	@ApiModelProperty(value="beginTime",name="开始时间")
	private String beginTime;

    /** 活动结束时间 */
	@ApiModelProperty(value="endTime",name="结束时间")
	private String endTime;
}