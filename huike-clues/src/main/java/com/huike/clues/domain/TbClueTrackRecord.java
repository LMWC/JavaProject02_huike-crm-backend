package com.huike.clues.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 线索跟进记录对象 tb_clue_track_record
 * 
 * @date 2021-04-19
 */
@Data
public class TbClueTrackRecord{
	private static final long serialVersionUID = 1L;

	/** 任务id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 线索id */
	private Long clueId;

	/** 意向等级 */
	private String subject;

	/** 跟进记录 */
	private String record;

	/** 意向等级 */
	private String level;

	/** 0 正常跟进记录 1 伪线索 */
	private String type;

	/** 原因 */
	private String falseReason;

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
