package com.huike.contract.mapper;

import java.util.List;
import java.util.Map;

import com.huike.clues.domain.vo.IndexStatisticsVo;
import com.huike.contract.domain.TbContract;
import org.apache.ibatis.annotations.Param;

/**
 * 合同Mapper接口
 * @date 2021-05-19
 */
public interface TbContractMapper {


    public TbContract selectTbContractById(Long id);

    /**
     * 查询合同列表
     *
     * @param tbContract 合同
     * @return 合同集合
     */
    public List<TbContract> selectTbContractList(TbContract tbContract);

    /**
     * 新增合同
     *
     * @param tbContract 合同
     * @return 结果
     */
    public int insertTbContract(TbContract tbContract);

    /**
     * 修改合同
     *
     * @param tbContract 合同
     * @return 结果
     */
    public int updateTbContract(TbContract tbContract);

    /**
     * 删除合同
     *
     * @param id 合同ID
     * @return 结果
     */
    public int deleteTbContractById(Long id);

    /**
     * 批量删除合同
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbContractByIds(Long[] ids);

    /**
     * 合同统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String,Object>> contractStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);


    /**
     * 销售统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String,Object>> salesStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    /**
     * 渠道统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String,Object>> chanelStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    /**
     * 活动统计
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    public List<Map<String,Object>> activityStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);


    public List<Map<String,Object>> deptStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    public List<Map<String,Object>> channelStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    public List<Map<String,Object>> ownerShipStatistics(@Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    public Map<String,Object> countByActivity(TbContract tbContract);


    public List<Map<String,Object>> contractStatisticsByUser(IndexStatisticsVo query);
}
