package com.huike.clues.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;
import com.huike.common.core.dto.PageRequestDto;

import lombok.Data;

@Data
public class TbRuleAssignDto extends PageRequestDto {
	
	private static final long serialVersionUID = 1L;

	/** 线索id */
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
}
