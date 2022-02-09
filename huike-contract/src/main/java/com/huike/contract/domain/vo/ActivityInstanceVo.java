package com.huike.contract.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActivityInstanceVo {

    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String status ="2";


    private String rejectionReasons;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rejectionTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(String rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }


    public Date getRejectionTime() {
        return rejectionTime;
    }

    public void setRejectionTime(Date rejectionTime) {
        this.rejectionTime = rejectionTime;
    }

    public enum ActivityType
    {

        STEP1("销售专员添加合同","1"),

        STEP2("销售主管审核","2"),

        STEP3("总经理审批","3"),

        STEP4("财务审批","4");



        private String name;
        private String dictType;

        private ActivityType(String name, String dictType)
        {
            this.name = name;
            this.dictType = dictType;
        }

        public String getName() {
            return name;
        }

        public String getDictType() {
            return dictType;
        }

    }

}
