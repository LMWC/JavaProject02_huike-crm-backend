package com.huike.business.service;

import java.util.List;
import java.util.Map;

import com.huike.business.domain.TbBusiness;

/**
 * 商机Service接口
 * 
 * @author wgl
 * @date 2021-04-25
 */
public interface ITbBusinessService 
{
    /**
     * 查询商机
     * 
     * @param id 商机ID
     * @return 商机
     */
    public TbBusiness selectTbBusinessById(Long id);

    /**
     * 查询商机列表
     * 
     * @param tbBusiness 商机
     * @return 商机集合
     */
    public List<TbBusiness> selectTbBusinessList(TbBusiness tbBusiness);



    public List<TbBusiness> selectTbBusinessPool(TbBusiness tbBusiness);

    /**
     * 新增商机
     * 
     * @param tbBusiness 商机
     * @return 结果
     */
    public int insertTbBusiness(TbBusiness tbBusiness);

    /**
     * 修改商机
     * 
     * @param tbBusiness 商机
     * @return 结果
     */
    public int updateTbBusiness(TbBusiness tbBusiness);

    /**
     * 批量删除商机
     * 
     * @param ids 需要删除的商机ID
     * @return 结果
     */
    public int deleteTbBusinessByIds(Long[] ids);

    /**
     * 删除商机信息
     * 
     * @param id 商机ID
     * @return 结果
     */
    public int deleteTbBusinessById(Long id);


    public String assign(Long[] clueIds, Long userId);

    public String gain(Long[] clueIds, Long userId);

    public int changeBusiness(Long clueId);

    public int updateStatus(Long id, String status);
}
