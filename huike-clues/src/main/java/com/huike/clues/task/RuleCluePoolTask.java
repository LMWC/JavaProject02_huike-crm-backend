package com.huike.clues.task;

import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.service.ITbAssignRecordService;
import com.huike.clues.service.ITbClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 线索池规则
 */
@Component("ruleCluesPoolTask")
public class RuleCluePoolTask {


    @Autowired
    private ITbClueService tbClueService;


    //线程池回收回调
    public void recovery(String id)
    {
        TbClue tbClue= tbClueService.selectTbClueById(Long.valueOf(id));
        if(tbClue!=null){
           tbClueService.updateStatus(tbClue.getId(), TbClue.StatusType.RECOVERY.getValue());
        }
        System.out.println("-------------recovery"+tbClue );
    }


    //线索池告警回调
    public void warn(String id){
        TbClue tbClue= tbClueService.selectTbClueById(Long.valueOf(id));
        if(tbClue!=null){
//            tbClue.setStatus("3");
//            tbClueService.updateTbClue(tbClue);
        }
        System.out.println("-------------warn"+tbClue);
    }

}
