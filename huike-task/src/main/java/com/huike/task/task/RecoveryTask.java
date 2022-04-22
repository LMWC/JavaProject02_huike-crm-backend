package com.huike.task.task;

import com.huike.task.service.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecoveryTask {

    @Autowired
    private RecoveryService recoveryService;

    /**
     * 回收线索
     * 生产环境 每天6点执行
     * 测试环境 每10秒执行一次 0/10 * * * * ?
     */
    @Scheduled(cron = "0 0 6 * * ?")//每天早上6点执行
    public void recoveryClue() {
        recoveryService.recoveryClue();
    }

    /**
     * 回收商机
     * 生产环境 每天6点执行
     * 测试环境 每10秒执行一次 0/10 * * * * ?
     */
    @Scheduled(cron = "0 0 6 * * ?")//每天早上6点执行
    public void recoveryBusiness() {
        recoveryService.recoveryBusiness();
    }

}