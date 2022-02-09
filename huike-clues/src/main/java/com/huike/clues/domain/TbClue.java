package com.huike.clues.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;
import com.huike.common.utils.DateUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 线索管理对象 tb_clue
 * @date 2021-04-02
 */
@TableName("tb_clue")
@ApiModel(value="线索对象",description="线索类")
@Data
public class TbClue{

	private static final long serialVersionUID = 1L;

    /**
     * 线索id
     */
    @ApiModelProperty(value="id",name="主键ID",example="1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    @ApiModelProperty(value="name",name="客户姓名",example="张三")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    @ApiModelProperty(value="phone",name="手机号",example="150111111111")
    private String phone;

    /** 渠道 */
    @Excel(name = "渠道")
    @ApiModelProperty(value="channel",name="渠道",example="线上推广")
    private String channel;

    /** 活动id */
    @Excel(name = "活动id")
    @ApiModelProperty(value="activityId",name="活动ID",example="1")
    private Long activityId;

    /** 活动名称 */
    @ApiModelProperty(value="activityName",name="活动名称",example="北京马边成直播线上推广活动")
    @TableField(exist = false)
    private String activityName;

    /** 活动名称 */
    @ApiModelProperty(value="activityInfo",name="活动信息",example="马边成李传播线下推广javaEE就业课程")
    @TableField(exist = false)
    private String activityInfo;

    /** 1 男 0 女 */
    @Excel(name = "1 男 0 女")
    @ApiModelProperty(value="sex",name="性别1 男 0 女",example="1")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    @ApiModelProperty(value="age",name="年龄",example="20")
    private Integer age;

    /** 微信 */
    @Excel(name = "微信")
    @ApiModelProperty(value="weixin",name="微信",example="wx511111")
    private String weixin;

    /** qq */
    @Excel(name = "qq")
    @ApiModelProperty(value="qq",name="QQ",example="987654321")
    private String qq;

    /** 意向等级 */
    @Excel(name = "意向等级")
    @ApiModelProperty(value="level",name="意向等级")
    private String level;

    /** 意向学科 */
    @Excel(name = "意向学科")
    @ApiModelProperty(value="level",name="意向等级")
    private String subject;

    /** 状态(已分配1  进行中2  回收3  伪线索4) */
    @Excel(name = "状态(已分配1  进行中2  回收3  伪线索4)")
    @ApiModelProperty(value="status",name="状态(已分配1  进行中2  回收3  伪线索4)",example="1")
    private String status;

    /** 分配人 */
    @Excel(name = "分配人")
    @ApiModelProperty(value="assignBy",name="分配人")
    @TableField(exist = false)
    private String assignBy;

    /** 分配时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "分配时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value="assignTime",name="分配时间")
    @TableField(exist = false)
    private Date assignTime;

    /** 所属人 */
    @Excel(name = "所属人")
    @ApiModelProperty(value="owner",name="所属人")
    @TableField(exist = false)
    private String owner;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date ownerTime;


    /** 伪线索失败次数(最大数3次) */
    @Excel(name = "伪线索失败次数(最大数3次)")
    @TableField(exist = false)
    private int falseCount;

    /** 下次跟进时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value="nextTime",name="下次根进时间")
    private Date nextTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    
    
    /** 部门 */
    @ApiModelProperty(value="deptId",name="部门ID")
    @TableField(exist = false)
    private Integer deptId;
    
    /** 部门名 */
    @ApiModelProperty(value="deptName",name="部门名称")
    @TableField(exist = false)
    private String deptName;
    
    /** 下属所有部门 */
    @ApiModelProperty(value="deptIds",name="下属所有部门")
    @TableField(exist = false)
    private List<Integer> deptIds;
    
	/** 搜索值 */
	@TableField(exist = false)
	@JsonIgnore
	private String searchValue;

	@TableField(exist = false)
	/** 创建者 */
	private String createBy;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@TableField(exist = false)
	/** 更新者 */
	private String updateBy;

	@TableField(exist = false)
	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@TableField(exist = false)
	/** 备注 */
	@JsonIgnore
	private String remark;

	/** 请求参数 */
	@TableField(exist = false)
	@JsonIgnore
	private Map<String, Object> params;
    

	/** 创建人id */
    @Excel(name = "创建人id")
    @TableField(exist = false)
    private Long userId;

    @JsonIgnore
    @TableField(exist = false)
    private String createTimeStr;

    private String transfer; //是否转派 默认0
    
    public String getCreateTimeStr() {
        if(getCreateTime()!=null){
            this.createTimeStr = DateUtils.dateTime(getCreateTime());
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    @Getter
    @AllArgsConstructor
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

    }

    @Getter
    @AllArgsConstructor
    public enum ImportDictType{

        CHANNEL("渠道来源","clues_item"),

        SUBJECT("意向学科","course_subject"),

        LEVEL("意向学科","clues_level"),

        SEX("性别","sys_user_sex");

        private String name;
        private String dictType;

    }

}