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

/**
 * 字典数据表 sys_dict_data
 * 
 * 
 */
@Data
public class SysDictData {
	private static final long serialVersionUID = 1L;

	/** 字典编码 */
	@Excel(name = "字典编码", cellType = ColumnType.NUMERIC)
	@TableId(type = IdType.AUTO)
	private Long dictCode;

	/** 字典排序 */
	@Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
	private Long dictSort;

	/** 字典标签 */
	@Excel(name = "字典标签")
	private String dictLabel;

	/** 字典键值 */
	@Excel(name = "字典键值")
	private String dictValue;

	/** 字典类型 */
	@Excel(name = "字典类型")
	private String dictType;

	/** 样式属性（其他样式扩展） */
	private String cssClass;

	/** 表格字典样式 */
	private String listClass;

	/** 是否默认（Y是 N否） */
	@Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
	private String isDefault;

	/** 状态（0正常 1停用） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

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
