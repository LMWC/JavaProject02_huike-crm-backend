package com.huike.clues.dto;

import com.huike.common.core.dto.PageRequestDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *   线索DTO
 * @author WGL
 *
 */
@Data
public class TbClueDto extends PageRequestDto{
	
	private static final long serialVersionUID = 1L;
	
	/** id */
	@ApiModelProperty(value="id",name="id")
	private String id;
	
    /** 手机号 */
	@ApiModelProperty(value="phone",name="手机号")
	private String phone;

    /** 线索归属 */
	@ApiModelProperty(value="owner",name="线索归属")
	private String owner;
	
	/** 渠道来源 */
	@ApiModelProperty(value="channel",name="渠道来源")
	private String channel;
	
	/** 渠道来源 */
	@ApiModelProperty(value="status",name="渠道来源")
	private String status;

    /** 创建开始时间 */
	@ApiModelProperty(value="beginCreateTime",name="创建开始时间")
    private String beginCreateTime;

    /** 创建结束时间 */
	@ApiModelProperty(value="endCreateTime",name="创建结束时间")
    private String endCreateTime;

    /** 课程学科 */
	@ApiModelProperty(value="subject",name="课程学科")
	private String subject;
	
	
	//==============修改条件=============
	@ApiModelProperty(value="name",name="name")
	private String name;
	
	@ApiModelProperty(value="activityId",name="activityId")
	private String activityId;
	
	@ApiModelProperty(value="age",name="age")
	private String age;
	
	@ApiModelProperty(value="qq",name="qq")
	private String qq;
	
	@ApiModelProperty(value="sex",name="sex")
	private String sex;
	
	@ApiModelProperty(value="weixin",name="weixin")
	private String weixin;
	
	@ApiModelProperty(value="level",name="level")
	private String level;
}