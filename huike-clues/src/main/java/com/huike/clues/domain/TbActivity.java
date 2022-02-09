package com.huike.clues.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动管理对象 tb_activity
 *
 * @author WGL
 * @date 2021-04-02
 */
@ApiModel(value="活动对象",description="活动类")
@Data
public class TbActivity{
	private static final long serialVersionUID = 1L;

	/** id */
	@ApiModelProperty(value="id",name="主键ID",example="1")
	@TableId(type = IdType.AUTO)
	private Long id;

	// 编码
	@ApiModelProperty(value="code",name="活动编码")
	private String code;

	@ApiModelProperty(value="name",name="活动名称",example="线下推广活动")
	private String name;

	/** 渠道来源 */
	@Excel(name = "渠道来源")
	@ApiModelProperty(value="channel",name="渠道来源")
	private String channel;

	/** 活动简介 */
	@Excel(name = "活动简介")
	@ApiModelProperty(value="info",name="活动简介")
	private String info;

	/** 活动类型 */
	@Excel(name = "活动类型")
	@ApiModelProperty(value="type",name="活动类型")
	private String type;

	/** 课程折扣 */
	@Excel(name = "课程折扣")
	@ApiModelProperty(value="discount",name="课程折扣",example = "9.5")
	private Float discount;

	/** 课程代金券 */
	@Excel(name = "课程代金券")
	@ApiModelProperty(value="vouchers",name="课程代金券",example = "50")
	private Integer vouchers;

	/** 状态 */
	@Excel(name = "状态")
	@ApiModelProperty(value="status",name="状态")
	private String status;

	/** 开始时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@ApiModelProperty(value="beginTime",name="开始时间")
	private Date beginTime;

	/** 结束时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@ApiModelProperty(value="endTime",name="结束时间")
	private Date endTime;

	/** 搜索值 */
	@TableField(exist = false)
	@JsonIgnore
	private String searchValue;

	@TableField(exist = false)
	/** 创建者 */
	private String createBy;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@TableField(exist = false)
	/** 更新者 */
	private String updateBy;

	@TableField(exist = false)
	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@TableField(exist = false)
	/** 备注 */
	@JsonIgnore
	private String remark;

	/** 请求参数 */
	@TableField(exist = false)
	@JsonIgnore
	private Map<String, Object> params;
}