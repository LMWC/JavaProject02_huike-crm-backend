package com.huike.clues.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.common.core.domain.entity.SysRole;

/**
 * 角色表 sys_role mybatis——plus 数据持久层
 * @author WGL
 *
 */
@Repository
public interface SysRolePlusMapper extends BaseMapper<SysRole> {

}
