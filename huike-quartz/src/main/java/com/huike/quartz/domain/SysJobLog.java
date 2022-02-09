package com.huike.quartz.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import lombok.Data;

/**
 * 定时任务调度日志表 sys_job_log
 * 
 * 
 */
@Data
public class SysJobLog{
	private static final long serialVersionUID = 1L;

	/** ID */
	@Excel(name = "日志序号")
	@TableId(type = IdType.AUTO)
	private Long jobLogId;

	/** 任务名称 */
	@Excel(name = "任务名称")
	private String jobName;

	/** 任务组名 */
	@Excel(name = "任务组名")
	private String jobGroup;

	/** 调用目标字符串 */
	@Excel(name = "调用目标字符串")
	private String invokeTarget;

	/** 日志信息 */
	@Excel(name = "日志信息")
	private String jobMessage;

	/** 执行状态（0正常 1失败） */
	@Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
	private String status;

	/** 异常信息 */
	@Excel(name = "异常信息")
	private String exceptionInfo;

	/** 开始时间 */
	private Date startTime;

	/** 停止时间 */
	private Date stopTime;

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
