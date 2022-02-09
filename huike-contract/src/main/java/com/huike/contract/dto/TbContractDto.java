package com.huike.contract.dto;

import com.huike.common.core.dto.PageRequestDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TbContractDto  extends PageRequestDto{
	
	private static final long serialVersionUID = 1L;
	
    /** 合同编号 */
	@ApiModelProperty(value="contractNo",name="合同编号")
	private String contractNo;

    /** 渠道来源 */
	@ApiModelProperty(value="channel",name="渠道来源")
	private String channel;
	
	/** 手机号 */
	@ApiModelProperty(value="phone",name="手机号")
	private String phone;

    /** 客户姓名 */
	@ApiModelProperty(value="name",name="客户姓名")
    private String name;

    /** 购买课程ID */
	@ApiModelProperty(value="courseId",name="购买课程ID")
    private String courseId;
	
    /** 购买学科 */
	@ApiModelProperty(value="subject",name="购买学科")
    private String subject;
	
    /** 创建时间 */
	@ApiModelProperty(value="beginCreateTime",name="创建时间")
	private String beginCreateTime;

    /** 结束时间 */
	@ApiModelProperty(value="endCreateTime",name="结束时间")
	private String endCreateTime;
}
