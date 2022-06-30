package com.huike.clues.mapper;


import com.huike.clues.domain.TbClueTrackRecord;

import java.util.List;

/**
 * 线索跟进记录Mapper接口
 * @date 2021-04-19
 */
public interface TbClueTrackRecordMapper {

    /**
     * 根据线索id查询线索跟进记录
     * @param clueId
     * @return
     */
    public List<TbClueTrackRecord> selectTbClueTrackRecordListByClueId(Long clueId);

    /**
     * 新增线索跟进记录
     * @param tbClueTrackRecord 线索跟进记录
     * @return 结果
     */
    public int insertTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);
    
}
