package com.huike.clues.mapper;

import java.util.List;
import com.huike.clues.domain.TbRuleExpression;

/**
 * 规则达式Mapper接口
 * @date 2021-04-08
 */
public interface TbRuleExpressionMapper 
{
    /**
     * 查询规则达式
     * 
     * @param id 规则达式ID
     * @return 规则达式
     */
    public TbRuleExpression selectTbRuleExpressionById(Long id);



    public List<TbRuleExpression> selectTbRuleExpressionByRuleId(Long id);

    /**
     * 查询规则达式列表
     * 
     * @param tbRuleExpression 规则达式
     * @return 规则达式集合
     */
    public List<TbRuleExpression> selectTbRuleExpressionList(TbRuleExpression tbRuleExpression);


    public void insertBatch(List<TbRuleExpression> list);

    /**
     * 新增规则达式
     * 
     * @param tbRuleExpression 规则达式
     * @return 结果
     */
    public int insertTbRuleExpression(TbRuleExpression tbRuleExpression);

    /**
     * 修改规则达式
     * 
     * @param tbRuleExpression 规则达式
     * @return 结果
     */
    public int updateTbRuleExpression(TbRuleExpression tbRuleExpression);

    /**
     * 删除规则达式
     * 
     * @param id 规则达式ID
     * @return 结果
     */
    public int deleteTbRuleExpressionById(Long id);

    /**
     * 批量删除规则达式
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbRuleExpressionByIds(Long[] ids);


    public int deleteTbRuleExpressionByRuleIds(Long[] ruleIds);
}
