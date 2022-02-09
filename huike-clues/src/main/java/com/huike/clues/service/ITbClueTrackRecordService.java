package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.dto.TbClueTrackRecordDto;

/**
 * 线索跟进记录Service接口
 * 
 * @author ruoyi
 * @date 2021-04-19
 */
public interface ITbClueTrackRecordService 
{
    /**
     * 查询线索跟进记录
     * 
     * @param id 线索跟进记录ID
     * @return 线索跟进记录
     */
    public TbClueTrackRecord selectTbClueTrackRecordById(Long id);

    /**
     * 查询线索跟进记录列表
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 线索跟进记录集合
     */
    public Page<TbClueTrackRecord> selectTbClueTrackRecordList(TbClueTrackRecordDto tbClueTrackRecord);

    /**
     * 新增线索跟进记录
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 结果
     */
    public int insertTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);


    public int insertTbClueTrackRecord(TbClue tbClue, TbClueTrackRecord tbClueTrackRecord);

    /**
     * 修改线索跟进记录
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 结果
     */
    public int updateTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);

    /**
     * 批量删除线索跟进记录
     * 
     * @param ids 需要删除的线索跟进记录ID
     * @return 结果
     */
    public int deleteTbClueTrackRecordByIds(Long[] ids);

    /**
     * 删除线索跟进记录信息
     * 
     * @param id 线索跟进记录ID
     * @return 结果
     */
    public int deleteTbClueTrackRecordById(Long id);
}
