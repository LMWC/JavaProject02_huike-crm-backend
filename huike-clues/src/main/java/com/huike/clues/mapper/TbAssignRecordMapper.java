package com.huike.clues.mapper;

import java.util.List;
import com.huike.clues.domain.TbAssignRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 线索分配记录Mapper接口
 * @date 2021-04-19
 */
public interface TbAssignRecordMapper
{
    /**
     * 查询线索分配记录
     * 
     * @param id 线索分配记录ID
     * @return 线索分配记录
     */
    public TbAssignRecord selectAssignRecordById(Long id);


    public TbAssignRecord selectAssignRecordByAssignId(@Param("assignId") Long assignId, @Param("type") String type);

    /**
     * 查询线索分配记录列表
     * 
     * @param tbAssignRecord 线索分配记录
     * @return 线索分配记录集合
     */
    public List<TbAssignRecord> selectAssignRecordList(TbAssignRecord tbAssignRecord);

    /**
     * 新增线索分配记录
     * 
     * @param tbAssignRecord 线索分配记录
     * @return 结果
     */
    public int insertAssignRecord(TbAssignRecord tbAssignRecord);

    /**
     * 修改线索分配记录
     * 
     * @param tbAssignRecord 线索分配记录
     * @return 结果
     */
    public int updateAssignRecord(TbAssignRecord tbAssignRecord);

    /**
     * 删除线索分配记录
     * 
     * @param id 线索分配记录ID
     * @return 结果
     */
    public int deleteAssignRecordById(Long id);

    /**
     * 批量删除线索分配记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAssignRecordByIds(Long[] ids);


    /**
     * 设置已经分配记录
     * @param assignId
     * @param type
     * @return
     */
    public int updateLatest(@Param("assignId") Long assignId, @Param("type") String type);

    public int updateStatus(@Param("assignId") Long assignId, @Param("type") String type, @Param("status") String status);

   // public int countAssignByUser(@Param("userId") Long userId, @Param("type") String type);
    
    /**
     * 跟进用户Id统计分配线索数量
     * @param userId
     * @return
     */
    public int countAssignCluesByUser(@Param("userId") Long userId);
    
    /**
     * 跟进用户Id统计分配商机线索数量
     * @param userId
     * @return
     */
    public int countAssignBusinessByUser(@Param("userId") Long userId);
    
}
