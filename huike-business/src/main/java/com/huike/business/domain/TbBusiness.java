package com.huike.business.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huike.clues.domain.TbClue;
import com.huike.common.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 线索对象 tb_business
 * 
 * @date 2021-04-28
 */
@TableName("tb_business")
@ApiModel(value="商机对象",description="商机类")
@Data
public class TbBusiness extends TbClue {
	private static final long serialVersionUID = 1L;

	/** 省 */
	@Excel(name = "省")
	@ApiModelProperty(value="省",name="provinces",example="北京")
	private String provinces;

	/** 区 */
	@Excel(name = "区")
	@ApiModelProperty(value="区",name="city",example="昌平")
	private String city;

	@ApiModelProperty(value="区域",name="region",example="回龙观街道")
	@TableField(exist = false)
	private String region;

	/** 课程 */
	@Excel(name = "课程")
	@ApiModelProperty(value="课程id",name="courseId",example="1")
	private Long courseId;

	/** 职业 */
	@Excel(name = "职业")
	@ApiModelProperty(value="职业",name="occupation",example="学生")
	private String occupation;

	/** 学历 */
	@Excel(name = "学历")
	@ApiModelProperty(value="学历",name="education",example="大专")
	private String education;

	/** 在职情况 */
	@Excel(name = "在职情况")
	@ApiModelProperty(value="在职情况",name="job",example="在职")
	private String job;

	/** 薪资 */
	@Excel(name = "薪资")
	@ApiModelProperty(value="薪资",name="salary",example="20000")
	private String salary;

	/** 专业 */
	@Excel(name = "专业")
	@ApiModelProperty(value="专业",name="major",example="计算机")
	private String major;

	/** 希望薪资 */
	@Excel(name = "希望薪资")
	@ApiModelProperty(value="希望薪资",name="expectedSalary",example="25000")
	private String expectedSalary;

	/** 学习原因 */
	@Excel(name = "学习原因")
	@ApiModelProperty(value="学习原因",name="reasons",example="就想搞钱")
	private String reasons;

	/** 职业计划 */
	@Excel(name = "职业计划")
	@ApiModelProperty(value="职业计划",name="plan",example="搞钱")
	private String plan;

	/** 计划时间 */
	@ApiModelProperty(value="计划时间",name="planTime",example="2021-05-05 11:11:11")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Excel(name = "计划时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date planTime;

	/** 其他意向 */
	@Excel(name = "其他意向")
	private String otherIntention;

	/** 下次跟进时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Excel(name = "下次跟进时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date nextTime;

	/** 状态(已分配1 进行中2 回收3) */
	@Excel(name = "状态(已分配1  进行中2  回收3 完成4)")
	private String status;

	// 转换使用
	private Long clueId;

	// 开始时间
	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
	private String beginCreateTime;

	// 结束时间
	@TableField(exist = false)
	private String endCreateTime;

}