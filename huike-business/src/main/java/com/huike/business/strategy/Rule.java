package com.huike.business.strategy;

import com.huike.business.domain.TbBusiness;

/**
 * 线索转商机使用的规则
 */
public interface Rule {

    Integer transforBusiness(TbBusiness tbBusiness);
}
