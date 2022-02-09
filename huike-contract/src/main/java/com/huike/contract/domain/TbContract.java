package com.huike.contract.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 合同对象 tb_contract
 * 
 * @date 2021-05-24
 */
@Data
public class TbContract {
	private static final long serialVersionUID = 1L;

	/** 合同id */
	@TableId(type = IdType.AUTO)
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

	private String channel; // 渠道

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

	// 课程价格
	private float coursePrice;

	// 活动折扣类型
	private String discountType;
	
	@TableField(exist = false)
	private float order;

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;

	private Long businessId;

	private Long deptId;
	
	private String createBy;
	
	/** 搜索值 */
	@TableField(exist = false)
	@JsonIgnore
	private String searchValue;

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
	
	@AllArgsConstructor
	@Getter
	public enum AssigneeUser {

		Assignee1(137L, "xszgtest123", "销售主管"),

		Assignee2(152L, "zjltest", "总经理"),

		Assignee3(153L, "caiwuzhuguan", "财务主管");

		private Long id;
		private String name;
		private String desc;

	}

}
