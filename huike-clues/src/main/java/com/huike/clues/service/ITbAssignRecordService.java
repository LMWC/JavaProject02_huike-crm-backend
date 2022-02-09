package com.huike.clues.service;

import com.huike.clues.domain.TbAssignRecord;

import java.util.List;

/**
 * 线索分配记录Service接口
 */

public interface ITbAssignRecordService
{
    /**
     * 查询线索分配记录
     * 
     * @param id 线索分配记录ID
     * @return 线索分配记录
     */
    public TbAssignRecord selectAssignRecordById(Long id);

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
    public int updateTbClueAssignRecord(TbAssignRecord tbAssignRecord);

    /**
     * 批量删除线索分配记录
     * 
     * @param ids 需要删除的线索分配记录ID
     * @return 结果
     */
    public int deleteAssignRecordByIds(Long[] ids);

    /**
     * 删除线索分配记录信息
     * 
     * @param id 线索分配记录ID
     * @return 结果
     */
    public int deleteAssignRecordById(Long id);


    //public int updateTbClueAssignRecord(TbAssignRecord tbAssignRecord);
}
