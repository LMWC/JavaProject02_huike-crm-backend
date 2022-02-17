package com.huike.clues.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.utils.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

/**
 * 线索管理对象 tb_clue
 * @date 2021-04-02
 */
public class TbClue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 线索id
     */
    private Long id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 渠道 */
    @Excel(name = "渠道")
    private String channel;

    /** 活动id */
    @Excel(name = "活动id")
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 活动名称 */
    private String activityInfo;

    /** 1 男 0 女 */
    @Excel(name = "1 男 0 女")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 微信 */
    @Excel(name = "微信")
    private String weixin;

    /** qq */
    @Excel(name = "qq")
    private String qq;

    /** 意向等级 */
    @Excel(name = "意向等级")
    private String level;

    /** 意向学科 */
    @Excel(name = "意向学科")
    private String subject;

    /** 状态(已分配1  进行中2  回收3  伪线索4) */
    @Excel(name = "状态(已分配1  进行中2  回收3  伪线索4)")
    private String status;

    /** 分配人 */
    @Excel(name = "分配人")
    private String assignBy;

    /** 分配时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "分配时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date assignTime;

    /** 所属人 */
    @Excel(name = "所属人")
    private String owner;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ownerTime;


    /** 伪线索失败次数(最大数3次) */
    @Excel(name = "伪线索失败次数(最大数3次)")
    private int falseCount;

    /** 下次跟进时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    
    
    public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/** 创建人id */
    @Excel(name = "创建人id")
    private Long userId;

    @JsonIgnore
    private String createTimeStr;

    private String transfer; //是否转派 默认0
    
	public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getCreateTimeStr() {
        if(getCreateTime()!=null){
            this.createTimeStr = DateUtils.dateTime(getCreateTime());
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getChannel()
    {
        return channel;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setWeixin(String weixin)
    {
        this.weixin = weixin;
    }

    public String getWeixin()
    {
        return weixin;
    }
    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getQq()
    {
        return qq;
    }
    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setAssignBy(String assignBy)
    {
        this.assignBy = assignBy;
    }

    public String getAssignBy()
    {
        return assignBy;
    }
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }

    public Date getAssignTime()
    {
        return assignTime;
    }

    public int getFalseCount() {
        return falseCount;
    }

    public void setFalseCount(int falseCount) {
        this.falseCount = falseCount;
    }

    public void setNextTime(Date nextTime)
    {
        this.nextTime = nextTime;
    }

    public Date getNextTime()
    {
        return nextTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getOwnerTime() {
        return ownerTime;
    }

    public void setOwnerTime(Date ownerTime) {
        this.ownerTime = ownerTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("phone", getPhone())
                .append("channel", getChannel())
                .append("activityId", getActivityId())
                .append("sex", getSex())
                .append("age", getAge())
                .append("weixin", getWeixin())
                .append("qq", getQq())
                .append("level", getLevel())
                .append("subject", getSubject())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createTimeStr", getCreateTimeStr())
                .append("userId", getUserId())
                .append("createBy", getCreateBy())
                .append("owner", getOwner())
                .append("assignBy", getAssignBy())
                .append("assignTime", getAssignTime())
                .append("falseCount", getFalseCount())
                .append("nextTime", getNextTime())
                .toString();
    }



    public enum StatusType{


        UNFOLLOWED("待跟进","1"),
        FOLLOWING("跟进中","2"),
        RECOVERY("回收","3"),
        FALSE("伪线索/踢回公海","4"),
        DELETED("删除","5"),
        TOBUSINESS("转换商机","6"),
        TOCUSTOMER("转换客户","7");

        private String name;
        private String value;

        private StatusType(String name,String value)
        {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ImportDictType
    {

        CHANNEL("渠道来源","clues_item"),

        SUBJECT("意向学科","course_subject"),

        LEVEL("意向学科","clues_level"),

        SEX("性别","sys_user_sex");


        private String name;
        private String dictType;

        private ImportDictType(String name, String dictType)
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


