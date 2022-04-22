package com.huike.clues.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.clues.mapper.TbRuleExpressionMapper;
import com.huike.clues.domain.TbRuleExpression;
import com.huike.clues.service.ITbRuleExpressionService;

/**
 * 规则达式Service业务层处理
 * 
 * @author wgl
 * @date 2021-04-08
 */
@Service
public class TbRuleExpressionServiceImpl implements ITbRuleExpressionService 
{
    @Autowired
    private TbRuleExpressionMapper tbRuleExpressionMapper;

    /**
     * 查询规则达式
     * 
     * @param id 规则达式ID
     * @return 规则达式
     */
    @Override
    public TbRuleExpression selectTbRuleExpressionById(Long id)
    {
        return tbRuleExpressionMapper.selectTbRuleExpressionById(id);
    }

    /**
     * 查询规则达式列表
     * 
     * @param tbRuleExpression 规则达式
     * @return 规则达式
     */
    @Override
    public List<TbRuleExpression> selectTbRuleExpressionList(TbRuleExpression tbRuleExpression)
    {
        return tbRuleExpressionMapper.selectTbRuleExpressionList(tbRuleExpression);
    }

    /**
     * 新增规则达式
     * 
     * @param tbRuleExpression 规则达式
     * @return 结果
     */
    @Override
    public int insertTbRuleExpression(TbRuleExpression tbRuleExpression)
    {
        return tbRuleExpressionMapper.insertTbRuleExpression(tbRuleExpression);
    }

    /**
     * 修改规则达式
     * 
     * @param tbRuleExpression 规则达式
     * @return 结果
     */
    @Override
    public int updateTbRuleExpression(TbRuleExpression tbRuleExpression)
    {
        return tbRuleExpressionMapper.updateTbRuleExpression(tbRuleExpression);
    }

    /**
     * 批量删除规则达式
     * 
     * @param ids 需要删除的规则达式ID
     * @return 结果
     */
    @Override
    public int deleteTbRuleExpressionByIds(Long[] ids)
    {
        return tbRuleExpressionMapper.deleteTbRuleExpressionByIds(ids);
    }

    /**
     * 删除规则达式信息
     * 
     * @param id 规则达式ID
     * @return 结果
     */
    @Override
    public int deleteTbRuleExpressionById(Long id)
    {
        return tbRuleExpressionMapper.deleteTbRuleExpressionById(id);
    }
}
