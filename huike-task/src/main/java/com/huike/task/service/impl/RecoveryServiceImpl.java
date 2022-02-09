package com.huike.task.service.impl;

import com.huike.task.mapper.RecoveryMapper;
import com.huike.task.service.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RecoveryServiceImpl  implements RecoveryService {

    @Autowired
    private RecoveryMapper mapper;

    private String Tbclue_RECOVERY = "3";

	private String TbBusiness_RECOVERY = "3";


    @Override
    public void recoveryBusiness() {
        mapper.resetNextTimeAndStatusOnBusiness(TbBusiness_RECOVERY, new Date());
    }

    @Override
    public void recoveryClue() {
        //回收线索
        mapper.resetNextTimeAndStatusOnClue(Tbclue_RECOVERY,new Date());
    }
}
