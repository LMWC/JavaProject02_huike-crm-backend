package com.huike.business.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huike.business.domain.TbBusiness;
import com.huike.clues.domain.vo.IndexStatisticsVo;
import org.apache.ibatis.annotations.Param;

/**
 * 商机Mapper接口
 * @date 2021-04-25
 */
public interface TbBusinessMapper {
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
     * 删除商机
     *
     * @param id 商机ID
     * @return 结果
     */
    public int deleteTbBusinessById(Long id);

    /**
     * 批量删除商机
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbBusinessByIds(Long[] ids);

    /**
     * 重新分配商机的状态
     * @param id
     * @param status
     * @return
     */
    public int resetNextTimeAndStatus(@Param("id") Long id, @Param("status") String status);


    public int setTransfer(@Param("id") Long id, @Param("status") String status);

    public void updateBusinessEndTimeById(@Param("id")Long id, @Param("endTime")Date endTime);

}
