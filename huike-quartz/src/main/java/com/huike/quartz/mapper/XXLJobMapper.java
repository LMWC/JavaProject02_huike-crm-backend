package com.huike.quartz.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface XXLJobMapper {

	//回收线索
	void resetNextTimeAndStatusOnClue(@Param("status") String status,@Param("now")String date);

	//回收商机
	void resetNextTimeAndStatusOnBusiness(@Param("status") String status,@Param("now")String date);

}
