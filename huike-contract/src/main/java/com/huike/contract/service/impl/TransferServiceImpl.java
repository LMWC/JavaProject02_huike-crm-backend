package com.huike.contract.service.impl;

import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.service.ISysUserService;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.clues.utils.JobUtils;
import com.huike.common.constant.Constants;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.DateUtils;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransferServiceImpl implements ITransferService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;

    @Autowired
    private ITbRulePoolService rulePoolService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TbClueMapper clueMapper;


    @Autowired
    private TbBusinessMapper businessMapper;


}
