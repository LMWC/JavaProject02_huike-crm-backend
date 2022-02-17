package com.huike.clues.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

/**
 * 线索池规则对象 tb_rule_pool
 * @date 2021-04-16
 */
public class TbRulePool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 线程池规则 */
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLimitTime(Long limitTime) 
    {
        this.limitTime = limitTime;
    }

    public Long getLimitTime() 
    {
        return limitTime;
    }
    public void setLimitTimeType(String limitTimeType) 
    {
        this.limitTimeType = limitTimeType;
    }

    public String getLimitTimeType() 
    {
        return limitTimeType;
    }
    public void setWarnTime(Long warnTime) 
    {
        this.warnTime = warnTime;
    }

    public Long getWarnTime() 
    {
        return warnTime;
    }
    public void setWarnTimeType(String warnTimeType) 
    {
        this.warnTimeType = warnTimeType;
    }

    public String getWarnTimeType() 
    {
        return warnTimeType;
    }
    public void setRepeatGetTime(Long repeatGetTime) 
    {
        this.repeatGetTime = repeatGetTime;
    }

    public Long getRepeatGetTime() 
    {
        return repeatGetTime;
    }
    public void setRepeatType(String repeatType) 
    {
        this.repeatType = repeatType;
    }

    public String getRepeatType() 
    {
        return repeatType;
    }
    public void setMaxNunmber(Long maxNunmber) 
    {
        this.maxNunmber = maxNunmber;
    }

    public Long getMaxNunmber() 
    {
        return maxNunmber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("limitTime", getLimitTime())
            .append("limitTimeType", getLimitTimeType())
            .append("warnTime", getWarnTime())
            .append("warnTimeType", getWarnTimeType())
            .append("repeatGetTime", getRepeatGetTime())
            .append("repeatType", getRepeatType())
            .append("maxNunmber", getMaxNunmber())
            .append("type", getType())
            .toString();
    }

    public enum LimitTimeType
    {
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

        private LimitTimeType(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    public enum RuleType
    {
        /**
         * 小时
         */
        CLUES("0"),

        /**
         * 天
         */
        BUSINESS("1");

        private String value;

        private RuleType(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }
}
