package com.huike.clues.service;

import java.util.List;

import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;

/**
 * 线索跟进记录Service接口
 * 
 * @author ruoyi
 * @date 2021-04-19
 */
public interface ITbClueTrackRecordService {
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
    public List<TbClueTrackRecord> selectTbClueTrackRecordList(TbClueTrackRecord tbClueTrackRecord);

    /**
     * 新增线索跟进记录
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 结果
     */
    public int insertTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);

    /**
     * 添加线索跟进记录
     * @param tbClue
     * @param tbClueTrackRecord
     * @return
     */
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
    
    /**
     * 跟进线索id查询线索跟进记录
     * @param clueId
     * @return
     */
	public List<TbClueTrackRecord> selectTbClueTrackRecordList(Long clueId);
}
