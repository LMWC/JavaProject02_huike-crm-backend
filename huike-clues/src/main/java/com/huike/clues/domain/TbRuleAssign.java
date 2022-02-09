package com.huike.clues.domain;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 规则分配对象 tb_rule_assign
 */
@Data
public class TbRuleAssign {
	private static final long serialVersionUID = 1L;

	/** 线索id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 规则集合 */
	@Excel(name = "规则集合")
	private String ruleCondition;

	/** 规则表达式 */
	@Excel(name = "规则表达式")
	@JsonIgnore
	private String ruleExpression;

	/** 分配对象 */
	@Excel(name = "分配对象")
	private String assignObject;

	private List<String> assignObjectNames = new ArrayList<>();

	/** 分配类型(本人,角色，组织) */
	@Excel(name = "分配类型(本人,角色，组织)")
	private String assignType;

	/** 规则顺序 */
	@Excel(name = "规则顺序")
	private Long ruleOrder;

	private String type;

	private List<TbRuleExpression> rulesList;
	
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

	@AllArgsConstructor
	@Getter
	public enum AssignType {
		/**
		 * 小时
		 */
		USER("0"),

		/**
		 * 天
		 */
		POST("1"),

		/**
		 * 部门
		 */
		DEPARTMENT("2"),

		/**
		 * 角色
		 */
		ROLE("3");

		private String value;

	}

}
