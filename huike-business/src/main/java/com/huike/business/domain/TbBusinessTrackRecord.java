package com.huike.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商机跟进记录对象 tb_business_track_record
 * @date 2021-04-28
 */
public class TbBusinessTrackRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务id */
    private Long id;

    /** 线索id */
    @Excel(name = "线索id")
    private Long businessId;

    /** 沟通重点 */
    @Excel(name = "沟通重点")
    private String keyItems;

    private List<String> keys =new ArrayList<>();

    /** 沟通纪要 */
    @Excel(name = "沟通纪要")
    private String record;

    /** 跟进状态 */
    @Excel(name = "跟进状态")
    private String trackStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;


    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public void setKeyItems(String keyItems)
    {
        this.keyItems = keyItems;
    }

    public String getKeyItems() 
    {
        return keyItems;
    }
    public void setRecord(String record) 
    {
        this.record = record;
    }

    public String getRecord() 
    {
        return record;
    }
    public void setTrackStatus(String trackStatus) 
    {
        this.trackStatus = trackStatus;
    }

    public String getTrackStatus() 
    {
        return trackStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("businessId", getBusinessId())
            .append("createBy", getCreateBy())
            .append("keyItems", getKeyItems())
            .append("record", getRecord())
            .append("createTime", getCreateTime())
            .append("trackStatus", getTrackStatus())
            .toString();
    }
}
