package com.huike.report.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActivityStatisticsVo {

    private String channel; //渠道
    private String name;  //活动名称
    private String info; //活动简介
    private String code; //活动编码
    private Integer cluesNum=0;  //线索总数
    private Integer falseCluesNum=0;  //伪线索数目
    private Integer businessNum=0;  //转换商机数目
    private Integer customersNum=0;  //转换客户数目
    private Double amount; //成交金额
    private Double cost; //活动成本
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //创建时间

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getCluesNum() {
        return cluesNum;
    }

    public void setCluesNum(Integer cluesNum) {
        this.cluesNum = cluesNum;
    }

    public Integer getFalseCluesNum() {
        return falseCluesNum;
    }

    public void setFalseCluesNum(Integer falseCluesNum) {
        this.falseCluesNum = falseCluesNum;
    }

    public Integer getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(Integer businessNum) {
        this.businessNum = businessNum;
    }

    public Integer getCustomersNum() {
        return customersNum;
    }

    public void setCustomersNum(Integer customersNum) {
        this.customersNum = customersNum;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
