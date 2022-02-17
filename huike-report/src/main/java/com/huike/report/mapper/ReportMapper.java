package com.huike.report.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huike.clues.domain.vo.IndexStatisticsVo;

/**
 * 首页统计分析的Mapper
 * @author Administrator
 *
 */
public interface ReportMapper {
	
    
    /**
     * 获取基础信息
     * @param request
     * @param now
     * @param username
     * @return
     */
	public Map getBaseData(@Param("indexVo")IndexStatisticsVo request, 
			@Param("now")String now,@Param("username")String username);

	/**
	 * 获取今日简报数据
	 * @param request
	 * @param now
	 * @param username
	 * @return
	 */
	public Map getTodayData(@Param("indexVo")IndexStatisticsVo request, 
			@Param("now")String now,@Param("username")String username);


	/**
	 * 获取待办任务
	 * @param request
	 * @param now
	 * @param username
	 * @return
	 */
	public Map getTodoData(@Param("indexVo")IndexStatisticsVo request, 
			@Param("now")String now,@Param("username")String username);


}
