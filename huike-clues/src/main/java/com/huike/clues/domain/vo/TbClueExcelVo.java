package com.huike.clues.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;

public class TbClueExcelVo{


    /** 客户手机号 手机号(11位手机号，不可有空格) */
    @ExcelProperty(value = "手机号(11位手机号，不可有空格)",index = 0)
    private String phone;

    /** 渠道 */
    @ExcelProperty(value = "渠道来源",index = 1)
    private String channel;
    
    /** 活动编号 (来源于活动列表8位字母或数字)*/
    @ExcelProperty(value = "活动编号(来源于活动列表8位字母或数字)",index = 2)
    private String activityCode;


    /** "客户姓名 **/
    @ExcelProperty(value = "客户姓名",index = 3)
    private String name;
    
    /** 意向学科 */
    @ExcelProperty(value = "意向学科",index = 4)
    private String subject;

    /** 意向级别 */
    @ExcelProperty(value = "意向级别",index = 5)
    private String level;
    
    /** 性别 */
    @ExcelProperty(value = "性别",index = 6)
    private String sex;

    /** 年龄 */
    @ExcelProperty(value = "年龄",index = 7)
    private Long age;
    
    /** 微信 */
    @ExcelProperty(value = "微信",index = 8)
    private String weixin;

    /** qq */
    @ExcelProperty(value = "QQ",index = 9)
    private String qq;

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

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
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

	@Override
	public String toString() {
		return "TbClueExcelVo [phone=" + phone + ", channel=" + channel + ", activityCode=" + activityCode + ", name="
				+ name + ", subject=" + subject + ", level=" + level + ", sex=" + sex + ", age=" + age + ", weixin="
				+ weixin + ", qq=" + qq + "]";
	}
}
