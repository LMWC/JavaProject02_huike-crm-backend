package com.huike.common.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 部门表 sys_dept
 * 
 * 
 */
@Data
public class SysDept{
	private static final long serialVersionUID = 1L;

	/** 部门ID */
	@TableId(type = IdType.AUTO)
	private Long deptId;

	/** 父部门ID */
	private Long parentId;

	/** 祖级列表 */
	private String ancestors;

	/** 部门名称 */
	private String deptName;

	/** 显示顺序 */
	private String orderNum;

	/** 负责人 */
	private String leader;

	/** 联系电话 */
	private String phone;

	/** 邮箱 */
	private String email;

	/** 部门状态:0正常,1停用 */
	private String status;

	/** 删除标志（0代表存在 2代表删除） */
	private String delFlag;

	/** 父部门名称 */
	@TableField(exist = false)
	private String parentName;

	// 用户列表
	@TableField(exist = false)
	private List<SysUser> users = new ArrayList<SysUser>();

	/** 子部门 */
	@TableField(exist = false)
	private List<SysDept> children = new ArrayList<SysDept>();

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
}
