package com.huike.clues.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程管理对象 tb_course
 *
 * @author WGL
 * @date 2021-04-02
 */
@Data
@ApiModel(value = "课程对象", description = "课程类")
public class TbCourse {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	/** 课程id */
	@ApiModelProperty(value = "id", name = "课程Id", example = "1")
	private Long id;

	/** 课程名称 */
	@Excel(name = "课程名称")
	@ApiModelProperty(value = "name", name = "课程名称", example = "javaSE")
	private String name;

	/** 课程编码 */
	@ApiModelProperty(value = "code", name = "课程编码")
	private String code;

	/** 课程学科 */
	@ApiModelProperty(value = "subject", name = "课程学科")
	private String subject;

	/** 价格 */
	@Excel(name = "价格")
	@ApiModelProperty(value = "price", name = "价格", example = "500")
	private Integer price;

	/** 适用人群 */
	@Excel(name = "适用人群")
	@ApiModelProperty(value = "applicablePerson", name = "适用人群", example = "0基础")
	private String applicablePerson;

	/** 课程描述信息 */
	@Excel(name = "课程描述信息")
	@ApiModelProperty(value = "info", name = "课程描述信息", example = "javaSE入门课程")
	private String info;
	
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