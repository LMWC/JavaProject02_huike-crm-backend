package com.huike.clues.utils;

import java.util.Calendar;
import java.util.Date;

import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.service.ITbRulePoolService;
import com.huike.common.utils.spring.SpringUtils;

/**
 * 获取结束时间的工具类
 * @author 86150
 *
 */
public class HuiKeCrmDateUtils {
	
	/**
	 * 根据规则获取结束时间
	 * @param tbAssignRecord
	 * @return
	 */
	public static Date getEndDateByRule(TbAssignRecord tbAssignRecord){
        ITbRulePoolService rulePoolService = SpringUtils.getBean(ITbRulePoolService.class);
        TbRulePool rulePool= rulePoolService.selectTbRulePoolByType(tbAssignRecord.getType());
        if(rulePool==null){
            return null;
        }
        //回收规则
        Date recoveryDate=getDate(rulePool.getLimitTime().intValue(),
                rulePool.getLimitTimeType(), tbAssignRecord.getCreateTime());
        return recoveryDate;
    }
	
	public static Date getDate(int time, String type, Date now){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        if(TbRulePool.LimitTimeType.HOUR.getValue().equals(type)){
            cal.add(Calendar.HOUR, time);
        }else if(TbRulePool.LimitTimeType.DAY.getValue().equals(type)){
            cal.add(Calendar.DATE, time);
        }else if(TbRulePool.LimitTimeType.WEEK.getValue().equals(type)){
            cal.add(Calendar.DAY_OF_WEEK, time);
        }
        return cal.getTime();
    }
}
