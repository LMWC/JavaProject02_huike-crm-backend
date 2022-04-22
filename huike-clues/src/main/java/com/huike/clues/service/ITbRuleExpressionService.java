package com.huike.clues.service;

import java.util.List;
import com.huike.clues.domain.TbRuleExpression;

/**
 * 规则达式Service接口
 * 
 * @author wgl
 * @date 2021-04-08
 */
public interface ITbRuleExpressionService 
{
    /**
     * 查询规则达式
     * 
     * @param id 规则达式ID
     * @return 规则达式
     */
    public TbRuleExpression selectTbRuleExpressionById(Long id);

    /**
     * 查询规则达式列表
     * 
     * @param tbRuleExpression 规则达式
     * @return 规则达式集合
     */
    public List<TbRuleExpression> selectTbRuleExpressionList(TbRuleExpression tbRuleExpression);

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
     * 批量删除规则达式
     * 
     * @param ids 需要删除的规则达式ID
     * @return 结果
     */
    public int deleteTbRuleExpressionByIds(Long[] ids);

    /**
     * 删除规则达式信息
     * 
     * @param id 规则达式ID
     * @return 结果
     */
    public int deleteTbRuleExpressionById(Long id);
}
