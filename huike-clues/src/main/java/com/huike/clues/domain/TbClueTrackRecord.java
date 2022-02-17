package com.huike.clues.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 线索跟进记录对象 tb_clue_track_record
 * @date 2021-04-19
 */
public class TbClueTrackRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务id */
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


    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public Long getClueId() {
        return clueId;
    }

    public void setClueId(Long clueId) {
        this.clueId = clueId;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject() 
    {
        return subject;
    }
    public void setRecord(String record) 
    {
        this.record = record;
    }

    public String getRecord() 
    {
        return record;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setFalseReason(String falseReason) 
    {
        this.falseReason = falseReason;
    }

    public String getFalseReason() 
    {
        return falseReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clueId", getClueId())
            .append("createBy", getCreateBy())
            .append("subject", getSubject())
            .append("record", getRecord())
            .append("level", getLevel())
            .append("createTime", getCreateTime())
            .append("type", getType())
            .append("falseReason", getFalseReason())
            .toString();
    }
}
