package com.huike.clues.service;

import java.util.List;
import com.huike.clues.domain.TbRulePool;

/**
 * 线索池规则Service接口
 * @date 2021-04-16
 */
public interface ITbRulePoolService 
{
    /**
     * 查询线索池规则
     * 
     * @param id 线索池规则ID
     * @return 线索池规则
     */
    public TbRulePool selectTbRulePoolById(Long id);


    /**
     * 查询不同类型线索池
     *
     * @param type
     * @return 线索池规则
     */
    public TbRulePool selectTbRulePoolByType(String type);

    /**
     * 查询线索池规则列表
     * 
     * @param tbRulePool 线索池规则
     * @return 线索池规则集合
     */
    public List<TbRulePool> selectTbRulePoolList(TbRulePool tbRulePool);

    /**
     * 新增线索池规则
     * 
     * @param tbRulePool 线索池规则
     * @return 结果
     */
    public int insertTbRulePool(TbRulePool tbRulePool);

    /**
     * 修改线索池规则
     * 
     * @param tbRulePool 线索池规则
     * @return 结果
     */
    public int updateTbRulePool(TbRulePool tbRulePool);

    /**
     * 批量删除线索池规则
     * 
     * @param ids 需要删除的线索池规则ID
     * @return 结果
     */
    public int deleteTbRulePoolByIds(Long[] ids);

    /**
     * 删除线索池规则信息
     * 
     * @param id 线索池规则ID
     * @return 结果
     */
    public int deleteTbRulePoolById(Long id);
}
