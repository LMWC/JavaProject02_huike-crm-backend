package com.huike.clues.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.common.core.domain.entity.SysDept;

/**
 * 部门表 sys_dept mybatis——plus 数据持久层
 * @author WGL
 *
 */
@Repository
public interface SysDeptPlusMapper extends BaseMapper<SysDept> {

}