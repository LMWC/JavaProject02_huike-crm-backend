package com.huike.clues.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

/**
 * 规则达式对象 tb_rule_expression
 * @date 2021-04-08
 */
public class TbRuleExpression extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规则表达式id */
    private Long id;

    /** 规则key */
    @Excel(name = "规则key")
    private String ruleKey;

    /** 表达式（=，!=, 包含，不包含)  */
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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setRuleKey(String ruleKey)
    {
        this.ruleKey = ruleKey;
    }

    public String getRuleKey()
    {
        return ruleKey;
    }
    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    public String getExpression()
    {
        return expression;
    }
    public void setRuleValue(String ruleValue)
    {
        this.ruleValue = ruleValue;
    }

    public String getRuleValue()
    {
        return ruleValue;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setRuleId(Long ruleId)
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId()
    {
        return ruleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ruleKey", getRuleKey())
                .append("expression", getExpression())
                .append("ruleValue", getRuleValue())
                .append("number", getNumber())
                .append("ruleId", getRuleId())
                .toString();
    }
}
