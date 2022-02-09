package com.huike.clues.domain;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.annotation.Excel;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 线索分配记录对象 tb_assign_record
 * @date 2021-04-19
 */
@TableName("tb_assign_record")
@ApiModel(value="线索分配记录对象",description="线索分配记录对象")
@Data
public class TbAssignRecord{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** id */
    @Excel(name = "assign_id")
    private Long assignId;

    /** 所属人用户id */
    @Excel(name = "所属人用户id")
    private Long userId;

    /** 所属人名称 */
    @Excel(name = "所属人名称")
    private String userName;

    /** 所属人所属组织 */
    @Excel(name = "所属人所属组织")
    private Long deptId;

    /** 是否当前最新分配人 */
    @Excel(name = "是否当前最新分配人")
    private String latest;

    @Excel(name = "类型0 线索 1 商机")
    private String type="0";
    
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

   
    @AllArgsConstructor
    @Getter
    public enum RecordType {
        /**
         * 线索
         */
        CLUES("0"),

        /**
         * 商机
         */
        BUSNIESS("1");

        private String value;

    }
}
