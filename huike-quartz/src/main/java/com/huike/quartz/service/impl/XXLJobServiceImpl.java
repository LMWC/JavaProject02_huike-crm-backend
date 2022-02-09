package com.huike.quartz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huike.quartz.mapper.XXLJobMapper;
import com.huike.quartz.service.IXXLJobService;

@Service
public class XXLJobServiceImpl implements IXXLJobService {
	
	private String Tbclue_RECOVERY = "3";
	
	private String TbBusiness_RECOVERY = "3";

	@Autowired
	private XXLJobMapper xxlJobMapper;
	
	@Override
	public void recovery() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String nowString = sdf.format(now);
		//回收线索
		xxlJobMapper.resetNextTimeAndStatusOnClue(Tbclue_RECOVERY,nowString);
		//回收商机
		xxlJobMapper.resetNextTimeAndStatusOnBusiness(TbBusiness_RECOVERY,nowString);
	}
}