package com.huike.clues.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则分配对象 tb_rule_assign
 */
public class TbRuleAssign extends BaseEntity
{
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

    private List<String> assignObjectNames=new ArrayList<>();

    /** 分配类型(本人,角色，组织) */
    @Excel(name = "分配类型(本人,角色，组织)")
    private String assignType;

    /** 规则顺序 */
    @Excel(name = "规则顺序")
    private Long ruleOrder;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAssignObjectNames() {
        return assignObjectNames;
    }

    public void setAssignObjectNames(List<String> assignObjectNames) {
        this.assignObjectNames = assignObjectNames;
    }

    private List<TbRuleExpression> rulesList;

    public List<TbRuleExpression> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<TbRuleExpression> rulesList) {
        this.rulesList = rulesList;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setRuleCondition(String ruleCondition)
    {
        this.ruleCondition = ruleCondition;
    }

    public String getRuleCondition()
    {
        return ruleCondition;
    }
    public void setRuleExpression(String ruleExpression)
    {
        this.ruleExpression = ruleExpression;
    }

    public String getRuleExpression()
    {
        return ruleExpression;
    }
    public void setAssignObject(String assignObject)
    {
        this.assignObject = assignObject;
    }

    public String getAssignObject()
    {
        return assignObject;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public void setRuleOrder(Long ruleOrder)
    {
        this.ruleOrder = ruleOrder;
    }

    public Long getRuleOrder()
    {
        return ruleOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ruleCondition", getRuleCondition())
                .append("ruleExpression", getRuleExpression())
                .append("assignObject", getAssignObject())
                .append("assignType", getAssignType())
                .append("ruleOrder", getRuleOrder())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }

    public enum AssignType{
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

        private AssignType(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

}
