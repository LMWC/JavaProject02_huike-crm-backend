package com.huike.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.common.annotation.Excel;

import java.util.Date;

public class BusinessTrackVo {

    private Long businessId;

    /** 客户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 渠道 */
    private Long channelId;

    /** 活动id */
    private Long activityId;

    /** 省 */
    @Excel(name = "省")
    private String provinces;

    /** 区 */
    @Excel(name = "区")
    private String city;

    /** 男或者女 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 微信 */
    private String weixin;

    /** qq */
    private String qq;

    /** 意向等级 */
    private String level;

    /** 意向学科 */
    private String subject;

    /** 课程 */
    private Long courseId;

    /** 职业 */
    private String occupation;

    /** 学历 */
    private String education;

    /** 在职情况 */
    private String job;

    /** 薪资 */
    private String salary;

    /** 专业 */
    private String major;

    /** 希望薪资 */
    private String expectedSalary;

    /** 学习原因 */
    private String reasons;

    /** 职业计划 */
    private String plan;

    /** 计划时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planTime;

    /** 其他意向 */
    @Excel(name = "其他意向")
    private String otherIntention;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;

    //沟通备注
    private String remark;

    //沟通重点
    private String keyItems;


    /** 沟通纪要 */
    private String record;

    /** 跟进状态 */
    private String trackStatus;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getOtherIntention() {
        return otherIntention;
    }

    public void setOtherIntention(String otherIntention) {
        this.otherIntention = otherIntention;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKeyItems() {
        return keyItems;
    }

    public void setKeyItems(String keyItems) {
        this.keyItems = keyItems;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    @Override
    public String toString() {
        return "BusinessTrackVo{" +
                "businessId=" + businessId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", channelId='" + channelId + '\'' +
                ", activityId=" + activityId +
                ", provinces='" + provinces + '\'' +
                ", city='" + city + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", weixin='" + weixin + '\'' +
                ", qq='" + qq + '\'' +
                ", level='" + level + '\'' +
                ", subject='" + subject + '\'' +
                ", courseId=" + courseId +
                ", occupation='" + occupation + '\'' +
                ", education='" + education + '\'' +
                ", job='" + job + '\'' +
                ", salary='" + salary + '\'' +
                ", major='" + major + '\'' +
                ", expectedSalary='" + expectedSalary + '\'' +
                ", reasons='" + reasons + '\'' +
                ", plan='" + plan + '\'' +
                ", planTime=" + planTime +
                ", otherIntention='" + otherIntention + '\'' +
                ", nextTime=" + nextTime +
                ", remark='" + remark + '\'' +
                ", keyItems='" + keyItems + '\'' +
                ", record='" + record + '\'' +
                ", trackStatus='" + trackStatus + '\'' +
                '}';
    }
}
