package com.huike.clues.service;

import java.util.List;
import java.util.Map;

import com.huike.clues.domain.TbActivity;

/**
 * 活动管理Service接口
 * @date 2021-04-01
 */
public interface ITbActivityService 
{
    /**
     * 查询活动管理
     * 
     * @param id 活动管理ID
     * @return 活动管理
     */
    public TbActivity selectTbActivityById(Long id);



    public TbActivity selectTbActivityByCode(String code);

    /**
     * 查询活动管理列表
     * 
     * @param tbActivity 活动管理
     * @return 活动管理集合
     */
    public List<TbActivity> selectTbActivityList(TbActivity tbActivity);

    /**
     * 新增活动管理
     * 
     * @param tbActivity 活动管理
     * @return 结果
     */
    public int insertTbActivity(TbActivity tbActivity);

    /**
     * 修改活动管理
     * 
     * @param tbActivity 活动管理
     * @return 结果
     */
    public int updateTbActivity(TbActivity tbActivity);

    /**
     * 批量删除活动管理
     * 
     * @param ids 需要删除的活动管理ID
     * @return 结果
     */
    public int deleteTbActivityByIds(Long[] ids);

    /**
     * 删除活动管理信息
     * 
     * @param id 活动管理ID
     * @return 结果
     */
    public int deleteTbActivityById(Long id);

    public Map getCountByStatus();

    void loadAllActivityCode();
}
