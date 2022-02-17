package com.huike.clues.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

/**
 * 线索分配记录对象 tb_assign_record
 * @date 2021-04-19
 */
public class TbAssignRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** id */
    @Excel(name = "assign_id")
    private Long assignId;

    /** 所属人用户id */
    @Excel(name = "所属人用户id")
    private Long userId;

    /** 所属人名称 */
    @Excel(name = "所属人名称")
    private String userName;

    /** 所属人所属组织 */
    @Excel(name = "所属人所属组织")
    private Long deptId;

    /** 是否当前最新分配人 */
    @Excel(name = "是否当前最新分配人")
    private String latest;

    @Excel(name = "类型0 线索 1 商机")
    private String type="0";

   // private String status;

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setLatest(String latest) 
    {
        this.latest = latest;
    }

    public String getLatest() 
    {
        return latest;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("assignId", getAssignId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("deptId", getDeptId())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("latest", getLatest())
            .append("type", getType())
            .toString();
    }

    public enum RecordType {
        /**
         * 线索
         */
        CLUES("0"),

        /**
         * 商机
         */
        BUSNIESS("1");


        private String value;

        private RecordType(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }
}
