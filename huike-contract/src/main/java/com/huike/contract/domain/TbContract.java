package com.huike.contract.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 合同对象 tb_contract
 * @date 2021-05-24
 */
public class TbContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同id */
    private String id;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String name;

    /** 意向学科 */
    @Excel(name = "学科")
    private String subject;

    private String channel; //渠道

    /** 活动信息 */
    @Excel(name = "活动信息")
    private Long activityId;

    /** 课程id */
    @Excel(name = "课程id")
    private Long courseId;

    /** 状态(待审核0，已完成1，已驳回2) */
    @Excel(name = "状态(待审核0，已完成1，已驳回2)")
    private String status;



    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;


   //课程价格
    private float coursePrice;

    //活动折扣类型
    private String discountType;

    private float order;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    private Long  businessId;

    private Long deptId;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public float getOrder() {
        return order;
    }

    public void setOrder(float order) {
        this.order = order;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public float getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(float coursePrice) {
        this.coursePrice = coursePrice;
    }

//    public float getActivityDiscount() {
//        return activityDiscount;
//    }
//
//    public void setActivityDiscount(float activityDiscount) {
//        this.activityDiscount = activityDiscount;
//    }


    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo() 
    {
        return contractNo;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSubject(String subject) 
    {
        this.subject = subject;
    }

    public String getSubject() 
    {
        return subject;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contractNo", getContractNo())
            .append("phone", getPhone())
            .append("order", getOrder())
            .append("name", getName())
            .append("subject", getSubject())
            .append("activityId", getActivityId())
            .append("courseId", getCourseId())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("fileName", getFileName())
            .toString();
    }

    public enum AssigneeUser
    {


        Assignee1(137L, "xszgtest123","销售主管"),

        Assignee2(152L,"zjltest","总经理"),

        Assignee3(153L,"caiwuzhuguan","财务主管");




        private Long id;
        private String name;
        private String desc;

        public String getDesc() {
            return desc;
        }

        private AssigneeUser(Long id, String name, String desc)
        {
            this.id = id;
            this.name = name;
            this.desc = desc;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
