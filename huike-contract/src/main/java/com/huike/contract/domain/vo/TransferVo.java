package com.huike.contract.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TransferVo {

    private Long userId;
    private String userName;
    private String phonenumber;
    private int clueNum;
    private int businessNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getClueNum() {
        return clueNum;
    }

    public void setClueNum(int clueNum) {
        this.clueNum = clueNum;
    }

    public int getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(int businessNum) {
        this.businessNum = businessNum;
    }
}
