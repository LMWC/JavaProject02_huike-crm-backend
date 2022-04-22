package com.huike.clues.mapper;

import java.util.List;
import com.huike.clues.domain.TbRuleAssign;

/**
 * 规则分配Mapper接口
 * 
 * @author wgl
 * @date 2021-04-08
 */
public interface TbRuleAssignMapper 
{
    /**
     * 查询规则分配
     * 
     * @param id 规则分配ID
     * @return 规则分配
     */
    public TbRuleAssign selectTbRuleAssignById(Long id);


    public TbRuleAssign selectMaxIdRule();

    /**
     * 查询规则分配列表
     * 
     * @param tbRuleAssign 规则分配
     * @return 规则分配集合
     */
    public List<TbRuleAssign> selectTbRuleAssignList(TbRuleAssign tbRuleAssign);

    /**
     * 新增规则分配
     * 
     * @param tbRuleAssign 规则分配
     * @return 结果
     */
    public int insertTbRuleAssign(TbRuleAssign tbRuleAssign);

    /**
     * 修改规则分配
     * 
     * @param tbRuleAssign 规则分配
     * @return 结果
     */
    public int updateTbRuleAssign(TbRuleAssign tbRuleAssign);


    /**
     * 删除规则分配
     * 
     * @param id 规则分配ID
     * @return 结果
     */
    public int deleteTbRuleAssignById(Long id);

    /**
     * 批量删除规则分配
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbRuleAssignByIds(Long[] ids);
}
