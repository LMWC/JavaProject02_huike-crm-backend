package com.huike.common.core.domain.entity;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;
import com.huike.common.annotation.Excel.ColumnType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色表 sys_role
 * 
 * 
 */
@Data
@NoArgsConstructor
public class SysRole {
	private static final long serialVersionUID = 1L;

	/** 角色ID */
	@Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
	@TableId(type = IdType.AUTO)
	private Long roleId;

	/** 角色名称 */
	@Excel(name = "角色名称")
	private String roleName;

	/** 角色权限 */
	@Excel(name = "角色权限")
	private String roleKey;

	/** 角色排序 */
	@Excel(name = "角色排序")
	private String roleSort;

	/** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
	@Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
	private String dataScope;

	/** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
	private boolean menuCheckStrictly;

	/** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
	private boolean deptCheckStrictly;

	/** 角色状态（0正常 1停用） */
	@Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** 删除标志（0代表存在 2代表删除） */
	private String delFlag;

	/** 用户是否存在此角色标识 默认不存在 */
	private boolean flag = false;

	/** 菜单组 */
	private Long[] menuIds;

	/** 部门组（数据权限） */
	private Long[] deptIds;

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
	
	public SysRole(Long roleId) {
        this.roleId = roleId;
    }

	public boolean isAdmin() {
		return isAdmin(this.roleId);
	}

	public static boolean isAdmin(Long roleId) {
		return roleId != null && 1L == roleId;
	}

}
