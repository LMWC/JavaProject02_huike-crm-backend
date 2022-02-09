package com.huike.clues.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;
import com.huike.common.annotation.Excels;
import com.huike.common.annotation.Excel.ColumnType;
import com.huike.common.annotation.Excel.Type;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.core.dto.PageRequestDto;

import lombok.Data;

/**
 *   用户DTO
 * @author WGL
 *
 */
@Data
public class SysUserDto extends PageRequestDto{
	
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
	@TableId(type = IdType.AUTO)
	private Long userId;

	/** 部门ID */
	@Excel(name = "部门编号", type = Type.IMPORT)
	private Long deptId;

	/** 用户账号 */
	@Excel(name = "登录名称")
	private String userName;

	/** 用户昵称 */
	@Excel(name = "用户名称")
	private String nickName;

	/** 用户邮箱 */
	@Excel(name = "用户邮箱")
	private String email;

	/** 手机号码 */
	@Excel(name = "手机号码")
	private String phonenumber;

	/** 用户性别 */
	@Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
	private String sex;

	/** 用户头像 */
	private String avatar;

	/** 密码 */
	private String password;

	/** 盐加密 */
	@TableField(exist = false)
	private String salt;

	/** 帐号状态（0正常 1停用） */
	@Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** 删除标志（0代表存在 2代表删除） */
	private String delFlag;

	/** 最后登录IP */
	@Excel(name = "最后登录IP", type = Type.EXPORT)
	private String loginIp;

	/** 最后登录时间 */
	@Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date loginDate;

	/** 部门对象 */
	@Excels({ @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
			@Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT) })
	@TableField(exist = false)
	private SysDept dept;

	/** 角色对象 */
	@TableField(exist = false)
	private List<SysRole> roles;

	/** 角色组 */
	@TableField(exist = false)
	private Long[] roleIds;
	
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
		
	public boolean isAdmin() {
		return isAdmin(this.userId);
	}
	
	public static boolean isAdmin(Long userId) {
		return userId != null && 1l == userId;
	}
	
}