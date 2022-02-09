package com.huike.business.task;

import com.huike.business.domain.TbBusiness;
import com.huike.business.service.ITbBusinessService;
import com.huike.clues.domain.TbClue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ruleBusinessPoolTask")
public class RuleBusinessPoolTask {


    @Autowired
    private ITbBusinessService businessService;

    //线程池回收回调
    public void recovery(String id)
    {
        TbBusiness business= businessService.selectTbBusinessById(Long.valueOf(id));
        if(business!=null){
            businessService.updateStatus(business.getId(),
                    TbBusiness.StatusType.RECOVERY.getValue());
        }
        System.out.println("-------------recovery"+business );
    }


    //线索池告警回调
    public void warn(String id){
        TbBusiness business= businessService.selectTbBusinessById(Long.valueOf(id));
        if(business!=null){
//            tbClue.setStatus("3");
//            tbClueService.updateTbClue(tbClue);
        }
        System.out.println("-------------warn----"+business);
    }
}
