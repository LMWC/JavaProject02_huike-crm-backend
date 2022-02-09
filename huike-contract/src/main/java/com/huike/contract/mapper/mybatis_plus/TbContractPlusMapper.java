package com.huike.contract.mapper.mybatis_plus;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.contract.domain.TbContract;

/**
 * mybatisPlus mapper层  TbContract 合同Mybatisplus数据持久层
 * @author WGL
 *
 */
@Repository
public interface TbContractPlusMapper extends BaseMapper<TbContract> {

}