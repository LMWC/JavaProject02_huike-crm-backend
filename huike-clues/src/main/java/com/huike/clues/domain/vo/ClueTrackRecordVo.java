package com.huike.clues.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ClueTrackRecordVo {

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

    /** 客户姓名 */
    private String name;

    /** 1 男 0 女 */
    private String sex;

    /** 微信 */
    private String weixin;

    /** qq */
    private String qq;

    private Integer age;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getClueId() {
        return clueId;
    }

    public void setClueId(Long clueId) {
        this.clueId = clueId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFalseReason() {
        return falseReason;
    }

    public void setFalseReason(String falseReason) {
        this.falseReason = falseReason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }
}
