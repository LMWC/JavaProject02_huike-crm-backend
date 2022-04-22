package com.huike.clues.strategy.impl;

import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRuleAssign;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.strategy.Rule;
import com.huike.common.core.domain.entity.SysUser;
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
@ConditionalOnProperty(name = "rule.clue.import", havingValue = "admin")
@Service("ClueAdminStrategy")
public class AdminClueStrategy implements Rule {

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;

    @Autowired
    private SysUserMapper userMapper;


    private static SysUser ADMIN = new SysUser();

    @PostConstruct
    public void init() {
        ADMIN = userMapper.selectUserByName("admin");
    }

    @Override
    public Boolean loadRule(TbClue clue) {
        try {
            TbAssignRecord tbAssignRecord = new TbAssignRecord();
            tbAssignRecord.setAssignId(clue.getId());
            tbAssignRecord.setUserId(SecurityUtils.getAdmin());
            tbAssignRecord.setUserName(ADMIN.getUserName());
            tbAssignRecord.setDeptId(ADMIN.getDeptId());
            tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
            tbAssignRecord.setCreateTime(clue.getCreateTime());
            assignRecordMapper.insertAssignRecord(tbAssignRecord);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

