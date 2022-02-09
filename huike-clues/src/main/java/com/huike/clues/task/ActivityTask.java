package com.huike.clues.task;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.service.ITbActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 */
@Component("activityTask")
public class ActivityTask {

    @Autowired
    ITbActivityService tbActivityService;

    //活动结束任务调用
    public void finish(String id)
    {
        TbActivity tbActivity= tbActivityService.selectTbActivityById(Long.parseLong(id));
        tbActivity.setStatus("4");
        tbActivityService.updateTbActivity(tbActivity);
    }
}
