package com.huike.business.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import lombok.Data;

/**
 * 商机跟进记录对象 tb_business_track_record
 * 
 * @date 2021-04-28
 */
@Data
public class TbBusinessTrackRecord{
	
	private static final long serialVersionUID = 1L;

	/** 任务id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 线索id */
	@Excel(name = "线索id")
	private Long businessId;

	/** 沟通重点 */
	@Excel(name = "沟通重点")
	private String keyItems;

	@TableField(exist = false)
	private List<String> keys = new ArrayList<>();

	/** 沟通纪要 */
	@Excel(name = "沟通纪要")
	private String record;

	/** 跟进状态 */
	@Excel(name = "跟进状态")
	private String trackStatus;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date nextTime;

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
