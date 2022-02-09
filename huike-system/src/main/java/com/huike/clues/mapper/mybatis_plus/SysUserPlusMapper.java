package com.huike.clues.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.common.core.domain.entity.SysUser;

/**
 * 用户对象 sys_user mybatis——plus 数据持久层
 * @author WGL
 *
 */
@Repository
public interface SysUserPlusMapper extends BaseMapper<SysUser> {

}