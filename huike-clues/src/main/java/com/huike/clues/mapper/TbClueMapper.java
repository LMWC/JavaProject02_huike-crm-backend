package com.huike.clues.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.vo.IndexStatisticsVo;
import org.apache.ibatis.annotations.Param;

/**
 * 线索管理Mapper接口

 * @date 2021-04-02
 */
public interface TbClueMapper {
    /**
     * 查询线索管理
     *
     * @param id 线索管理ID
     * @return 线索管理
     */
    public TbClue selectTbClueById(Long id);


    public List<TbClue>  selectClueByIds(Long[] ids);

    /**
     * 查询线索管理
     *
     * @param phone 手机号
     * @return 线索管理
     */
    public TbClue selectTbClueByPhone(String phone);

    /**
     * 查询线索管理列表
     *
     * @param tbClue 线索管理
     * @return 线索管理集合
     */
    public List<TbClue> selectTbClueList(TbClue tbClue);


    public List<TbClue> selectTbClueForReport(TbClue tbClue);


    public List<TbClue> selectTbCluePoll(TbClue tbClue);

    /**
     * 新增线索管理
     *
     * @param tbClue 线索管理
     * @return 结果
     */
    public int insertTbClue(TbClue tbClue);

    /**
     * 修改线索管理
     *
     * @param tbClue 线索管理
     * @return 结果
     */
    public int updateTbClue(TbClue tbClue);

    public int resetNextTimeAndStatus(@Param("id") Long id, @Param("status") String status);


    public int setTransfer(@Param("id") Long id, @Param("status") String status);

    /**
     * 删除线索管理
     *
     * @param id 线索管理ID
     * @return 结果
     */
    public int deleteTbClueById(Long id);

    /**
     * 批量删除线索管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbClueByIds(Long[] ids);


    /**
     * 根据渠道活动统计
     * @param tbClue
     * @return
     */
    public Map<String,Object> countByActivity(TbClue tbClue);


    public List<Map<String,Object>> countAllContractByUser(@Param("indexVo")IndexStatisticsVo vo);


    public void updateClueEndTimeById(@Param("id")Long id,@Param("endTime") Date endTime);


    public Map<String, Object> getcontractsBasicInfo(@Param("indexVo")IndexStatisticsVo request,
                                                     @Param("now")String now,@Param("username")String username);



    /**
     * 统计线索数量
     * @param request
     * @param now
     * @param username
     * @return
     */
    public int getCluesNum(@Param("indexVo")IndexStatisticsVo request,@Param("now")String now,@Param("username")String username);

}