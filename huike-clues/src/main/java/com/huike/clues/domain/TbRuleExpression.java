package com.huike.clues.domain;

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
 * 规则达式对象 tb_rule_expression
 * 
 * @date 2021-04-08
 */
@Data
public class TbRuleExpression{
	private static final long serialVersionUID = 1L;

	/** 规则表达式id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 规则key */
	@Excel(name = "规则key")
	private String ruleKey;

	/** 表达式（=，!=, 包含，不包含) */
	@Excel(name = "表达式", readConverterExp = "表达式（=，!=, 包含，不包含) ")
	private String expression;

	/** 规则值 */
	@Excel(name = "规则值")
	private String ruleValue;

	/** 顺序 */
	@Excel(name = "顺序")
	private int number;

	/** 规则id */
	@Excel(name = "规则id")
	private Long ruleId;

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
