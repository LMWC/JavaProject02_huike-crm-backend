package com.huike.task.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface RecoveryMapper {
    //线索回收
    @Update("update tb_clue set next_time=null, status=#{status} where end_time <= #{now} and status IN (1,2,4)")
    void resetNextTimeAndStatusOnClue(@Param("status")String status, @Param("now") Date date);

    //商机回收
    @Update("update tb_business set next_time=null, status=#{status} where end_time <= #{now} and status IN (1,2,4)")
    void resetNextTimeAndStatusOnBusiness(@Param("status")String tbBusiness_recovery,@Param("now") Date date);

}
