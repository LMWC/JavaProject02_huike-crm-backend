package com.huike.clues.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huike.clues.domain.TbRuleAssign;
import com.huike.clues.dto.TbRuleAssignDto;

/**
 * 规则分配Service接口
 * @date 2021-04-08
 */
public interface ITbRuleAssignService 
{
    /**
     * 查询规则分配
     * 
     * @param id 规则分配ID
     * @return 规则分配
     */
    public TbRuleAssign selectTbRuleAssignById(Long id);

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
    public boolean insertTbRuleAssign(TbRuleAssign tbRuleAssign);


    /**
     * 修改规则分配
     * 
     * @param tbRuleAssign 规则分配
     * @return 结果
     */
    public boolean updateTbRuleAssign(TbRuleAssign tbRuleAssign);


    public void updateOrders(List<TbRuleAssign> list);

    /**
     * 删除规则分配信息
     * 
     * @param id 规则分配ID
     * @return 结果
     */
    public boolean deleteTbRuleAssignById(Long id);


    public void loadRule();



    public boolean assignment(Long id, String type, String assignUser, String assignType);
    
    /**
     * 查询规则分配列表
     * 
     * @param tbRuleAssign 规则分配
     * @return 规则分配集合
     */
    public Page<TbRuleAssign> selectTbRuleAssignPage(TbRuleAssignDto tbRuleAssign);


}
