package com.huike.clues.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysConfig;

/**
 * 参数配置表 sys_config mybatis——plus 数据持久层
 * @author WGL
 *
 */
@Repository
public interface SysConfigPlusMapper extends BaseMapper<SysConfig> {

}