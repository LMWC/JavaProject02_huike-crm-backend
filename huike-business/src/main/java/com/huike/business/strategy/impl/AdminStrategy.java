package com.huike.business.strategy.impl;

import com.huike.business.domain.TbBusiness;
import com.huike.business.strategy.Rule;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRuleAssign;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
@Service("BusinessAdminStrategy")
public class AdminStrategy implements Rule {

    @Autowired
    private TbAssignRecordMapper  assignRecordMapper;

    @Autowired
    private SysUserMapper userMapper;


    private static SysUser ADMIN = new SysUser();

    @PostConstruct
    public void init() {
        ADMIN = userMapper.selectUserByName("admin");
    }

    /**
     * 转商机时的方法
     * @param business
     */
    @Override
    public Integer transforBusiness(TbBusiness business) {
        //默认分配给管理员
        TbAssignRecord tbAssignRecord =new TbAssignRecord();
        tbAssignRecord.setAssignId(business.getId());
        tbAssignRecord.setUserId(ADMIN.getUserId());
        tbAssignRecord.setUserName(ADMIN.getUserName());
        //这里是写死的admin所在部门的id
        SysUser admin = userMapper.selectUserByName(ADMIN.getUserName());
        tbAssignRecord.setDeptId(admin.getDeptId());
        tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
        tbAssignRecord.setCreateTime(DateUtils.getNowDate());
        tbAssignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
        business.setNextTime(null);
        return assignRecordMapper.insertAssignRecord(tbAssignRecord);
    }
}