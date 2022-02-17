package com.huike.clues.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.common.annotation.Excel;

import java.util.Date;

public class ClueReportVo {

    private Long id;

    /** 客户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 渠道 */
    private String channel;

    /** 活动 */
    private String code;

    /** 活动详情 */
    private String info;

    /** 所属人 */
    private String owner;

    /** 所属部门 */
    private String deptId;

    /** 线索状态 */
    private String clueStatus;

    /** 线索创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date clueCreateTime;

    /** 转换商机时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessCreateTime;

    /** 商机状态 */
    private String businessStatus;

    /** 合同成交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractCreateTime;

    /** 订单金额*/
    private Double order;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Double getOrder() {
        return order;
    }

    public void setOrder(Double order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getClueStatus() {
        return clueStatus;
    }

    public void setClueStatus(String clueStatus) {
        this.clueStatus = clueStatus;
    }

    public Date getClueCreateTime() {
        return clueCreateTime;
    }

    public void setClueCreateTime(Date clueCreateTime) {
        this.clueCreateTime = clueCreateTime;
    }

    public Date getBusinessCreateTime() {
        return businessCreateTime;
    }

    public void setBusinessCreateTime(Date businessCreateTime) {
        this.businessCreateTime = businessCreateTime;
    }

    public Date getContractCreateTime() {
        return contractCreateTime;
    }

    public void setContractCreateTime(Date contractCreateTime) {
        this.contractCreateTime = contractCreateTime;
    }
}
