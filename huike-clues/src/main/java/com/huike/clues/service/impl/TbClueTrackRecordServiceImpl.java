package com.huike.clues.service.impl;

import java.util.List;

import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.clues.mapper.TbClueTrackRecordMapper;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.service.ITbClueTrackRecordService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 线索跟进记录Service业务层处理
 * @date 2021-04-22
 */
@Service
public class TbClueTrackRecordServiceImpl implements ITbClueTrackRecordService 
{
    @Autowired
    private TbClueTrackRecordMapper tbClueTrackRecordMapper;

    @Autowired
    private TbClueMapper tbClueMapper;

    /**
     * 查询线索跟进记录列表
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 线索跟进记录
     */
    @Override
    public List<TbClueTrackRecord> selectTbClueTrackRecordList(TbClueTrackRecord tbClueTrackRecord)
    {
        return tbClueTrackRecordMapper.selectTbClueTrackRecordList(tbClueTrackRecord);
    }

    /**
     * 修改线索跟进记录
     * 
     * @param tbClueTrackRecord 线索跟进记录
     * @return 结果
     */
    @Override
    public int updateTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord)
    {
        return tbClueTrackRecordMapper.updateTbClueTrackRecord(tbClueTrackRecord);
    }

    /**
     * 批量删除线索跟进记录
     * 
     * @param ids 需要删除的线索跟进记录ID
     * @return 结果
     */
    @Override
    public int deleteTbClueTrackRecordByIds(Long[] ids)
    {
        return tbClueTrackRecordMapper.deleteTbClueTrackRecordByIds(ids);
    }

    /**
     * 删除线索跟进记录信息
     * 
     * @param id 线索跟进记录ID
     * @return 结果
     */
    @Override
    public int deleteTbClueTrackRecordById(Long id)
    {
        return tbClueTrackRecordMapper.deleteTbClueTrackRecordById(id);
    }
    
}
