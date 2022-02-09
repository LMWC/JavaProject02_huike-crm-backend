package com.huike.clues.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 线索池规则对象 tb_rule_pool
 * 
 * @date 2021-04-16
 */
@Data
@TableName("tb_rule_pool")
public class TbRulePool {
	private static final long serialVersionUID = 1L;

	/** 线程池规则 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 回收时限 */
	@Excel(name = "回收时限")
	private Long limitTime;

	/** 回收时限字典 */
	@Excel(name = "回收时限字典")
	private String limitTimeType;

	/** 警告时间 */
	@Excel(name = "警告时间")
	private Long warnTime;

	/** 警告时间字典单位类型 */
	@Excel(name = "警告时间字典单位类型")
	private String warnTimeType;

	/** 重复捞取时间 */
	@Excel(name = "重复捞取时间")
	private Long repeatGetTime;

	/** 重复捞取时间字典 */
	@Excel(name = "重复捞取时间字典")
	private String repeatType;

	/** 最大保有量 */
	@Excel(name = "最大保有量")
	private Long maxNunmber;

	/** 0 线索 1 商机 */
	@Excel(name = "0 线索 1 商机")
	private String type;
	
	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/** 搜索值 */
	@TableField(exist = false)
	@JsonIgnore
	private String searchValue;

	@TableField(exist = false)
	/** 创建者 */
	private String createBy;

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
	
	@AllArgsConstructor
	@Getter
	public enum LimitTimeType {
		/**
		 * 小时
		 */
		HOUR("0"),

		/**
		 * 天
		 */
		DAY("1"),

		/**
		 * 周
		 */
		WEEK("2");

		private String value;

	}

	@Getter
	@AllArgsConstructor
	public enum RuleType {
		/**
		 * 小时
		 */
		CLUES("0"),

		/**
		 * 天
		 */
		BUSINESS("1");

		private String value;

	}
}
