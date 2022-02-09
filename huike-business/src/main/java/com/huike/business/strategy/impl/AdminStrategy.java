package com.huike.business.strategy.impl;

import com.huike.business.domain.TbBusiness;
import com.huike.business.strategy.Rule;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRuleAssign;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *  admin 处理策略
 *
 * 由admin来处理所有的线索导入和转商机的数据
 *
 * 全部导入到admin 统一由admin来处理所有的线索
 * exchange 转商机的时候统一转换到admin，再由admin来统一分片商机
 */
@ConditionalOnProperty(name = "rule.transfor", havingValue = "admin")
@Service
public class AdminStrategy implements Rule {

    @Autowired
    private TbAssignRecordMapper  assignRecordMapper;

    /**
     * 转商机时的方法
     * @param business
     */
    @Override
    public Integer transforBusiness(TbBusiness business) {
        //默认分配给管理员
        if(!SecurityUtils.getLoginUser().getUsername().equals("admin")) {
            TbAssignRecord tbAssignRecord =new TbAssignRecord();
            tbAssignRecord.setAssignId(business.getId());
            tbAssignRecord.setUserId(SecurityUtils.getAdmin());
            tbAssignRecord.setUserName("admin");
            //tbAssignRecord.setDeptId(SecurityUtils.);
            tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
            tbAssignRecord.setCreateTime(DateUtils.getNowDate());
            tbAssignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
            business.setNextTime(null);
            return assignRecordMapper.insertAssignRecord(tbAssignRecord);
        }else{
            return 0;
        }
    }
}