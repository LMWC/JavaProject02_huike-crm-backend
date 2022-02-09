package com.huike.business.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.business.domain.TbBusiness;

/**
 * mybatisPlus mapper层  TbBusiness 商机Mybatisplus数据持久层
 * @author WGL
 *
 */
@Repository
public interface TbBusinessPlusMapper extends BaseMapper<TbBusiness> {

}